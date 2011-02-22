package com.mpaike.core.util.xml;

import java.util.HashMap;
import java.util.Map;

import com.mpaike.core.util.ErrorMessage;

public class Validator {
	
	static final Map defaultValidators;
	
	static{
		//通过配置文件装载验证函数
	    defaultValidators = new HashMap();

	}
}

abstract interface ValidatorFunc{
	  public abstract ErrorMessage validate(Object value, String filedName, Map record);
}

