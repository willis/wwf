package com.mpaike.util.bot;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class SpiderInternalWorkload implements IWorkloadStorable {

  /**
   * A list of complete workload items.
   */
  Hashtable complete = new Hashtable();

  /**
   * A list of waiting workload items.
   */
  Vector waiting = new Vector();

  /**
   * A list of running workload items.
   */
  Vector running = new Vector();

  /**
   * Call this method to request a URL
   * to process. This method will return
   * a WAITING URL and mark it as RUNNING.
   *
   * @return The URL that was assigned.
   */
  synchronized public String assignWorkload()
  {
    if ( waiting.size()<1 )
      return null;

    String w=(String)waiting.firstElement();
    if ( w!=null ) {
      waiting.remove(w);
      running.addElement(w);
    }
    Log.log(Log.LOG_LEVEL_TRACE,"Spider workload assigned:" + w);
    return w;
  }

  /**
   * Add a new URL to the workload, and
   * assign it a status of WAITING.
   *
   * @param url The URL to be added.
   */
  synchronized public void addWorkload(String url)
  {
    if ( getURLStatus(url) != IWorkloadStorable.UNKNOWN )
      return;
    waiting.addElement(url);
    Log.log(Log.LOG_LEVEL_TRACE,"Spider workload added:" + url);
  }

  /**
   * Called to mark this URL as either
   * COMPLETE or ERROR.
   *
   * @param url The URL to complete.
   * @param error true - assign this workload a status of ERROR.
   * false - assign this workload a status of COMPLETE.
   */
  synchronized public void completeWorkload(String url,boolean error)
  {
    running.remove(url);

    if ( error ) {
      Log.log(Log.LOG_LEVEL_TRACE,"Spider workload ended in error:" + url);
      complete.put(url,"e");
    } else {
      Log.log(Log.LOG_LEVEL_TRACE,"Spider workload complete:" + url);
      complete.put(url,"c");
    }
  }

  /**
   * Get the status of a URL.
   *
   * @param url Returns either RUNNING, ERROR
   * WAITING, or COMPLETE. If the URL
   * does not exist in the database,
   * the value of UNKNOWN is returned.
   * @return Returns either RUNNING,ERROR,
   * WAITING,COMPLETE or UNKNOWN.
   */
  synchronized public char getURLStatus(String url)
  {
    if ( complete.get(url)!=null )
      return COMPLETE;

    if ( waiting.size()>0 ) {
      for ( Enumeration e = waiting.elements() ; e.hasMoreElements() ; ) {
        String w = (String)e.nextElement();
        if ( w.equals(url) )
          return WAITING;
      }
    }

    if ( running.size()>0 ) {
      for ( Enumeration e = running.elements() ; e.hasMoreElements() ; ) {
        String w = (String)e.nextElement();
        if ( w.equals(url) )
          return RUNNING;
      }
    }

    return UNKNOWN;
  }

  /**
   * Clear the contents of the workload store.
   */
  synchronized public void clear()
  {
    waiting.clear();
    complete.clear();
    running.clear();
  }
}
