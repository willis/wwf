package com.mpaike.core.util.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FileResourceStore implements ResourceStore {
    private static final Logger LOG = LoggerFactory.getLogger(FileResourceStore.class);
    private final File root;

    public FileResourceStore(final File pFile) {
        root = pFile;
    }

    public byte[] read(final String pResourceName) {
        FileInputStream fis = null;
        try {
            File file = getFile(pResourceName);
            byte[] data = new byte[(int) file.length()];
            fis = new FileInputStream(file);
            fis.read(data);

            return data;
        } catch (Exception e) {
            if (LOG.isDebugEnabled())
                LOG.debug("Unable to read file [#0]", e, pResourceName);
            return null;
        } finally {
            closeQuietly(fis);
        }
    }

    public void write(final String pResourceName, final byte[] pData) {

    }

    private void closeQuietly(InputStream is) {
        try {
            if (is != null)
                is.close();
        } catch (IOException e) {
            if (LOG.isErrorEnabled())
                LOG.error("Unable to close file input stream", e);
        }
    }

    private File getFile(final String pResourceName) {
        final String fileName = pResourceName.replace('/', File.separatorChar);
        return new File(root, fileName);
    }

    public String toString() {
        return this.getClass().getName() + root.toString();
    }
}
