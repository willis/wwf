package com.mpaike.xfire;

public class TestImpl implements Test{
	
	  public void remotePrint(String s)
	  {
	    System.out.println(s);
	  }
	  public String remoteGet() {
	    return " remote string ";
	  }

}
