package com.mpaike.core.util.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class IOUtil
{
  public static final int DEFAULT_BUFFER_SIZE = 4096;

  public static void copyStreams(InputStream in, OutputStream out)
    throws IOException
  {
    copyStreams(in, out, 4096, true);
  }

  public static void copyStreams(InputStream in, OutputStream out, int bufSize)
    throws IOException
  {
    copyStreams(in, out, bufSize, true);
  }

  public static void copyStreams(InputStream in, OutputStream out, boolean buffered)
    throws IOException
  {
    copyStreams(in, out, 4096, buffered);
  }

  public static void copyStreams(InputStream in, OutputStream out, int bufSize, boolean buffered)
    throws IOException
  {
    int bytesRead;
    byte[] buffer = new byte[bufSize];
    if (buffered) {
      in = new BufferedInputStream(in);
      out = new BufferedOutputStream(out);
    }

    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytesRead);
    }

    if (buffered) out.flush();
  }

  public static void copyCharacterStreams(Reader in, Writer out)
    throws IOException
  {
    copyCharacterStreams(in, out, 4096, true);
  }

  public static void copyCharacterStreams(Reader in, Writer out, int bufSize)
    throws IOException
  {
    copyCharacterStreams(in, out, bufSize, true);
  }

  public static void copyCharacterStreams(Reader in, Writer out, boolean buffered)
    throws IOException
  {
    copyCharacterStreams(in, out, 4096, buffered);
  }

  public static void copyCharacterStreams(Reader in, Writer out, int bufSize, boolean buffered)
    throws IOException
  {
    int bytesRead;
    char[] buffer = new char[bufSize];
    if (buffered) {
      in = new BufferedReader(in);
      out = new BufferedWriter(out);
    }

    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytesRead);
    }

    if (buffered) out.flush();
  }

  public static void addOutput(OutputStream out, Object input)
    throws Exception
  {
    if (input instanceof List) {
      List inputs = (List)input;
      for (Iterator e = inputs.iterator(); e.hasNext(); )
        addOutput(out, e.next());
    }
    else if (input instanceof String) {
      Writer writer = new OutputStreamWriter(new NonFlushingOutputStream(out));
      writer.write((String)input);
      writer.flush();
    } else if (input instanceof InputStream) {
      InputStream inputStream = (InputStream)input;
      try {
        copyStreams(inputStream, out);
      }
      finally {
        try {
          inputStream.close(); } catch (Exception ignored) {
        }
      }
    } else if (input instanceof Reader) {
      Reader inputReader = (Reader)input;
      try {
        Writer writer = new OutputStreamWriter(new NonFlushingOutputStream(out));
        copyCharacterStreams(inputReader, writer);
        writer.flush();
      }
      finally {
        try {
          inputReader.close(); } catch (Exception ignored) {
        }
      }
    } else {
      throw new Exception("Unsupported input format: " + input.getClass().getName());
    }
  }

  public static Reader makeReader(Object source) {
    if (source == null) throw new NullPointerException("Passed null source");

    if (source instanceof Reader)
      return ((Reader)source);
    if (source instanceof InputStream)
      return new InputStreamReader((InputStream)source);
    if ((source instanceof String) || (source instanceof StringBuffer))
      return new StringReader(source.toString());
    if (source instanceof char[])
      return new CharArrayReader((char[])source);
    if (source instanceof byte[])
      return new InputStreamReader(new ByteArrayInputStream((byte[])source));

    throw new IllegalArgumentException("Don't know to make a Reader from a " + source.getClass().getName());
  }

  public static String inputStreamToString(InputStream stream)
    throws IOException
  {
    return readerToString(new InputStreamReader(stream)); }

  public static String readerToString(Reader reader) throws IOException {
    Writer writer = new StringWriter();
    copyCharacterStreams(reader, writer);
    return writer.toString(); }

  public static void closeQuitely(InputStream is) {
    try {
      is.close(); } catch (Exception ignored) { } }

  public static void closeQuitely(OutputStream os) {
    try {
      os.flush(); } catch (Exception ignored) { }
    try { os.close();
    }
    catch (Exception ignored)
    {
    }
  }
}

class NonFlushingOutputStream extends FilterOutputStream
{
  public void flush()
  {
  }

  public NonFlushingOutputStream(OutputStream stream)
  {
    super(stream);
  }
}