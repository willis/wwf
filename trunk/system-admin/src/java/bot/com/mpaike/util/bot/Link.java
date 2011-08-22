package com.mpaike.util.bot;
/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class Link {

  /**
   * The ALT attribute.
   */
  protected String alt;

  /**
   * The link attribute.
   */
  protected String href;


  /**
   * The link prompt text
   */
  protected String prompt;


  /**
   * The constructor.
   *
   * @param alt The alt attribute for this link.
   * @param href The URL this link goes to.
   * @param prompt The prompt(if any) for this link.
   */

  public Link(String alt,String href,String prompt)
  {
    this.alt = alt;
    this.href = href;
    this.prompt = prompt;
  }

  /**
   * Returns the ALT attribute.
   *
   * @return The ALT attribute.
   */
  public String getALT()
  {
    return alt;
  }

  /**
   * Returns the link attribute.
   *
   * @return The link(HREF) attribute.
   */
  public String getHREF()
  {
    return href;
  }

  /**
   * Returns the link attribute.
   *
   * @return The link(HREF) attribute.
   */
  public String getPrompt()
  {
    return prompt;
  }


  /**
   * Returns the link URL.
   *
   * @return The link URL.
   */
  public String toString()
  {
    return href;
  }

  /**
   * Set the prompt.
   *
   * @param prompt The prompt for this link.
   */
  public void setPrompt(String prompt)
  {
    this.prompt = prompt;
  }
}
