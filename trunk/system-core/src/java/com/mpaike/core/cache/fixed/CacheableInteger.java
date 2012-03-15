package com.mpaike.core.cache.fixed;

/**
 * Wrapper for Integer objects so they can be treated as Cacheable objects.
 * Integer is a final class, so it can't be extended.
 */
public class CacheableInteger implements Cacheable {

    /**
     * Wrapped Integer object.
     */
    private Integer integer;

    /**
     * Creates a new CacheableInteger.
     *
     * @param string the Integer object to wrap.
     */
    public CacheableInteger(Integer integer) {
        this.integer = integer;
    }

    /**
     * Returns the Integer wrapped by the CacheableInteger object.
     *
     * @return the Integer object.
     */
    public Integer getInteger() {
        return integer;
    }

    //FROM THE CACHEABLE INTERFACE//

    public int getSize() {
        return CacheSizes.sizeOfObject() + CacheSizes.sizeOfInt();
    }
}
