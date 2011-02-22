package com.mpaike.core.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpaike.core.util.data.IOUtil;

public class Compression {
	
	private static final Logger LOG = LoggerFactory.getLogger(Compression.class);

	  static ByteArrayOutputStream whiteSpace = null;
	  public static int whiteSpaceBufSize = 2049;

	  private static final ByteArrayOutputStream getWhiteSpaceBuffer()
	  {
	    if (whiteSpace == null) {
	      whiteSpace = new ByteArrayOutputStream();
	      for (int i = 0; i < whiteSpaceBufSize; ) { whiteSpace.write(32); ++i;
	      }

	      whiteSpace.write(10);
	    }
	    return whiteSpace;
	  }

	  private static final boolean mimeTypeRequiresPadding(String paramString)
	  {
	    return ((paramString != null) && (((paramString.indexOf("javascript") != -1) || (paramString.indexOf("ecmascript") != -1))));
	  }

	  public ByteArrayOutputStream compressBuffer(ByteArrayOutputStream paramByteArrayOutputStream, String paramString)
	    throws Exception
	  {
	    return compressBuffer(paramByteArrayOutputStream, mimeTypeRequiresPadding(paramString));
	  }

	  public ByteArrayOutputStream compressBuffer(ByteArrayOutputStream paramByteArrayOutputStream, boolean paramBoolean)
	    throws Exception
	  {
	    paramByteArrayOutputStream.flush();
	    int i = paramByteArrayOutputStream.size();
	    int j = i;
	    if (paramBoolean) j += whiteSpaceBufSize;

	    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(j / 4);

	    GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream, j);

	    if (paramBoolean) { getWhiteSpaceBuffer().writeTo(localGZIPOutputStream);
	    }

	    paramByteArrayOutputStream.writeTo(localGZIPOutputStream);
	    localGZIPOutputStream.flush();
	    localGZIPOutputStream.close();
	    localByteArrayOutputStream.flush();

	    LOG.info("Compressed buffer: start -->" + ((paramBoolean) ? getWhiteSpaceBuffer().toString() : "") + paramByteArrayOutputStream.toString() + "<-- end");
	    return localByteArrayOutputStream;
	  }

	  public ByteArrayOutputStream compressStream(InputStream paramInputStream, String paramString)
	    throws Exception
	  {
	    return compressStream(paramInputStream, mimeTypeRequiresPadding(paramString));
	  }

	  public ByteArrayOutputStream compressStream(InputStream paramInputStream, boolean paramBoolean)
	    throws Exception
	  {
	    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	    IOUtil.copyStreams(paramInputStream, localByteArrayOutputStream);
	    return compressBuffer(localByteArrayOutputStream, paramBoolean); }

	  public Object staticInstance() throws Exception {
	    return new Compression();
	  }
	
}
