package com.mpaike.core.util.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.Socket;

import com.mpaike.core.util.ThreadTimer;

public class TimedSocket {

	  public static Socket getSocket(Object target, int port, int timeout)
	    throws IOException, InterruptedIOException
	  {
	    ThreadTimer tt = new ThreadTimer(Thread.currentThread(), timeout);
	    tt.start();

	    Socket sock = null;
	    try
	    {
	      if (target instanceof String)
	        sock = new Socket((String)target, port);
	      else
	        sock = new Socket((InetAddress)target, port);
	      tt.interrupt();
	    } catch (InterruptedIOException e) {
	      if (sock == null)
	        throw new InterruptedIOException("Connection attempt to " + target.toString() + ':' + port + " timed out after " + timeout + "ms.");

	    }

	    return sock;
	  }
	  
}
