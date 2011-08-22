package com.mpaike.util.bot;
import java.util.*;
import java.net.*;
import java.io.*;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class BotExclusion {

  /**
   * The full URL of the robots.txt file.
   */
  protected String robotFile;

  /**
   * A list of full URL's to exclude.
   */
  protected Vector exclude = new Vector();
  /**
   * @param http A HTTP object to use.
   * @param url A URL from the webster to load the robots.txt file from.
   */

  public void load(HTTP http,String url)
  throws MalformedURLException,
  UnknownHostException,
  java.io.IOException
  {
    String str;
    boolean active = false;

    URL u = new URL(url);
    URL u2 = new URL(
                    u.getProtocol(),
                    u.getHost(),
                    u.getPort(),
                    "/robots.txt");
    robotFile = u2.toString();
    http.send(robotFile,null);

    StringReader sr = new StringReader(http.getBody());
    BufferedReader r = new BufferedReader(sr);
    while ( (str=r.readLine()) != null ) {
      str = str.trim();
      if ( str.length()<1 )
        continue;
      if ( str.charAt(0)=='#' )
        continue;
      int i = str.indexOf(':');
      if ( i==-1 )
        continue;
      String command = str.substring(0,i);
      String rest = str.substring(i+1).trim();
      if ( command.equalsIgnoreCase("User-agent") ) {
        active = false;
        if ( rest.equals("*") )
          active = true;
        else {
          if ( rest.equalsIgnoreCase(http.getAgent()) )
            active = true;
        }
      }
      if ( active ) {
        if ( command.equalsIgnoreCase("disallow") ) {
          URL u3 = new URL(new URL(robotFile),rest);
          if ( !isExcluded(u3.toString()) )
            exclude.addElement(u3.toString());
        }
      }
    }
  }

  /**
   * This is the main worker method for this class.
   * This method can be called to determine if the
   * specified URL should be excluded.
   *
   * @param url The URL to be checked.
   * @return Returns true if the specified URL is to be excluded.
   * Returns false if not.
   */
  public boolean isExcluded(String url)
  {
    for ( Enumeration e = exclude.elements();
        e.hasMoreElements() ; ) {
      String str = (String)e.nextElement();
      if ( str.startsWith(url) )
        return true;
    }
    return false;
  }

  /**
   * Returns a list of URL's to be excluded.
   *
   * @return A vector of URL's to be excluded.
   */
  public Vector getExclude()
  {
    return exclude;
  }
  /**
   * Returns the full URL of the robots.txt file.
   *
   * @return The full URL of the robots.txt file.
   */

  public String getRobotFile()
  {
    return robotFile;
  }
}
