package com.mpaike.core.util.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A collection of useful static methods for working with input and output
 * streams.
 */
public class DataUtil
{
    private static int BUFFER_SIZE = 1024;

    /**
     * Copy stream.
     * 
     * @param input
     *            the input
     * @param output
     *            the output
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException
    {
        copyStream(input, output, true);
    }

    /**
     * Copy stream.
     * 
     * @param in
     *            the in
     * @param out
     *            the out
     * @param closeStreams
     *            the close streams
     * 
     * @return the int
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static int copyStream(final InputStream in, final OutputStream out, final boolean closeStreams)
        throws IOException
    {
        try
        {
            int byteCount = 0;
            final byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1)
            {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        }
        finally
        {
            if (closeStreams)
            {
                try
                {
                    in.close();
                }
                catch (IOException ex)
                {
                }
                try
                {
                    out.close();
                }
                catch (IOException ex)
                {
                }
            }
        }
    }

    /**
     * To byte array.
     * 
     * @param input
     *            the input
     * 
     * @return the byte[]
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] toByteArray(InputStream input) throws IOException
    {
        return toByteArray(input, true);
    }

    /**
     * To byte array.
     * 
     * @param input
     *            the input
     * @param closeStream
     *            the close stream
     * 
     * @return the byte[]
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static byte[] toByteArray(InputStream input, boolean closeStream)
            throws IOException
    {
        return copyToByteArray(input, closeStream).toByteArray();
    }

    /**
     * Copy to byte array.
     * 
     * @param input
     *            the input
     * @param closeInputStream
     *            the close input stream
     * 
     * @return the byte array output stream
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static ByteArrayOutputStream copyToByteArray(InputStream input, boolean closeInputStream)
        throws IOException
    {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(BUFFER_SIZE);
        
        copyStream(input, buffer, closeInputStream);
        
        return buffer;
    }

    /**
     * Copy to string.
     * 
     * @param input     the input
     * @param closeInputStream  the close input stream
     * 
     * @return the string
     * 
     * @throws IOException  Signals that an I/O exception has occurred.
     */
    public static String copyToString(InputStream input, String encoding, boolean closeInputStream)
        throws IOException
    {
        ByteArrayOutputStream baos = copyToByteArray(input, closeInputStream);
        return encoding != null ? new String(baos.toByteArray(), encoding) : new String(baos.toByteArray());
    }
}
