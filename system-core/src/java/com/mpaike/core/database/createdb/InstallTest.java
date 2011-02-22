package com.mpaike.core.database.createdb;

import junit.framework.TestCase;

public class InstallTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	public void testCreateDb(){
		try {
			Install.createDb(Install.POSTGRESQL,"127.0.0.1", "5432", "InstallTest", "postgres", "postgres",true);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
