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

import proj.zoie.impl.indexing.ZoieSystem;

import cn.vivame.v2.gene.service.IGeneService;

import com.mpaike.client.zoie.service.IndexEngineBuild;
import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.ApplicationContextUtil;
import com.mpaike.util.app.ApplictionContext;

/**
 * Servlet implementation class init
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InitServlet() {
		super();

	}

	/**
	 * 初始系统
	 * 
	 * @param config
	 *            ServletConfig
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		String configFile=this.getClass().getResource("/init.properties").getFile();
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
		IndexEngineBuild.setIndexingSystem((ZoieSystem)wac.getBean("indexingSystem"));
		System.out.println(config.getServletContext().getRealPath(""));
		ApplicationContextUtil.getInstance().setAppContext(wac);
		
		System.out.println(IndexEngineBuild.getIndexingSystem());
		
		
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
