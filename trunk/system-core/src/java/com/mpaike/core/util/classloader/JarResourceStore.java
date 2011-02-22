package com.mpaike.core.util.classloader;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read resources from a jar file
 */
public class JarResourceStore implements ResourceStore {
    private static final Logger LOG = LoggerFactory.getLogger(JarResourceStore.class);

    private final File file;

    public JarResourceStore(File file) {
        this.file = file;
    }

    public void write(String pResourceName, byte[] pResourceData) {
    }

    public byte[] read(String pResourceName) {
        InputStream in = null;
        try {
            ZipFile jarFile = new ZipFile(file);
            ZipEntry entry = jarFile.getEntry(pResourceName);

            //read into byte array
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in = jarFile.getInputStream(entry);
            copy(in, out);

            return out.toByteArray();
        } catch (Exception e) {
            if (LOG.isDebugEnabled())
                LOG.debug("Unable to read file ["+pResourceName+"] from ["+file.getName()+"]", e);
            return null;
        } finally {
            closeQuietly(in);
        }
    }

    public static long copy(InputStream input, OutputStream output)
            throws IOException {
        byte[] buffer = new byte[1024 * 4];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    private void closeQuietly(InputStream is) {
        try {
            if (is != null)
                is.close();
        } catch (IOException e) {
            if (LOG.isErrorEnabled())
                LOG.error("Unable to close input stream", e);
        }
    }
}
