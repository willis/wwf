package com.mpaike.core.database.createdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;


/**
 * 安装类
 * 
 * @author niko
 * 
 */
public class Install {
	
	public static final int POSTGRESQL = 0;
	public static final int SQLSERVER = 1;
	public static final int MYSQL = 2;
	public static final int ORACLE = 3;
	public static final int HSQLDB = 4;

	
	
	public static void dbXml(String fileName, String dbHost, String dbPort,
			String dbName, String dbUser, String dbPassword) throws Exception {
		String s = FileUtils.readFileToString(new File(fileName));
		s = s.replaceFirst("DB_HOST", dbHost);
		s = s.replaceFirst("DB_PORT", dbPort);
		s = s.replaceFirst("DB_NAME", dbName);
		s = s.replaceFirst("DB_USER", dbUser);
		s = s.replaceFirst("DB_PASSWORD", dbPassword);
		FileUtils.writeStringToFile(new File(fileName), s);
	}

	public static Connection getConn(int type,String dbHost, String dbPort,
			String dbName, String dbUser, String dbPassword) throws Exception {

		Connection conn = DriverManager.getConnection(getConnURL(type,dbHost, dbPort, dbName),dbUser,dbPassword);
		return conn;
	}
	
	private static String getConnURL(int type,String dbHost, String dbPort,
			String dbName) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		String connStr = "";
		switch (type) {
		case POSTGRESQL:
			Class.forName("org.postgresql.Driver").newInstance();
			connStr = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;
			break;
		case SQLSERVER:
			Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
			connStr = "jdbc:jtds:sqlserver://"+dbHost+":"+dbPort+";DatabaseName="+dbName+";SelectMethod=cursor";
			break;
		case MYSQL:
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connStr = "jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?useUnicode=true&characterEncoding=UTF-8";
			break;
		case ORACLE:
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			connStr =  "jdbc:oracle:thin:@"+dbHost+":"+dbPort+":"+dbName;
			break;
		case HSQLDB:
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			connStr = "jdbc:hsqldb:hsql://"+ dbHost + "/" + dbName;
			break;
		default:
			break;
		}
		return connStr;
	}
	
	private static String getCreateSql(int type,String dbName,String dbUser) {
		String sql = "";
		switch (type) {
		case POSTGRESQL:
			sql = "CREATE DATABASE \"" + dbName + "\" WITH OWNER="+dbUser+" ENCODING = 'UTF8'";
			break;
		case SQLSERVER:
			sql = "CREATE DATABASE \"" + dbName +"\"";
			break;
		case MYSQL:
			sql = "CREATE DATABASE " + dbName + " CHARACTER SET UTF8";
			break;
		case ORACLE:
			sql =  "CREATE DATABASE " + dbName;
			break;
		case HSQLDB:
			sql = "CREATE DATABASE " + dbName;
			break;
		default:
			break;
		}
		return sql;
	}


	public static void webXml(String fromFile, String toFile) throws Exception {
		FileUtils.copyFile(new File(fromFile), new File(toFile));
	}

	/**
	 * 创建数据库
	 * 
	 * @param dbHost
	 * @param dbName
	 * @param dbPort
	 * @param dbUser
	 * @param dbPassword
	 * @throws Exception
	 */
	public static void createDb(int type,String dbHost, String dbPort, String dbName,
			String dbUser, String dbPassword,boolean dorpDb) throws Exception {

		Connection conn = getConn(type,dbHost, dbPort, dbName, dbUser, dbPassword);
		
		Statement stat = conn.createStatement();
		
		String sql = "DROP DATABASE \"" + dbName + "\"";
		try{
			if(dorpDb){
				stat.execute(sql);
			}
		}catch(Exception ex){
			//ex.printStackTrace();
		}
		sql = getCreateSql( type, dbName, dbUser);
		stat.execute(sql);
		stat.close();
		conn.close();
	}

	private static String getCharset(int type,String dbName) {
		String sql = "";
		switch (type) {
		case POSTGRESQL:
			sql = "ALTER DATABASE " + dbName + " WITH ENCODING = 'UTF8'";
			break;
		case SQLSERVER:
			sql = "CREATE DATABASE \"" + dbName +"\"";
			break;
		case MYSQL:
			sql = "ALTER DATABASE " + dbName + " CHARACTER SET UTF8";
			break;
		case ORACLE:
			sql =  "CREATE DATABASE " + dbName;
			break;
		case HSQLDB:
			sql =  "CREATE DATABASE " + dbName;
			break;
		default:
			break;
		}
		return sql;
	}
	
	public static void changeDbCharset(int type,String dbHost, String dbPort,
			String dbName, String dbUser, String dbPassword) throws Exception {
		Connection conn = getConn(type,dbHost, dbPort, dbName, dbUser, dbPassword);
		Statement stat = conn.createStatement();
		String sql = getCharset(type,dbName);;
		stat.execute(sql);
		stat.close();
		conn.close();
	}

	/**
	 * 创建表
	 * 
	 * @param dbHost
	 * @param dbName
	 * @param dbPort
	 * @param dbUser
	 * @param dbPassword
	 * @param sqlList
	 * @throws Exception
	 */
	public static void createTable(int type,String dbHost, String dbPort, String dbName,
			String dbUser, String dbPassword, List<String> sqlList)
			throws Exception {
		Connection conn = getConn(type, dbHost, dbPort, dbName, dbUser, dbPassword);
		Statement stat = conn.createStatement();
		for (String dllsql : sqlList) {
			stat.addBatch(dllsql);
		}
		stat.executeBatch();
		stat.close();
		conn.close();
	}

	/**
	 * 读取sql语句。“/*”开头为注释，“;”为sql结束。
	 * 
	 * @param fileName
	 *            sql文件地址
	 * @return list of sql
	 * @throws Exception
	 */
	public static List<String> readSql(String fileName) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileName), "UTF-8"));
		List<String> sqlList = new ArrayList<String>();
		StringBuilder sqlSb = new StringBuilder();
		String s = null;
		while ((s = br.readLine()) != null) {
			if (s.startsWith("/*")) {
				continue;
			}
			if (s.endsWith(";")) {
				sqlSb.append(s);
				sqlSb.setLength(sqlSb.length() - 1);
				sqlList.add(sqlSb.toString());
				sqlSb.setLength(0);
			} else {
				sqlSb.append(s);
			}
		}
		br.close();
		return sqlList;
	}
}
