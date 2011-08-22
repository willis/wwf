package com.mpaike.util.bot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;


public class ImageReportable implements ISpiderReportable{
	
	Connection connection;
	PreparedStatement prepSetStatus;
	
	private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpeg|jpg|png|tiff))$");
	private static final Pattern otherPatterns = Pattern.compile(".*(\\.(js|css|flv|mp4))$");
	private static String score;
	
	public ImageReportable(String url,String driverName,String jdbcurl,String userName,String password) throws ClassNotFoundException, SQLException{
		if(url!=null){
			url = url.toLowerCase();
			if(url.startsWith("http://")){
				url = url.substring(7);
			}
			if(url.startsWith("www.")){
				url = url.substring(4);
			}
			int index = url.indexOf("/");
			if(index!=-1){
				url = url.substring(0,index);
			}
			score = url;
			System.out.println("score:"+url);
			
		}
		Class.forName(driverName);
	    connection = DriverManager.getConnection(jdbcurl,userName,password);
	    prepSetStatus =  connection.prepareStatement("INSERT INTO images_url(url,status) VALUES (?,?);");
	}

	@Override
	public boolean foundInternalLink(String url) {
		System.out.println("foundInternalLink:"+url);
		if(otherPatterns.matcher(url.toLowerCase()).matches()){
			return false;
		}
		if(imgPatterns.matcher(url.toLowerCase()).matches()){
			try {
				prepSetStatus.setString(1,url);
				prepSetStatus.setString(2,"W");
			    prepSetStatus.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		return true;
	}

	@Override
	public boolean foundExternalLink(String url) {
		System.out.println("foundExternalLink:"+url);
		if(otherPatterns.matcher(url.toLowerCase()).matches()){
			return false;
		}
		if(url.toLowerCase().indexOf(score)!=-1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean foundOtherLink(String url) {
		System.out.println("foundOtherLink:"+url);
		return false;
	}

	@Override
	public void processPage(HTTP page) {
		System.out.println("processPage:"+page.getURL());
		
	}

	@Override
	public void completePage(HTTP page, boolean error) {
		System.out.println("completePage:"+page.getURL());
	}

	@Override
	public boolean getRemoveQuery() {
		System.out.println("getRemoveQuery:");
		return false;
	}

	@Override
	public void spiderComplete() {
		System.out.println("spiderComplete:");
		
	}
	
	
	
	public static void main(String[] args) {
		
		
		
	}

}
