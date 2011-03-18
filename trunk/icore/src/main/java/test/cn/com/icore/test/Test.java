package cn.com.icore.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import cn.com.icore.util.MD5;
import junit.framework.TestCase;

public class Test extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	static void f(int required,String... trailing){
		System.out.print("required : "+required+" ");
		for(String s : trailing){
			
			System.out.print(s+" ");
		}
		System.out.println();
	}
	
	
	public void netTest() throws IOException{
//		URL url = new URL("http://www.ekang120.com"); 
//		URLConnection connection = url.openConnection(); 
//		InputStream in = connection.getInputStream(); 
//		
//		BufferedReader br = new BufferedReader(new InputStreamReader(in, "gbk"));
//
//		String tempbf;
//		
//		while ((tempbf = br.readLine()) != null) {
//			System.out.println(tempbf);
//		}
//		in.close(); 
		


	}

	public void testCreateDb() {
		try {
			MD5 md5 = new MD5();
			System.out.println(md5.getMD5ofStr("kangruiqing"));
			int x = 5;
			double y = 5.332542;
			// The old way
			System.out.println("Row 1: [" + x + " " + y + "]");
			// The new way
			System.out.format("Row 1: [%d %f]\n", x, y);
			// or
			System.out.printf("Row 1: [%d %f]\n", x, y);
			
			f(1,"one");
			f(2,"two","three");
			f(0);
			netTest();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
