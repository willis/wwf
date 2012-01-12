package com.mpaike.bot.spider;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import com.mpaike.core.database.hibernate.SequenceManager;
import com.mpaike.core.util.date.DateTimeUtil;
import com.mpaike.core.util.resource.FileUtil;
import com.mpaike.image.dao.IPictureDao;
import com.mpaike.image.model.Picture;
import com.mpaike.util.ExifHelper;
import com.mpaike.util.MD5;
import com.mpaike.util.PicScaleUtil;
import com.mpaike.util.bot.HTTP;
import com.mpaike.util.bot.ISpiderReportable;
import com.mpaike.util.bot.Log;
import com.mpaike.util.bot.UrlIO;


public class ImageReportable implements ISpiderReportable{
	
	private String currentUrl = "";
	
	PreparedStatement prepAssign;
	PreparedStatement prepSetStatus;

	private IPictureDao pictureDao;
	private Connection connection;
	private  String imagesPath;
	private String enname;
	private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpeg|jpg|png|tiff))$");
	private static final Pattern otherPatterns = Pattern.compile(".*(\\.(js|css|flv|mp4|doc|docx|mp3|mov|zip|rar|gz|tar))$");
	private static String score;
	private static String sourceUrl;
	
	public ImageReportable(String url,String path,String enname,Connection connection,IPictureDao pictureDao) throws ClassNotFoundException, SQLException{
		imagesPath = path;
		this.enname = enname;
		if(url!=null){
			this.sourceUrl = url;
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
		this.pictureDao = pictureDao;
		this.connection = connection;
	    prepSetStatus =  connection.prepareStatement("INSERT INTO bot_images(id,score,url,filename,status) VALUES (?,?,?,?,?);");
	    prepAssign = connection.prepareStatement("SELECT count(*) as qty FROM bot_images WHERE id = ?;");

	}

	@Override
	public boolean foundInternalLink(String url) {
		currentUrl = url;
		if(otherPatterns.matcher(url.toLowerCase()).matches()){
			return false;
		}
		if(imgPatterns.matcher(url.toLowerCase()).matches()){
			saveImage(url);
			return false;
		}
		return true;
	}

	@Override
	public boolean foundExternalLink(String url) {
		currentUrl = url;
		if(otherPatterns.matcher(url.toLowerCase()).matches()){
			return false;
		}
		if(imgPatterns.matcher(url.toLowerCase()).matches()){
			saveImage(url);
		}
		if(url.toLowerCase().indexOf(score)!=-1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean foundOtherLink(String url) {
		//System.out.println("foundOtherLink:"+url);
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
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("spiderComplete:");
		
	}
	
	
	private void saveImage(String url){
	    ResultSet rs = null;
	    StringBuilder tagertName=null;
	    String path = null;
	    String abspath = null;
	    String filename = null;
	    String id =null;
	    Picture pic = null;
	    Date date =null;
	    try {
	      // first see if one exists
	    	prepAssign.setString(1,MD5.toMD5(url));
	      rs = prepAssign.executeQuery();
	      rs.next();
	      int count = rs.getInt("qty");

	      if ( count<1 ) {// Create one
	    	  	id = MD5.toMD5(url);
	    	  	date = new Date();
	    	  	path = new StringBuilder().append(enname).append("/").append(DateTimeUtil.getTime(date.getTime())).append("/").toString();
	    	  	abspath = new StringBuilder().append(imagesPath).append(path).toString();
	    	  	//创建目录
	    	  	mkdir(abspath.toString());
	    	  	filename = new StringBuilder().append(id).append(".").append(FileUtil.getTypePart(url)).toString();
	    	  	
	    	  	tagertName = new StringBuilder().append(abspath).append(filename);

	    	  	byte[] bytes = UrlIO.imageToFile(url, tagertName.toString(), 500, 500);
	        if(bytes!=null){
	        	prepSetStatus.setString(1,id);
	        	prepSetStatus.setString(2,score);
		        prepSetStatus.setString(3,url);
		        prepSetStatus.setString(4,tagertName.toString());
		        prepSetStatus.setString(5,"W");
		        prepSetStatus.executeUpdate();
		        pic = ExifHelper.getPicture(url,path,filename,date, bytes);
		        if(pic!=null){
		        		pic.setId(SequenceManager.nextID(100));
		        		pic.setSourceName(sourceUrl);
		        		pic.setUserId(-1L);
		        		pic.setType(Picture.TYPE_NORMAL);
		        		pic.setFilename(filename);
		        		pic.setPath(path);
		        		PicScaleUtil.zoomForZoomList(tagertName.toString());
		        		pictureDao.saveNow(pic);
		        }else{
		        	System.err.println("Exif信息获取错误。（"+url+")");
		        }
	        }
	      } 
	    } catch ( SQLException e ) {
	    		Log.logException("SQL Error: ",e );
	    } catch (IOException e) {
	    		Log.logException("ImageIO Error: ",e );
			e.printStackTrace();
		} finally {
	      try {
	        if ( rs!=null )
	          rs.close();
	      } catch ( Exception e ) {
	      }
	    }
	}
	
	private void mkdir(String path){
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public static void main(String[] args) {
		
		
		
	}

}
