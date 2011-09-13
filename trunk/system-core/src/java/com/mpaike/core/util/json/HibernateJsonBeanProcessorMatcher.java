package com.mpaike.core.util.json;

import java.util.Set;

import net.sf.json.processors.JsonBeanProcessorMatcher;


public class HibernateJsonBeanProcessorMatcher extends JsonBeanProcessorMatcher {   
  
	@Override  
	public Object getMatch(Class target, Set set) {   
	 
	   if (target.getName().contains("$$EnhancerByCGLIB$$")||target.getName().contains("$$_javassist")) {   
	        //log.warn("Found Lazy-References in Hibernate object " + target.getName()); 
	        return DEFAULT.getMatch(org.hibernate.proxy.HibernateProxy.class, set);
	   }   
	   return DEFAULT.getMatch(target, set);   
	}   
}  