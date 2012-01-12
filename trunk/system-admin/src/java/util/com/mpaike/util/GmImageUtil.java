package com.mpaike.util;

import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class GmImageUtil {

	/**
	 * @param args
	 * @throws IM4JavaException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException, IM4JavaException {
		cropImageCenter("/Users/tozhangwj/Documents/515x170-02.jpg","/Users/tozhangwj/Documents/crop515x170-02.jpg",100,100);
		scale("/Users/tozhangwj/Documents/515x170-02.jpg","/Users/tozhangwj/Documents/scale515x170-02.jpg",100,100);
	}
	
	/** 
     * 先缩放，后居中切割图片 
     * @param srcPath 源图路径 
     * @param desPath 目标图保存路径 
     * @param rectw 待切割在宽度 
     * @param recth 待切割在高度 
     * @throws IM4JavaException  
     * @throws InterruptedException  
     * @throws IOException  
     */  
    public static void cropImageCenter(String srcPath, String desPath, int rectw, int recth) throws IOException, InterruptedException, IM4JavaException  
    {  
        IMOperation op = new IMOperation();  
          
        op.addImage();
        op.resize(rectw, recth, '^').gravity("Center").extent(rectw, recth);
        op.quality(80d);
        op.addImage();
  
        ConvertCmd convert = new ConvertCmd(true);
        ConvertCmd.setGlobalSearchPath("/usr/local/bin/");

        convert.run(op, srcPath, desPath);  

    }
    
	/** 
     * 先缩放，后居中切割图片 
     * @param srcPath 源图路径 
     * @param desPath 目标图保存路径 
     * @param width 最大宽度 
     * @param height 最大高度 
     * @throws IM4JavaException  
     * @throws InterruptedException  
     * @throws IOException  
     */  
    public static void scale(String srcPath, String desPath, int maxWidth,int maxHeight) throws IOException, InterruptedException, IM4JavaException  
    {  
        IMOperation op = new IMOperation();  
          
        op.addImage();
        op.resize(maxWidth, maxHeight);
        op.addImage();
  
        ConvertCmd convert = new ConvertCmd(true);
        ConvertCmd.setGlobalSearchPath("/usr/local/bin/");

        convert.run(op, srcPath, desPath);  
    }
    


}
