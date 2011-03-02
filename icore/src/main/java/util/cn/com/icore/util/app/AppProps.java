package cn.com.icore.util.app;

import java.util.Properties;

public class AppProps {
	
	public static final String ID_NAME = "appProps";
	private Properties properties;

	public AppProps()
	{
	}

	public void setProperties(Properties properties)
	{
		this.properties = properties;
	}

	public Object get(Object key)
	{
		return properties.get(key);
	}

	public Object put(Object key, Object value)
	{
		return properties.put(key, value);
	}

	public Object remove(Object key)
	{
		return properties.remove(key);
	}

	public Properties getProperties()
	{
		return properties;
	}

}
