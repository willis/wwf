package com.mpaike.util.bot;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class Attribute implements Cloneable {

  /**
   * The name of this attribute.
   */
  private String name;

  /**
   * The value of this attribute.
   */
  private String value;

  /**
   * The delimiter for the value of this
   * attribute(i.e. " or ').
   */
  private char delim;

  /**
   * Clone this object using the cloneable interface.
   *
   * @return A new object which is a clone of the
   * the specified object.
   */
  public Object clone()
  {
    return new Attribute(name,value,delim);
  }

  /**
   * Construct a new Attribute.  The name, delim and value
   * properties can be specified here.
   *
   * @param name The name of this attribute.
   * @param value The value of this attribute.
   * @param delim The delimiter character for the value.
   * For example a " or '.
   */
  public Attribute(String name,String value,char delim)
  {
    this.name = name;
    this.value = value;
    this.delim = delim;
  }

  /**
   * The default constructor.  Construct a blank attribute.
   */
  public Attribute()
  {
    this("","",(char)0);
  }

  /**
   * Construct an attribute without a delimiter.
   *
   * @param name The name of this attribute.
   * @param value The value of this attribute.
   */
  public Attribute(String name,String value)
  {
    this(name,value,(char)0);
  }

  /**
   * Get the name of this attribute.
   *
   * @return The name of this attribute.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Get the value of this attribute.
   *
   * @return The value of this attribute.
   */
  public String getValue()
  {
    return value;
  }

  /**
   * Returns the delimiter used for the value of this attribute.
   *
   * @return The delimiter used for the value of this attribute.
   */
  public char getDelim()
  {
    return delim;
  }

  /**
   * Set the name of this attribute.
   *
   * @param name The name of this attribute.
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Set the value of this attribute.
   *
   * @param value The value of this attribute.
   */
  public void setValue(String value)
  {
    this.value = value;
  }

  /**
   * Set the delimiter of the value of this attribute.
   *
   * @param ch The delimiter of the value of this attribute.
   */
  public void setDelim(char ch)
  {
    delim = ch;
  }
}
