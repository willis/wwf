/*
 * Copyright (C) 2009-2010 WWF Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in WWF's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.42y.net/legal/licensing"
 */
package com.mpaike.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Static Helper methods for instantiating objects from reflection.
 */
public class ReflectionHelper
{
    private static Log logger = LogFactory.getLog(ReflectionHelper.class);
    
    private ReflectionHelper()
    {
    }

    public static Class forName(String className)
    {
    	Class clazz = null;
        try
        {
            clazz = Class.forName(className);
        }
        catch (ClassNotFoundException cnfe)
        {
            logger.debug(cnfe);
        }
        return clazz;
    }
    
    /**
     * Constructs a new object for the given class name.
     * The construction takes no arguments.
     * 
     * If an exception occurs during construction, null is returned.
     * 
     * All exceptions are written to the Log instance for this class.
     * 
     * @param className
     * @return
     */
    public static Object newObject(String className)
    {
        Object o = null;

        try
        {
            Class clazz = Class.forName(className);
            o = clazz.newInstance();
        }
        catch (ClassNotFoundException cnfe)
        {
            logger.debug(cnfe);
        }
        catch (InstantiationException ie)
        {
            logger.debug(ie);
        }
        catch (IllegalAccessException iae)
        {
            logger.debug(iae);
        }
        return o;
    }

    /**
     * Constructs a new object for the given class name and with the given
     * arguments.  The arguments must be specified in terms of their Class[]
     * types and their Object[] values.
     * 
     * Example:
     * 
     *   String s = newObject("java.lang.String", new Class[] { String.class},
     *              new String[] { "test"});
     *              
     * is equivalent to:
     * 
     *   String s = new String("test");
     * 
     * If an exception occurs during construction, null is returned.
     * 
     * All exceptions are written to the Log instance for this class.

     * @param className
     * @param argTypes
     * @param args
     * @return
     */
    public static Object newObject(String className, Class[] argTypes, Object[] args)
    {
        /**
         * We have some mercy here - if they called and did not pass in any
         * arguments, then we will call through to the pure newObject() method.
         */
        if (args == null || args.length == 0)
        {
            return newObject(className);
        }

        /**
         * Try to build the object
         * 
         * If an exception occurs, we log it and return null.
         */
        Object o = null;
        try
        {
            // base class
            Class clazz = Class.forName(className);

            Constructor c = clazz.getDeclaredConstructor(argTypes);
            o = c.newInstance(args);
        }
        catch (ClassNotFoundException cnfe)
        {
            logger.debug(cnfe);
        }
        catch (InstantiationException ie)
        {
            logger.debug(ie);
        }
        catch (IllegalAccessException iae)
        {
            logger.debug(iae);
        }
        catch (NoSuchMethodException nsme)
        {
            logger.debug(nsme);
        }
        catch (InvocationTargetException ite)
        {
            logger.debug(ite);
        }
        return o;
    }

    /**
     * Invokes a method on the given object by passing the given arguments
     * into the method.
     * 
     * @param obj
     * @param method
     * @param argTypes
     * @param args
     * @return
     */
    public static Object invoke(Object obj, String method, Class[] argTypes, Object[] args)
    {
        if (obj == null || method == null)
        {
            throw new IllegalArgumentException("Object and Method must be supplied.");
        }
        
        /**
         * Try to invoke the method.
         * 
         * If the method is unable to be invoked, we log and return null.
         */
        try
        {
            Method m = obj.getClass().getMethod(method, argTypes);
            if(m != null)
            {
                return m.invoke(obj, args);
            }
        }
        catch(NoSuchMethodException nsme)
        {
            logger.debug(nsme);
        }
        catch(IllegalAccessException iae)
        {
            logger.debug(iae);
        }
        catch(InvocationTargetException ite)
        {
            logger.debug(ite);
        }
        
        return null;
    }
    
