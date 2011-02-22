package com.mpaike.core.util.classloader;

/**
 * *interface taken from Apache JCI
 */
public interface ResourceStore {

    void write(final String pResourceName, final byte[] pResourceData);

    byte[] read(final String pResourceName);
}

