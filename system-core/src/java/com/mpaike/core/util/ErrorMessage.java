package com.mpaike.core.util;

import java.io.Serializable;
import java.util.HashMap;

public class ErrorMessage extends HashMap
implements Serializable
{
	  private static final String ERROR_MESSAGE_FIELD = "errorMessage";
	  private static final String SUGGESTED_VALUE_FIELD = "suggestedValue";

	  public void setErrorString(String errorString)
	  {
	    put(ERROR_MESSAGE_FIELD, errorString);
	  }

	  public String getErrorString()
	  {
	    return ((String)get(ERROR_MESSAGE_FIELD));
	  }

	  public void setSuggestedValue(Object suggestedValue)
	  {
	    if (suggestedValue != null) put(SUGGESTED_VALUE_FIELD, suggestedValue);
	  }

	  public Object getSuggestedValue()
	  {
	    return get(SUGGESTED_VALUE_FIELD);
	  }

	  public ErrorMessage()
	  {
	  }

	  public ErrorMessage(String errorString)
	  {
	    put(ERROR_MESSAGE_FIELD, errorString);
	  }

	  public ErrorMessage(String errorString, Object suggestedValue)
	  {
	    put(ERROR_MESSAGE_FIELD, errorString);
	    if (suggestedValue != null) put(SUGGESTED_VALUE_FIELD, suggestedValue);
	  }
}