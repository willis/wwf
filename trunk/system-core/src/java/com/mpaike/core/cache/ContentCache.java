package com.mpaike.core.cache;

/**
 * Interface that describes basic methods for working with a cache
 * of content objects.  
 */
public interface ContentCache<K>
{
    /**
     * Gets content stored in the cache 
     * 
     * @param key the key
     * 
     * @return the object
     */
    public Object get(String key);

    /**
     * Places content into the cache (with default timeout)
     * 
     * @param key the key
     * @param obj the obj
     */
    public void put(String key, K obj);
    
    /**
     * Places content into the cache
     * 
     * @param key the key
     * @param obj the obj
     * @param timeout the timeout in milliseconds
     */
    public void put(String key, K obj, long timeout);
    
    /**
     * Removes a content object from the cache.
     * 
     * @param key the key
     */
    public void remove(String key);

    /**
     * Invalidates the cache
     */
    public void invalidate();
}
