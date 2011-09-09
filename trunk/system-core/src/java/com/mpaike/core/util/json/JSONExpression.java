package com.mpaike.core.util.json;

import java.io.Serializable;

import net.sf.json.JSONFunction;

/**
 * 
 * @author niko
 *
 */
public class JSONExpression extends JSONFunction implements Serializable {

	public JSONExpression(String text) {
		super(text);
	}

	/**
	    * Returns the string representation of this function.
	    */
	   public String toString() {
	      StringBuffer b = new StringBuffer();
	      if( getText().length() > 0 ){
	         b.append( ' ' )
	               .append( getText() )
	               .append( ' ' );
	      }
	      return b.toString();
	   }
	
}
