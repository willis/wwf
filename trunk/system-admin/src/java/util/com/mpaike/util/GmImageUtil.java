package com.mpaike.util;

import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class GmImageUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
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
        op.resize(rectw, recth, '^').gravity("center").extent(rectw, recth);  
        op.addImage();  
  
        ConvertCmd convert = new ConvertCmd(true);  
        //convert.createScript("e:\\test\\myscript.sh",op);  
        convert.run(op, srcPath, desPath);  
  
    }  

}
