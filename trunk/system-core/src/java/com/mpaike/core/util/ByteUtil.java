package com.mpaike.core.util;

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
