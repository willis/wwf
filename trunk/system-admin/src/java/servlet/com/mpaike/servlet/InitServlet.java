package com.mpaike.servlet;

import java.io.IOException;

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
		
		WebApplicationContext wac = WebApplicationContextUtils
				.getRequiredWebApplicationContext(config.getServletContext());
		ApplictionContext.getInstance().setAppContext(wac);
		IndexEngineBuild.setIndexingSystem((ZoieSystem)wac.getBean("indexingSystem"));
		System.out.println(config.getServletContext().getRealPath(""));
		ApplicationContextUtil.getInstance().setAppContext(wac);
		
		System.out.println(IndexEngineBuild.getIndexingSystem());
		
		System.out.println("＊＊＊＊＊＊重建基因开始＊＊＊＊＊");
		IGeneService db = (IGeneService)wac.getBean("geneService");
		//db.tagSqlToRedis();
		System.out.println("＊＊＊＊＊＊重建基因完成＊＊＊＊＊");
		
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
