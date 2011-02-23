package com.mpaike.xfire;

import java.net.URL;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

public class TestClient {

	public static void main(String[] args)

	{
		try {
			String url = "http://127.0.0.1:8080/icore/services/test";

			Client c = new Client(new URL(url + "?wsdl"));
			c.invoke("remotePrint", new Object[] { "方法1打印" });

			Service serviceModel = new ObjectServiceFactory()
					.create(Test.class);
			XFireProxyFactory factory = new XFireProxyFactory(XFireFactory
					.newInstance().getXFire());
			System.out.println("-----------");
			Test client = (Test) factory.create(serviceModel, url);
			client.remotePrint("方法2打印");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
