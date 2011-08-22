package com.mpaike.util.bot;
import java.net.URLEncoder;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class HTMLForm extends AttributeList {

  /**
   * The method(i.e. GET or POST)
   */
  protected String method;

  /**
   * The action, or the site to post the form to.
   */
  protected String action;


  protected String type;

  /**
   * Construct an HTMLForm object.
   *
   * @param method The method(i.e. GET or POST)
   * @param action The action, or the site that the result
   * @param type The type, multipart/form-data
   * should be posted to.
   */
  public HTMLForm(String method,String action,String type)
  {
    this.method = method;
    this.action = action;
    this.type = type;
  }
  /**
   * Call to get the URL to post to.
   *
   * @return The URL to post to.
   */

  public String getAction()
  {
    return action;
  }

  /**
   * @return The method(GET or POST)
   */
  public String getMethod()
  {
    return method;
  }


  /**
   * @return The type(multipart/form-data)
   */
  public String getType()
  {
    return type;
  }

  /**
   * Add a HTML INPUT item to this form.
   *
   * @param name The name of the input item.
   * @param value The value of the input item.
   * @param type The type of input item.
   */
  public void addInput(String name,String value,
                       String type,String prompt,AttributeList options)
  {
    FormElement e = new FormElement();
    e.setName(name);
    e.setValue(value);
    e.setType(type.toUpperCase());
    e.setOptions(options);
    e.setPrompt(prompt);
    add(e);
  }

  /**
   * Convert this form into the string that would be posted
   * for it.
   */
  public String toString()
  {
    String postdata;

    postdata = "";
    int i=0;
    while ( get(i)!=null ) {
      Attribute a = get(i);
      if ( postdata.length()>0 )
        postdata+="&";
      postdata+=a.getName();
      postdata+="=";
      if ( a.getValue()!=null )
        postdata+=URLEncoder.encode(a.getValue());
      i++;
    }
    return postdata;
  }

  public class FormElement extends Attribute {
    protected String type;
    protected AttributeList options;
    protected String prompt;

    public void setOptions(AttributeList options)
    {
      this.options = options;
    }

    public AttributeList getOptions()
    {
      return options;
    }

    public void setType(String t)
    {
      type = t;
    }

    public String getType()
    {
      return type;
    }

    public String getPrompt()
    {
      return prompt;
    }

    public void setPrompt(String str)
    {
      prompt = str;
    }
  }
}
