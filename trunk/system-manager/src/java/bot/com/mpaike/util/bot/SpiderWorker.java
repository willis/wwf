package com.mpaike.util.bot;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class SpiderWorker extends Thread {

  /**
   * The URL that this spider worker
   * should be downloading.
   */
  protected String target;

  /**
   * The owner of this spider worker class,
   * should always be a Spider object.
   * This is the class that this spider
   * worker will send its data to.
   */
  protected Spider owner;

  /**
   * Indicates if the spider is busy or not.
   * true = busy
   * false = idle
   */
  protected boolean busy;

  /**
   * A descendant of the HTTP object that
   * this class should be using for HTTP
   * communication. This is usually the
   * HTTPSocket class.
   */
  protected HTTP http;

  /**
   * Constructs a spider worker object.
   *
   * @param owner The owner of this object, usually
   * a Spider object.
   * @param http
   */
  public SpiderWorker(Spider owner,HTTP http)
  {
    this.http = http;
    this.owner = owner;
  }

  /**
   * Returns true of false to indicate if
   * the spider is busy or idle.
   *
   * @return true = busy
   * false = idle
   */
  public boolean isBusy()
  {
    return this.busy;
  }

  /**
   * The run method causes this thread to go idle
   * and wait for a workload. Once a workload is
   * received, the processWorkload method is called
   * to handle the workload.
   */
  public void run()
  {
    for ( ;; ) {
      target = this.owner.getWorkload();
      if ( target==null )
        return;
      owner.getSpiderDone().workerBegin();
      processWorkload();
      owner.getSpiderDone().workerEnd();
    }
  }

  /**
   * The run method actually performs the
   * the workload assigned to this object.
   */
  public void processWorkload()
  {
    try {
      busy = true;
      Log.log(Log.LOG_LEVEL_NORMAL,"Spidering " + target );
      http.send(target,null);
      Attribute typeAttribute = http.getServerHeaders().get("Content-Type");

      // if no content-type at all, its PROBABLY not HTML
      if ( typeAttribute==null )
        return;

      // now check to see if is HTML, ONLY PARSE text type files(namely HTML)
      owner.processPage(http);
      if ( !typeAttribute.getValue().startsWith("text/") )
        return;

      HTMLParser parse = new HTMLParser();

      parse.source = new StringBuffer(http.getBody());
      // find all the links
      while ( !parse.eof() ) {
        char ch = parse.get();
        if ( ch==0 ) {
          HTMLTag tag = parse.getTag();
          Attribute link = tag.get("HREF");
          if ( link==null )
            link = tag.get("SRC");

          if ( link==null )
            continue;

          URL target=null;
          try {
            target = new URL(new URL(this.target),link.getValue());
          } catch ( MalformedURLException e ) {
            Log.log(Log.LOG_LEVEL_TRACE,
                    "Spider found other link: " + link );
            owner.foundOtherLink(link.getValue());
            continue;
          }

          if ( owner.getRemoveQuery() )
            target = URLUtility.stripQuery(target);
          target = URLUtility.stripAnhcor(target);


          if ( target.getHost().equalsIgnoreCase(
                                                new URL(this.target).getHost()) ) {
            Log.log(Log.LOG_LEVEL_NORMAL,
                    "Spider found internal link: " + target.toString() );
            owner.foundInternalLink(target.toString());
          } else {
            Log.log(Log.LOG_LEVEL_NORMAL,
                    "Spider found external link: " + target.toString() );
            owner.foundExternalLink(target.toString());
          }
        }
      }
      owner.completePage(http,false);
    } catch ( java.io.IOException e ) {
      Log.log(Log.LOG_LEVEL_ERROR,
              "Error loading file("+ target +"): " + e );
      owner.completePage(http,true);
    } catch ( Exception e ) {
      Log.logException(
                      "Exception while processing file("+ target +"): ", e );
      owner.completePage(http,true);
    } finally {
      busy = false;
    }
  }

  /**
   * Returns the HTTP descendant that this
   * object should use for all HTTP communication.
   *
   * @return An HTTP descendant object.
   */
  public HTTP getHTTP()
  {
    return http;
  }
}

