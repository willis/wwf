package com.mpaike.util.bot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mpaike.util.MD5;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class SpiderSQLWorkload implements IWorkloadStorable {

  /**
   * The JDBC connection.
   */
  Connection connection;
  
  /**
   * A prepared SQL statement to create the workload.
   */
  PreparedStatement prepCreateURL;
  PreparedStatement prepCreateImageURL;
  
  /**
   * A prepared SQL statement to delete the workload.
   */
  PreparedStatement prepDeleteURL;
  PreparedStatement prepDeleteImageURL;

  /**
   * A prepared SQL statement to clear the workload.
   */
  PreparedStatement prepClear;

  /**
   * A prepared SQL statement to assign a workload.
   */
  PreparedStatement prepAssign;

  /**
   * A prepared SQL statement to get the status of
   * a URL.
   */
  PreparedStatement prepGetStatus;

  /**
   * A prepared SQL statement to set the status.
   */
  PreparedStatement prepSetStatus1;

  /**
   * A prepared SQL statement to set the status.
   */
  PreparedStatement prepSetStatus2;

  /**
   * A prepared SQL statement to set the status.
   */
  PreparedStatement prepSetStatus3;

  
  public SpiderSQLWorkload(Connection connection,String name)throws SQLException
  {
    this.connection = connection;
    init(name);
  }
  

  private void init(String name)throws SQLException{
	  //表操作
	  String createTable = "CREATE TABLE `bot_url_"+name+"` (`id` varchar(32) NOT NULL DEFAULT '',`url` varchar(1024) NOT NULL DEFAULT '',`status` char(1) NOT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;";
	  prepCreateURL = connection.prepareStatement(createTable);
	  prepDeleteURL = connection.prepareStatement("DROP table `bot_url_"+name+"`;");

	  try{
		  prepCreateURL.execute();
	  }catch(SQLException e){
	  }

	    prepClear = connection.prepareStatement("DELETE FROM bot_url_"+name+";");
	    prepAssign = connection.prepareStatement("SELECT url FROM bot_url_"+name+" WHERE status = 'W';");
	    prepGetStatus = connection.prepareStatement("SELECT status FROM bot_url_"+name+" WHERE id = ?;");
	    prepSetStatus1 = connection.prepareStatement("SELECT count(*) as qty FROM bot_url_"+name+" WHERE id = ?;");
	    prepSetStatus2 = connection.prepareStatement("INSERT INTO bot_url_"+name+"(id,url,status) VALUES (?,?,?);");
	    prepSetStatus3 = connection.prepareStatement("UPDATE bot_url_"+name+" SET status = ? WHERE id = ?;");
  }

  /**
   * Call this method to request a URL
   * to process. This method will return
   * a WAITING URL and mark it as RUNNING.
   *
   * @return The URL that was assigned.
   */
  synchronized public String assignWorkload()
  {
    ResultSet rs = null;

    try {
      rs = prepAssign.executeQuery();

      if ( !rs.next() )
        return null;
      String url = rs.getString("url");
      setStatus(url,RUNNING);
      return url;
    } catch ( SQLException e ) {
      Log.logException("SQL Error: ",e );
    } finally {
      try {
        if ( rs!=null )
          rs.close();
      } catch ( Exception e ) {
      }
    }
    return null;
  }

  /**
   * Add a new URL to the workload, and
   * assign it a status of WAITING.
   *
   * @param url The URL to be added.
   */
  synchronized public void addWorkload(String url)
  {
    if ( getURLStatus(url)!=UNKNOWN )
      return;
    setStatus(url,WAITING);

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
    if ( error )
      setStatus(url,ERROR);
    else
      setStatus(url,COMPLETE);

  }




  /**
   * This is an internal method used to set the status
   * of a given URL. This method will create a record
   * for the URL of one does not currently exist.
   *
   * @param url The URL to set the status for.
   * @param status What status to set.
   */
  protected void setStatus(String url,char status)
  {
    ResultSet rs = null;

    try {
      // first see if one exists
      prepSetStatus1.setString(1,MD5.toMD5(url));
      rs = prepSetStatus1.executeQuery();
      rs.next();
      int count = rs.getInt("qty");

      if ( count<1 ) {// Create one
    	  prepSetStatus2.setString(1,MD5.toMD5(url));
        prepSetStatus2.setString(2,url);
        prepSetStatus2.setString(3,(new Character(status)).toString());
        prepSetStatus2.executeUpdate();
      } else {// Update it
        prepSetStatus3.setString(1,(new Character(status)).toString());
        prepSetStatus3.setString(2,MD5.toMD5(url));
        prepSetStatus3.executeUpdate();
      }
    } catch ( SQLException e ) {
      Log.logException("SQL Error: ",e );
    } finally {
      try {
        if ( rs!=null )
          rs.close();
      } catch ( Exception e ) {
      }
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
    ResultSet rs = null;

    try {
      // first see if one exists
      prepGetStatus.setString(1,MD5.toMD5(url));
      rs = prepGetStatus.executeQuery();

      if ( !rs.next() )
        return UNKNOWN;

      return rs.getString("status").charAt(0);
    } catch ( SQLException e ) {
      Log.logException("SQL Error: ",e );
    } finally {
      try {
        if ( rs!=null )
          rs.close();
      } catch ( Exception e ) {
      }
    }
    return UNKNOWN;
  }

  /**
   * Clear the contents of the workload store.
   */
  synchronized public void clear()
  {
//    try {
//      prepClear.executeUpdate();
//    } catch ( SQLException e ) {
//      Log.logException("SQL Error: ",e );
//    }
  }
  
  public void distory(){
	  
  }

	@Override
	public void close() {
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("SpiderSQLWorkload close:");
	}
}

