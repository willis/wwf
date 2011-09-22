package com.mpaike.util.bot;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class Spider extends Thread implements ISpiderReportable {
  protected IWorkloadStorable workload;
  protected SpiderWorker pool[];
  protected boolean worldSpider;
  protected ISpiderReportable manager;
  protected boolean halted = false;
  protected SpiderDone done = new SpiderDone();
  protected int maxBodySize;
  protected String currentUrl;
  protected BlockingDeque<String> spiderLog = new LinkedBlockingDeque(35);

  /**
   * This constructor prepares the spider to begin.
   * Basic information required to begin is passed.
   * This constructor uses the internal workload manager.
   *
   * If you do not need a custom spider worker or
   * workload management, this is the constructor to use.
   *
   * @param manager The object that this spider reports its findings to.
   * @param url The URL that the spider should begin at.
   * @param http The HTTP handler used by this spider.
   * @param poolsize The size of the thread pool.
   */
  public Spider(ISpiderReportable manager,String url,HTTP http,int poolSize)
  {
    this(manager,url,http,poolSize,new SpiderInternalWorkload());
  }

  /**
   * This constructor prepares the spider to begin.
   * Basic information required to begin is passed.
   * This constructor allows the user to specify a
   * customized workload manager.
   *
   * @param manager The object that this spider reports its findings to.
   * @param url The URL that the spider should begin at.
   * @param http The HTTP handler used by this spider.
   * @param poolsize The size of the thread pool.
   * @param w A customized workload manager.
   */
  public Spider(ISpiderReportable manager,String url,HTTP http,int poolSize,IWorkloadStorable w)
  {
      try
      {
        init(manager,url,http.getClass(),SpiderWorker.class,poolSize,w);
      }
      // mostly ignore the exceptions since we're using the standard SpiderWorker stuff
      catch(InstantiationException e)
      {
          Log.logException("Spider reflection exception",e);
      }
      catch(NoSuchMethodException e)
      {
          Log.logException("Spider reflection exception",e);
      }
      catch(IllegalAccessException e)
      {
          Log.logException("Spider reflection exception",e);
      }
      catch(InvocationTargetException e)
      {
          Log.logException("Spider reflection exception",e);
      }

  }

  /**
   * This constructor prepares the spider to begin.
   * Basic information required to begin is passed.
   * This constructor allows the user to specify a
   * customized workload manager.
   *
   * This constructor was added to allow you to specify
   * a custom SpiderWorker class. Though not usually necessary
   * this will allow you exact control over the HTML parse.
   *
   * @param manager The object that this spider reports its findings to.
   * @param url The URL that the spider should begin at.
   * @param http The HTTP handler used by this spider.
   * @param worker A SpiderWorker class to be used to process the pages.
   * @param poolsize The size of the thread pool.
   * @param w A customized workload manager.
   */
  private Spider(ISpiderReportable manager,String url,Class http,Class worker,int poolSize,IWorkloadStorable w)
  throws InstantiationException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
  {
      init(manager,url,http,worker,poolSize,w);
  }


  /**
   * Internal method that is called by the various constructors to setup the spider.
   *
   * @param manager The object that this spider reports its findings to.
   * @param url The URL that the spider should begin at.
   * @param http The HTTP handler used by this spider.
   * @param http The spider worker
   * @param poolsize The size of the thread pool.
   * @param w A customized workload manager.
   */
  private void init(ISpiderReportable manager,String url,Class http,Class worker,int poolSize,IWorkloadStorable w)
  throws InstantiationException,NoSuchMethodException,IllegalAccessException,InvocationTargetException
  {
    this.manager = manager;
    worldSpider = false;

    Class types[] = { Spider.class,HTTP.class };
    Constructor constructor = worker.getConstructor(types);

    pool = new SpiderWorker[poolSize];
    HTTP hc;
    for ( int i=0;i<pool.length;i++ ) {
      hc = (HTTP)http.newInstance();
      Object params[]={this,hc};

      pool[i] = (SpiderWorker)constructor.newInstance(params);
    }
    workload = w;
    if ( url.length()>0 ) {
      workload.clear();
      addWorkload(url);
    }
  }

  public String getCurrentUrl(){
	  return currentUrl;
  }

  /**
   * Get the SpiderDone object used by this spider
   * to determine when it is done.
   *
   * @return Returns true if the spider is done.
   */
  public SpiderDone getSpiderDone()
  {
    return done;
  }

  /**
   * The main loop of the spider. This can be called
   * directly, or the start method can be called to
   * run as a background thread. This method will not
   * return until there is no work remaining for the
   * spider.
   */
  public void run()
  {
    if ( halted )
      return;
    for ( int i=0;i<pool.length;i++ )
      pool[i].start();

    try {
      done.waitBegin();
      done.waitDone();
      Log.log(Log.LOG_LEVEL_NORMAL,"Spider has no work.");
      spiderComplete();

      for ( int i=0;i<pool.length;i++ ) {
        pool[i].interrupt();
        pool[i].join();
        pool[i] = null;
      }

    } catch ( Exception e ) {
      Log.logException("Exception while starting spider", e);
    }

  }


  /**
   * This method is called to get a workload
   * from the workload manager. If no workload
   * is available, this method will block until
   * there is one.
   *
   * @return Returns the next URL to be spidered.
   */
  synchronized public String getWorkload()
  {
    try {
      for ( ;; ) {
        if ( halted )
          return null;
        String w = workload.assignWorkload();
        if ( w!=null )
          return w;
        wait();
      }
    } catch ( java.lang.InterruptedException e ) {
    }
    return null;
  }

  /**
   * Called to add a workload to the workload manager.
   * This method will release a thread that was waiting
   * for a workload. This method will do nothing if the
   * spider has been halted.
   *
   * @param url The URL to be added to the workload.
   */
  synchronized public void addWorkload(String url)
  {
    if ( halted )
      return;
    workload.addWorkload(url);
    notify();
  }

  /**
   * Called to specify this spider as either a world
   * or site spider. See getWorldSpider for more information
   * about what a world spider is.
   *
   * @param b True to be a world spider.
   */
  public void setWorldSpider(boolean b)
  {
    worldSpider = b;
  }

  /**
   * Returns true if this is a world spider, a world
   * spider does not restrict itself to a single site
   * and will likely go on "forever".
   *
   * @return Returns true if the spider is done.
   */
  public boolean getWorldSpider()
  {
    return worldSpider;
  }


  /**
   * Called when the spider finds an internal
   * link. An internal link shares the same
   * host address as the URL that started
   * the spider. This method hands the link off
   * to the manager and adds the URL to the workload
   * if necessary.
   *
   * @param url The URL that was found by the spider.
   * @return true - The spider should add this URL to the workload.
   * false - The spider should not add this URL to the workload.
   */
  synchronized public boolean foundInternalLink(String url)
  {
	currentUrl = url;
	addLog(url);
    if ( manager.foundInternalLink(url) )
      addWorkload(url);
    return true;
  }

  /**
   * Called when the spider finds an external
   * link. An external link does not share the
   * same host address as the URL that started
   * the spider. This method hands the link off
   * to the manager and adds the URL to the workload
   * if necessary. If this is a world spider, then
   * external links are treated as internal links.
   *
   * @param url The URL that was found by the spider.
   * @return true - The spider should add this URL to the workload.
   * false - The spider should not add this URL to the workload.
   */
  synchronized public boolean foundExternalLink(String url)
  {
	currentUrl = url;
	addLog(url);
    if ( worldSpider ) {
      foundInternalLink(url);
      return true;
    }

    if ( manager.foundExternalLink(url) )
      addWorkload(url);
    return true;
  }

  /**
   * Called when the spider finds a type of
   * link that does not point to another HTML
   * page(for example a mailto link). This method
   * hands the link off to the manager and adds
   * the URL to the workload if necessary.
   *
   * @param url The URL that was found by the spider.
   * @return true - The spider should add this URL to the workload.
   * false - The spider should not add this URL to the workload.
   */
  synchronized public boolean foundOtherLink(String url)
  {
	currentUrl = url;
	addLog(url);
    if ( manager.foundOtherLink(url) )
      addWorkload(url);
    return true;
  }

  /**
   * Called to actually process a page. This is where the
   * work actually done by the spider is usually preformed.
   *
   * @param page The page contents.
   * @param error true - This page resulted in an HTTP error.
   * false - This page downloaded correctly.
   */
  synchronized public void processPage(HTTP page)
  {
    manager.processPage(page);
  }


  /**
   * This method is called by the spider to determine if
   * query strings should be removed. By default the spider
   * always chooses to remove query strings, so true is
   * returned.
   *
   * @return true - Query string should be removed.
   * false - Leave query strings as is.
   */
  synchronized public boolean getRemoveQuery()
  {
    return true;
  }

  /**
   * Called to request that a page be processed.
   * This page was just downloaded by the spider.
   * This messages passes this call on to its
   * manager.
   *
   * @param page The page contents.
   * @param error true - This page resulted in an HTTP error.
   * false - This page downloaded correctly.
   */
  synchronized public void completePage(HTTP page,boolean error)
  {
    workload.completeWorkload(page.getURL(),error);

    // if this was a redirect, then also complete the root page
    if ( page.getURL().equals(page.getRootURL()) )
      workload.completeWorkload(page.getRootURL(),error);
  }

  /**
   * Called when the spider has no more work. This method
   * just passes this event on to its manager.
   */
  synchronized public void spiderComplete()
  {
    manager.spiderComplete();
    workload.close();
    addLog("spiderComplete.");
  }

  /**
   * Called to cause the spider to halt. The spider will not halt
   * immediately. Once the spider is halted the run method will
   * return.
   */
  synchronized public void halt()
  {
    halted = true;
    workload.clear();
    notifyAll();
  }

  /**
   * Determines if the spider has been halted.
   *
   * @return Returns true if the spider has been halted.
   */
  public boolean isHalted()
  {
    return halted;
  }

  /**
   * This method will set the maximum body size
   * that will be downloaded.
   *
   * @param i The maximum body size, or -1 for unlifted.
   */
  public void setMaxBody(int mx)
  {
    maxBodySize = mx;
    for ( int i=0;i<pool.length;i++ )
      pool[i].getHTTP().setMaxBody(mx);
  }

  /**
   * This method will return the maximum body size
   * that will be downloaded.
   *
   * @return The maximum body size, or -1 for unlifted.
   */
  public int getMaxBody()
  {
    return maxBodySize;
  }
  
  public BlockingDeque<String> getSpiderLog() {
	return spiderLog;
}

private void addLog(String url){
	  if(!this.spiderLog.offer(url)){
			this.spiderLog.poll();
			this.spiderLog.offer(url);

	  }
  }

}
