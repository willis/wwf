package com.mpaike.core.util.json;

import net.sf.json.util.PropertyFilter;

/**
 * 
 * @author niko
 *
 */
public class NullPropertyFilter implements PropertyFilter {

	@Override
	   public boolean apply( Object source, String name, Object value ) {   
	     if(value == null ){   
	         return true;   
	      }
	      return false;
	   }   

}
