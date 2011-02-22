package com.mpaike.core.util.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.springframework.util.ResourceUtils;

import com.mpaike.core.util.classloader.ClassLoaderUtil;

public class JarUtil {
	
	public static List<String> jarFile(String entryName) throws Exception{
		
		List<String> jarList = new ArrayList<String>();
		
		URL url = ClassLoaderUtil.getResource("com/ebizserve/core/util/resource/",JarUtil.class);
		
		url = ResourceUtils.extractJarFileURL(url);
		
		String pathString = url.getPath().substring(0, url.getPath().lastIndexOf("/"));

		File file = new File(pathString);
		
		String[] list = file.list();
		StringBuilder sb;
		JarFile jarFile;
		JarEntry jarEntry;
		for(String jarName : list){
			if(jarName.endsWith("jar")){
				sb = new StringBuilder();
				sb.append(pathString);
				sb.append("/").append(jarName);
				//System.out.println(sb);
				 jarFile = new JarFile(sb.toString());
				 jarEntry = jarFile.getJarEntry(entryName);
				 if(jarEntry!=null){
					 jarList.add(sb.toString());
				 }
			}
		}
		return jarList;
	}
	
	public static List<String> jarFile() throws Exception{
		
		List<String> jarList = new ArrayList<String>();
		
		URL url = ClassLoaderUtil.getResource("com/ebizserve/core/util/resource/",JarUtil.class);
		
		url = ResourceUtils.extractJarFileURL(url);
		
		String pathString = url.getPath().substring(0, url.getPath().lastIndexOf("/"));

		File file = new File(pathString);
		
		StringBuilder sb;
		String[] list = file.list();
		for(int i=0,n=list.length;i<n;i++){
			if(list[i].endsWith("jar")){
				sb = new StringBuilder();
				sb.append(pathString);
				sb.append("/").append(list[i]);
				jarList.add(sb.toString());
			}
		}
		return jarList;
	}
	
	public static String readerJarEntry(JarFile jarFile,JarEntry jarEntry){
		
	    String record = null;
	    StringBuffer allrecord = new StringBuffer();
	    int recCount = 0;
	    try {
	    	InputStream is = jarFile.getInputStream(jarEntry);
	    	InputStreamReader isr = new InputStreamReader(is, "UTF-8");
	    	BufferedReader br = new BufferedReader(isr);
	    	record = new String();
	    	while ((record = br.readLine()) != null) {
	    		recCount++;
	    		allrecord.append(record);
	    	}
	    	br.close();
	    	isr.close();
	    	is.close();
	   } catch (IOException e) {
	     e.printStackTrace();
	   }
	   return allrecord.toString();
	}

}
