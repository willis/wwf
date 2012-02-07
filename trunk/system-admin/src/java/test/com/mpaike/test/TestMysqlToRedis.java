package com.mpaike.test;

import org.springframework.context.support.AbstractApplicationContext;

import cn.vivame.v2.gene.service.IGeneService;

import com.mpaike.core.exception.ParameterException;
import com.mpaike.core.util.ApplicationContextUtil;
import com.mpaike.util.app.ApplictionContext;

public class TestMysqlToRedis {

	/**
	 * @param args
	 * @throws ParameterException 
	 */
	public static void main(String[] args) throws ParameterException {
		
		AbstractApplicationContext ac = ApplicationContextUtil.creatContext();
		ApplictionContext.getInstance().setAppContext(ac);
		IGeneService db = (IGeneService)ac.getBean("geneService");

		db.tagSqlToRedis();

	}

}
