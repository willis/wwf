package cn.com.icore.util.tag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;
import org.apache.commons.beanutils.PropertyUtils;
@SuppressWarnings("unchecked")
public class TagUtil {
	
	private static TagUtil me = new TagUtil();
	
	private static final Map scopes;

	private TagUtil()
	{
	}

	public static TagUtil getInstance()
	{
		return me;
	}

	public int getScope(String scopeName)
	{
		Integer scope = (Integer)scopes.get(scopeName.toLowerCase());
		return scope.intValue();
	}

	public Object lookup(PageContext pageContext, String name, String scopeName)
	{
		if (scopeName == null)
			return pageContext.findAttribute(name);
		else
			return pageContext.getAttribute(name, getScope(scopeName));
	}

	public Object lookup(PageContext pageContext, String name, String property, String scopeName)
		throws Exception
	{
		Object bean = lookup(pageContext, name, scopeName);
		return PropertyUtils.getProperty(bean, property);
	}

	static 
	{
		scopes = new HashMap();
		scopes.put("page", new Integer(1));
		scopes.put("request", new Integer(2));
		scopes.put("session", new Integer(3));
		scopes.put("application", new Integer(4));
	}

}
