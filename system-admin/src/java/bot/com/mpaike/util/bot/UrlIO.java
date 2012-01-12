package com.mpaike.util.bot;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import com.mpaike.core.util.data.FastByteArrayOutputStream;

public class UrlIO {
	
	public final static boolean DEBUG = true; //调试用
	private static int BUFFER_SIZE = 8096; //缓冲区大小
	
	/**
     * 将HTTP资源另存为文件
     *
     * @param destUrl String
     * @param fileName String
     * @throws Exception
     */
    public static String webAccess(String destUrl) {
        BufferedReader br = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        StringBuilder sb = new StringBuilder();
        //建立链接
        try {
			url = new URL(destUrl);

        httpUrl = (HttpURLConnection) url.openConnection();
        //连接指定的资源
        httpUrl.connect();
        //获取网络输入流
        br = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));
        //检测
        
        String line; 
        while((line = br.readLine())!=null){ 
        		sb.append(line);
        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(httpUrl!=null){
				httpUrl.disconnect();
			}
		}
        return sb.toString();
    }
	
	/**
     * 将HTTP资源另存为文件
     *
     * @param destUrl String
     * @param fileName String
     * @throws Exception
     */
    public static void saveToFile(String destUrl, String fileName) throws IOException {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
       

        //建立链接
        url = new URL(destUrl);
        httpUrl = (HttpURLConnection) url.openConnection();
        //连接指定的资源
        httpUrl.connect();
        //获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());
        //建立文件
        fos = new FileOutputStream(fileName);

        if (DEBUG)
            System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" +
                               fileName + "]");
        //保存文件
        while ((size = bis.read(buf)) != -1)
            fos.write(buf, 0, size);

        fos.close();
        bis.close();
        httpUrl.disconnect();
    }

 
	/**
     * 将HTTP资源另存为文件
     *
     * @param destUrl String
     * @param fileName String
     * @throws Exception
     */
    public static byte[] imageToFile(String destUrl, String fileName,int minWidth,int minHeight) throws IOException {

        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] bytes;
        byte[] buf = new byte[BUFFER_SIZE];;
        int size = 0; 
        boolean isSave = false;
       
        //建立链接
        url = new URL(destUrl);
        httpUrl = (HttpURLConnection) url.openConnection();
        //连接指定的资源
        httpUrl.connect();
        //获取网络输入流
        bis = new BufferedInputStream(httpUrl.getInputStream());
        //读取图像到内存
        FastByteArrayOutputStream fbao = new FastByteArrayOutputStream();
        while ((size = bis.read(buf)) != -1){
        		fbao.write(buf, 0, size);
        }
        bytes = fbao.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        
        BufferedImage image = ImageIO.read(is);
		if(image.getWidth()<minWidth||image.getHeight()<minHeight){
			isSave = false;
		}else{
			File file = new File(fileName);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			OutputStream fos = new FileOutputStream(fileName);
			fos.write(bytes);
			fos.close();
			System.out.println("Save image	:"+destUrl+" ");
			isSave = true;
		}
		is.close();
		fbao.close();
        bis.close();
        httpUrl.disconnect();
        if(isSave){
        		return bytes;
        }else{
        		return null;
        }
    }
    
    /**
        * 将HTTP资源另存为文件
        *
        * @param destUrl String
        * @param fileName String
        * @throws Exception
        */
       public static void saveToFile(String destUrl, String fileName, String username,String password) throws IOException {
           FileOutputStream fos = null;
           BufferedInputStream bis = null;
           HttpURLConnection httpUrl = null;
           URL url = null;
           byte[] buf = new byte[BUFFER_SIZE];
           int size = 0; 
         //建立链接
           url = new URL(destUrl);
           httpUrl = (HttpURLConnection) url.openConnection();
       
           //String authString = "username" + ":" + "password";
            String authString = username + ":" + password;
           String auth = "Basic " +
                         new sun.misc.BASE64Encoder().encode(authString.getBytes());
           httpUrl.setRequestProperty("Proxy-Authorization", auth);
       
           //连接指定的资源
           httpUrl.connect();
           //获取网络输入流
           bis = new BufferedInputStream(httpUrl.getInputStream());
           //建立文件
           fos = new FileOutputStream(fileName);
   
           if (DEBUG)
               System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" +
                                  fileName + "]");
           //保存文件
           while ((size = bis.read(buf)) != -1)
               fos.write(buf, 0, size);
   
           fos.close();
           bis.close();
           httpUrl.disconnect();
       }
    /**
     * 设置代理服务器
     *
     * @param proxy String
     * @param proxyPort String
     */
    public static void setProxyServer(String proxy, String proxyPort) {
    		//设置代理服务器
        System.getProperties().put("proxySet", "true");
        System.getProperties().put("proxyHost", proxy);
        System.getProperties().put("proxyPort", proxyPort);
    }
    
    public static void main(String[] argv) throws IOException{
    		System.out.print(webAccess("http://www.sina.com.cn"));
    }
    

}
