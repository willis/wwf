package com.mpaike.bot.spider;
import java.util.Vector;

import com.mpaike.util.bot.Attribute;
import com.mpaike.util.bot.HTMLForm;
import com.mpaike.util.bot.HTMLPage;
import com.mpaike.util.bot.HTTP;
import com.mpaike.util.bot.HTTPSocket;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class TestForms {

    /** Creates a new instance of TestForms */
    public TestForms() {
    }

    public static void test() {
        try {
            System.out.print("Forms test:");
            //setup connection
            HTTP http=new HTTPSocket();
            http.setTimeout(60000);
            http.setUseCookies(true,true);

            HTMLPage page=new HTMLPage(http);
            page.open("http://bbs.myniko.com/login.jsp",null);

            //let's get the forms
            Vector vec = page.getForms();
            if( vec.size()!=1 )
            {
                System.out.println("Invalid number of forms");
                return;
            }

            HTMLForm form = (HTMLForm)page.getForms().elementAt(0);

            if( !form.getMethod().equals("post") )
            {
                System.out.println("Invalid form method");
                return;
            }

            if( !form.getAction().equals("http://bbs.myniko.com/login.jsp") )
            {
                System.out.println("Invalid form action:" + form.getAction() );
                return;
            }

            Attribute field1 = form.get("username");
            Attribute field2 = form.get("password");

            if( field1==null )
            {
                System.out.println("Missing username");
                return;
            }

            if( field2==null )
            {
                System.out.println("Missing password");
                return;
            }

            if( !field1.getValue().equals("default1") )
            {
                System.out.println("Failed to get default value for field1:" + form.get("field1") );
                return;
            }

            if( !field2.getValue().equals("default2") )
            {
                System.out.println("Failed to get default value for field2");
                return;
            }

            field1.setValue("new1");
            field2.setValue("new2");

            page.post(form);

            String str = page.getHTTP().getBody();
            if( str.indexOf("new1")==-1 || str.indexOf("new2")==-1 )
            {
                System.out.println("Failed to get response on post");
                return;
            }

            System.out.println("Success");


        }

        catch (Exception e) {
            System.out.println("error " + e);
        }

    }

}
