package com.mpaike.core.cache.fixed;

/**
 * Wrapper for String objects so they can be treated as Cacheable objects.
 * String is a final class, so it can't be extended.
 */
public class CacheableString implements Cacheable {

    /**
     * Wrapped String object.
     */
    private String string;

    /**
     * Creates a new CacheableString.
     *
     * @param string the String object to wrap.
     */
    public CacheableString(String string) {
        this.string = string;
    }

    /**
     * Returns the String wrapped by the CacheableString object.
     *
     * @return the String object.
     */
    public String getString() {
        return string;
    }

    //FROM THE CACHEABLE INTERFACE//

    public int getSize() {
        return CacheSizes.sizeOfString(string);
    }
}
