package com.mpaike.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.im4java.core.IM4JavaException;

public class PicScaleUtil {
	
	private static List<ScaleType> zoomList = new ArrayList<ScaleType>();
	static{
		zoomList.add(new ScaleType(150,150,ScaleType.TYPE_SCALE));
		zoomList.add(new ScaleType(85,85,ScaleType.TYPE_CROP));
//		zoomList.add(new ScaleType("240X320-jpg"));
//		zoomList.add(new ScaleType("240X320-webp"));
//		zoomList.add(new ScaleType("320X240-jpg"));
//		zoomList.add(new ScaleType("320X240-webp"));
	}
	
	public static void zoomForZoomList(String filenamePath){
		File file = new File(filenamePath);
		try {
			for(ScaleType st : zoomList){
				if(ScaleType.TYPE_CROP == st.getType()){
					GmImageUtil.scale(filenamePath, new StringBuilder().append(file.getParentFile().getAbsolutePath()).append("/").append(st.width).append("X").append(st.height).append("(crop)").append("-").append(file.getName()).toString(), st.width, st.height);
				}else if(ScaleType.TYPE_SCALE == st.getType()){
					GmImageUtil.scale(filenamePath, new StringBuilder().append(file.getParentFile().getAbsolutePath()).append("/").append(st.width).append("X").append(st.height).append("(scale)").append("-").append(file.getName()).toString(), st.width, st.height);
				}

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
		File file = new File(filenamePath);
		try {
			GmImageUtil.scale(filenamePath, new StringBuilder().append(file.getParentFile().getParentFile()).append("/").append(width).append("X").append(height).append("-").append(file.getName()).toString(), width, height);
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
	
	static class ScaleType{
		
		public static final int TYPE_CROP = 0;
		public static final int TYPE_SCALE = 1;
		
		private int width;
		private int height;
		private int type;
		
		public ScaleType(){}
		
		public ScaleType(String p){
			width = Integer.valueOf(p.substring(0,p.indexOf("X")));
			height = Integer.valueOf(p.substring(p.indexOf("X"),p.indexOf("-")));
			type = Integer.valueOf(p.substring(p.indexOf("-")));
		}
		
		public ScaleType(int width,int height,int type){
			this.width = width;
			this.height = height;
			this.type = type;
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

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}


	}

}
