package com.mpaike.util.webp;

import java.io.IOException;
import java.io.InputStream;

public class WebPUtil {
	
	private static String libpath;
	
	static{
		String webpath = WebPUtil.class.getResource("/").getPath();
		libpath = webpath.substring(0,webpath.lastIndexOf("classes/"));
		System.out.println(libpath);
	}
	
	
	public static void encodeWebP(String destPath,String webpPath,boolean infoPrint){
		Process process;
		try {
			process = Runtime.getRuntime().exec(libpath+"webp/macos/cwebp -q 80 "+destPath+" -o "+webpPath);
			InputStream in = process.getInputStream();  
			StringBuilder info = new StringBuilder();  
			int a;  
			while ((a = in.read()) != -1) {  
			    info.append((char) a);  
			} 
			InputStream errin = process.getErrorStream();  
			StringBuilder errInfo = new StringBuilder();  
			while ((a = errin.read()) != -1) {  
				errInfo.append((char) a);  
			} 
			if(infoPrint){
				System.out.println(info);
			}
			if(infoPrint){
				System.out.println(errInfo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static void main(String[] argv){
		encodeWebP(libpath+"webp/537C0F58B007F87090ACB1DE11D97B74.jpg",libpath+"webp/test.webp",true);
	}
	
}