	  public static Map getPropertyDescriptors(Class beanClass)
	    throws Exception
	  {
	    if (beanClass == null) return null;
	    Map properties = new HashMap();

	    BeanInfo beanInfo = Introspector.getBeanInfo(beanClass);
	    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	    if (propertyDescriptors == null) return properties;
	    for (int i = 0; i < propertyDescriptors.length; ++i) {
	      PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
	      if (propertyDescriptor == null) continue;

	      String propertyName = propertyDescriptor.getName();
	      properties.put(propertyName, propertyDescriptor);
	    }
	    return properties;
	  }
	  
	  public static Map getPropertyDescriptors(Object bean)
	    throws Exception
	  {
	    if (bean == null) return null;
	    return getPropertyDescriptors(bean.getClass());
	  }

	  public static Map getProperties(Object bean)
	    throws Exception
	  {
	    return getProperties(bean, null);
	  }

	  public static Map getProperties(Object bean, Collection propsToKeep) throws Exception
	  {
	    if (bean == null) return null;

	    Map propertyMap = new LinkedMap();
	    BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
	    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	    if (propertyDescriptors == null) {
	      return propertyMap;
	    }

	    for (int i = 0; i < propertyDescriptors.length; ++i) {
	      PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
	      if (propertyDescriptor == null) continue;

	      String propertyName = propertyDescriptor.getName();

	      if ((propsToKeep != null) && (!(propsToKeep.contains(propertyName)))) continue;

	      Method getter = propertyDescriptor.getReadMethod();

	      if (getter != null) {
	        Object value;
	        if (!(Modifier.isPublic(getter.getModifiers()))) continue;

	        try
	        {
	          value = getter.invoke(bean, Collections.EMPTY_LIST.toArray());
	        } catch (Throwable t) {
	          String error = ExceptionUtils.getFullStackTrace(t);
	          logger.debug("Bean inspection: invocation of " + bean.getClass().getName() + '.' + getter.getName() + "() while trying to obtain property '" + propertyName + "' threw an exception: " + error + "\nSetting value to the error string and continuing");

	          value = t.toString();
	        }

	        propertyMap.put(propertyName, value);
	      }
	    }
	    return propertyMap;
	  }
	 
		public static List getProperty(List rows, Object column) {
			List values = new ArrayList();
			for (Iterator e = rows.iterator(); e.hasNext();) {
				Map row = (Map) e.next();
				if (row.containsKey(column))
					values.add(row.get(column));
			}
			return values;
		}

		public static List getColumnValues(List rows, Object column) {
			return getProperty(rows, column);
		}

		public static List find(List rows, Object propertyName, Object value) {
			List values = new ArrayList();
			for (Iterator e = rows.iterator(); e.hasNext();) {
				Map row = (Map) e.next();
				if (row.get(propertyName).equals(value))
					values.add(row);
			}
			return ((values.size() > 0) ? values : null);
		}

		public static List find(List rows, Map properties) {
			List values = new ArrayList();
			for (Iterator e = rows.iterator(); e.hasNext();) {
				Map row = (Map) e.next();
				boolean found = true;
				for (Iterator f = properties.keySet().iterator(); f.hasNext();) {
					Object propertyName = f.next();
					if (!properties.get(propertyName).equals(row.get(propertyName))) {
						found = false;
						break;
					}
				}

				if (found)
					values.add(row);
			}
			return ((values.size() > 0) ? values : null);
		}

		public static Map makeIndex(Collection objects, String propertyName) {
			Map index = new LinkedMap();
			for (Iterator e = objects.iterator(); e.hasNext();) {
				Object object = e.next();
				if (object instanceof Map) {
					Map propertyMap = (Map) object;
					Object propertyValue = propertyMap.get(propertyName);
					if (propertyValue != null) {
						index.put(propertyValue, propertyMap);
					}
				}
			}
			return index;
		}

		public static Map indexOnProperty(Collection objects, String propertyName) {
			return makeIndex(objects, propertyName);
		}
	  
    
}
