/*
 * Copyright (C) 2009-2011 WWF Software Limited.
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
 */
package com.mpaike.util;

/**
 * @author ChenH
 * @date 2012-2-1
 * TODO
 */
public class ByteUtil {
	
	public static int bytes2int(byte[] b) {
	    int mask = 0xff;
	    int temp = 0;
	    int res = 0;
	    for (int i = 0; i < 4; i++) {
	      res <<= 8;
	      temp = b[i] & mask;
	      res |= temp;
	    }
	    return res;
	  }
	  public static byte[] int2bytes(int num) {
	    byte[] b = new byte[4];
	    for (int i = 0; i < 4; i++) {
	      b[i] = (byte) (num >>> (24 - i * 8));
	    }
	    return b;
	  }

		public static long bytes2long(byte[] b) {
		    int mask = 0xff;
		    int temp = 0;
		    long res = 0;
		    for (int i = 0; i < 8; i++) {
		      res <<= 8;
		      temp = b[i] & mask;
		      res |= temp;
		    }
		    return res;
		  }
		  public static byte[] long2bytes(long num) {
		    byte[] b = new byte[8];
		    for (int i = 0; i < 8; i++) {
		      b[i] = (byte) (num >>> (56 - i * 8));
		    }
		    return b;
		  }
}