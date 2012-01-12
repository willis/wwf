package com.mpaike.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.im4java.core.IM4JavaException;

public class PicScaleUtil {
	
	private static List<ScaleType> zoomList = new ArrayList<ScaleType>();
	{
		zoomList.add(new ScaleType(240,320,"jpg"));
		zoomList.add(new ScaleType(320,240,"jpg"));

//		zoomList.add(new ScaleType("240X320-jpg"));
//		zoomList.add(new ScaleType("240X320-webp"));
//		zoomList.add(new ScaleType("320X240-jpg"));
//		zoomList.add(new ScaleType("320X240-webp"));
	}
	
	public static void zoomForZoomList(String filenamePath){
		String distPath = filenamePath.substring(0,filenamePath.lastIndexOf("/"));
		String filename = filenamePath.substring(filenamePath.lastIndexOf("/"));
		try {
			for(ScaleType st : zoomList){

					GmImageUtil.scale(filenamePath, new StringBuilder().append(distPath).append("/").append(st.width).append("X").append(st.height).append("-").append(filename).toString(), st.width, st.height);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IM4JavaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void zoom(String filenamePath,int width,int height,String picType){
		String distPath = filenamePath.substring(0,filenamePath.lastIndexOf("/"));
		String filename = filenamePath.substring(filenamePath.lastIndexOf("/"));
		try {
			GmImageUtil.scale(filenamePath, new StringBuilder().append(distPath).append("/").append(width).append("X").append(height).append("-").append(filename).toString(), width, height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IM4JavaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	class ScaleType{
		
		private int width;
		private int height;
		private String picType;
		
		public ScaleType(){}
		
		public ScaleType(String p){
			width = Integer.valueOf(p.substring(0,p.indexOf("X")));
			height = Integer.valueOf(p.substring(p.indexOf("X"),p.indexOf("-")));
			picType = p.substring(p.indexOf("-"));
		}
		
		public ScaleType(int width,int height,String picType){
			this.width = width;
			this.height = height;
			this.picType = picType;
		}
		
		public int getWidth() {
			return width;
		}
		public void setWidth(int width) {
			this.width = width;
		}
		public int getHeight() {
			return height;
		}
		public void setHeight(int height) {
			this.height = height;
		}
		public String getPicType() {
			return picType;
		}
		public void setPicType(String picType) {
			this.picType = picType;
		}
		
		
	}

}
