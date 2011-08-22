package com.mpaike.util.bot;
/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class HTMLTag extends AttributeList implements Cloneable {
  protected String name;

  public Object clone()
  {
    int i;
    AttributeList rtn = new AttributeList();
    for ( i=0;i<vec.size();i++ )
      rtn.add( (Attribute)get(i).clone() );
    rtn.setName(name);

    return rtn;
  }

  public void setName(String s)
  {
    name = s;
  }

  public String getName()
  {
    return name;
  }

  public String getAttributeValue(String name)
  {
    Attribute a = get(name);
    if ( a==null )
      return null;
    return a.getValue();
  }
}
