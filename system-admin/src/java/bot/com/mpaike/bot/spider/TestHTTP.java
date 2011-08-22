package com.mpaike.bot.spider;
import com.mpaike.util.bot.HTTPException;
import com.mpaike.util.bot.HTTPSocket;
/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class TestHTTP {

    /** Creates a new instance of TestHTTP */
    static public void test() {
        try
        {
            HTTPSocket http = new HTTPSocket();
            System.out.print("HTTP Test:");
            http.send("http://www.myniko.com/",null);
            String result = http.getBody();
            if( !result.trim().equals("Bot testbed") )
            {
                System.out.println("Failed to get correct response:" + result );
            }

            // now test an error
            try
            {
                http.send("http://www.myniko.com/index.html",null);
            }
            catch(HTTPException e)
            {
                System.out.println( e );
            }
            System.out.println("Success");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
