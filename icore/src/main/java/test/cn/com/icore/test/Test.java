package cn.com.icore.test;



import cn.com.icore.util.MD5;
import junit.framework.TestCase;

public class Test extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	public void testCreateDb(){
		try {
			  MD5 md5 = new MD5();
		      System.out.println(md5.getMD5ofStr("123"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
