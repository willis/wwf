package com.mpaike.core.util;

public class ObjectHelper
{
  public static boolean isNull(String s)
  {
    return "null".equals(s);
  }

  public static boolean isBoolean(String s) {
    return (("true".equals(s)) || ("false".equals(s)));
  }

  public static boolean isInteger(String s)
  {
    if ((s != null) && (s.length() > 0)) {
      if (s.length() == 1)
        return ((isNumber(s.charAt(0))) && ('.' != s.charAt(0)));

      return ((isNumber(s.charAt(0))) && (isNumber(s.charAt(s.length() - 1))) && (s.indexOf(46) < 0));
    }

    return false;
  }

  public static boolean isDouble(String s)
  {
    if ((s != null) && (s.length() > 1))
      return ((isNumber(s.charAt(0))) && (isNumber(s.charAt(s.length() - 1))) && (s.indexOf(46) >= 0));

    return false;
  }

  public static boolean isLong(String s)
  {
    if ((s != null) && (s.length() > 1))
      return ((isNumber(s.charAt(0))) && (s.endsWith("L")));

    return false;
  }

  public static boolean isFloat(String s)
  {
    if ((s != null) && (s.length() > 1))
      return ((isNumber(s.charAt(0))) && (s.endsWith("F")));

    return false;
  }

  public static boolean isString(String s)
  {
    if ((s != null) && (s.length() > 1))
      return (s.charAt(0) == '"');

    return false;
  }

  public static boolean isDateTime(String s)
  {
    if ((s != null) && (s.length() > 1))
      return (s.charAt(0) == '[');

    return false;
  }

  public static boolean isSplitor(String s)
  {
    return ((",".equals(s)) || ("(".equals(s)) || (")".equals(s)));
  }

  public static boolean isFunction(String s)
  {
    if ((s != null) && (s.length() > 1))
      return (s.charAt(0) == '$');

    return false;
  }

  public static boolean isNumber(char c)
  {
    return (((c >= '0') && (c <= '9')) || (c == '.'));
  }
}
