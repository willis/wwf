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
package com.mpaike.core.error;

import java.util.Locale;

import junit.framework.TestCase;

import com.mpaike.core.i18n.I18NUtil;

/**
 * WWF runtime exception test
 */
public class WWFRuntimeExceptionTest extends TestCase
{
    private static final String BASE_RESOURCE_NAME = "com.ebizserve.i18n.testMessages";
    private static final String PARAM_VALUE = "television";
    private static final String MSG_PARAMS = "msg_params";
    private static final String MSG_ERROR = "msg_error";
    private static final String VALUE_ERROR = "This is an error message. \n  This is on a new line.";
    private static final String VALUE_FR_ERROR = "C'est un message d'erreur. \n  C'est sur une nouvelle ligne.";
    private static final String VALUE_PARAMS = "What no " + PARAM_VALUE + "?";
    private static final String VALUE_FR_PARAMS = "Que non " + PARAM_VALUE + "?";
    private static final String NON_I18NED_MSG = "This is a non i18ned error message.";
   
    @Override
    protected void setUp() throws Exception
    {
        // Re-set the current locale to be the default
        Locale.setDefault(Locale.ENGLISH);
        I18NUtil.setLocale(Locale.getDefault());
    }
    
    public void testI18NBehaviour()
    {
        // Register the bundle
        I18NUtil.registerResourceBundle(BASE_RESOURCE_NAME);
        
        WWFRuntimeException exception1 = new WWFRuntimeException(MSG_PARAMS, new Object[]{PARAM_VALUE});
        assertTrue(exception1.getMessage().contains(VALUE_PARAMS));
        WWFRuntimeException exception3 = new WWFRuntimeException(MSG_ERROR);
        assertTrue(exception3.getMessage().contains(VALUE_ERROR));
        
        // Change the locale and re-test
        I18NUtil.setLocale(new Locale("fr", "FR"));
        
        WWFRuntimeException exception2 = new WWFRuntimeException(MSG_PARAMS, new Object[]{PARAM_VALUE});
        assertTrue(exception2.getMessage().contains(VALUE_FR_PARAMS));   
        WWFRuntimeException exception4 = new WWFRuntimeException(MSG_ERROR);
        assertTrue(exception4.getMessage().contains(VALUE_FR_ERROR));  
        
        WWFRuntimeException exception5 = new WWFRuntimeException(NON_I18NED_MSG);
        assertTrue(exception5.getMessage().contains(NON_I18NED_MSG));
    }
    
    public void testMakeRuntimeException()
    {
        Throwable e = new RuntimeException("sfsfs");
        RuntimeException ee = WWFRuntimeException.makeRuntimeException(e, "Test");
        assertTrue("Exception should not have been changed", ee == e);
        
        e = new Exception();
        ee = WWFRuntimeException.makeRuntimeException(e, "Test");
        assertTrue("Expected an WWFRuntimeException instance", ee instanceof WWFRuntimeException);
    }
}
