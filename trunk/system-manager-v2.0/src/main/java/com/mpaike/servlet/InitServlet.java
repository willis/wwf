package com.mpaike.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.mpaike.core.util.ApplicationContextUtil;
import com.mpaike.util.app.ApplictionContext;

/**
 * Servlet implementation class init
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public InitServlet() {
		super();

	}

	/**
	 * 初始系统
	 * 
	 * @param config
	 *            ServletConfig
	 * @throws javax.servlet.ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String configFile=this.getClass().getResource("/application.properties").getFile();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		Properties p = new Properties();
		try {
			fis = new FileInputStream(configFile);
			isr = new InputStreamReader(fis, "UTF-8");
			p.load(isr);
			fis.close();
			isr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//初始化爬取资源存放路径
		String botspider_path = p.getProperty("botspider.path");
		if(botspider_path!=null){
			try{
				new File(botspider_path).mkdirs();
			}catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		ApplictionContext.getInstance().setAppContext(wac);
		System.out.println(config.getServletContext().getRealPath(""));
		ApplicationContextUtil.getInstance().setAppContext(wac);
		

		
	}

	/**
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
