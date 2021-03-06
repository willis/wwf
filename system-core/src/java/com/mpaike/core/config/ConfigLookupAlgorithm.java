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
package com.mpaike.core.config;

import com.mpaike.core.config.evaluator.Evaluator;

/**
 * Interface definition for a config lookup algorithm, this may be last value
 * wins, a merging strategy or based on inheritance.
 */
public interface ConfigLookupAlgorithm
{
   /**
    * Determines whether the given section applies to the given object, if so
    * the section is added to the results
    * 
    * @param section The config section to test 
    * @param evaluator The evaluator for the section being processed
    * @param object The object which is the subject of the config lookup
    * @param results The Config object holding all the matched sections
    */
   public void process(ConfigSection section, Evaluator evaluator, Object object, Config results);
}
