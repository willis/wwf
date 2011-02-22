package com.mpaike.core.cache;

import java.util.HashMap;

/**
 * This is an implementation of a purely in-memory cache that uses a
 * HashMap to provide a basic form of caching.
 * 
 * This class is thread safe for concurrent usage of the cache.
 */
public class BasicCache<K> implements ContentCache<K>
{
    protected final HashMap<String, CacheItem<K>> cache;
    protected final long timeout;
    
    /**
     * Instantiates a new basic cache.
     * 
     * @param timeout   the timeout
     */
    public BasicCache(long timeout)
    {
        this(timeout, 256);
    }
    
    /**
     * Instantiates a new basic cache.
     * 
     * @param timeout   the timeout
     * @param size      Cache size
     */
    public BasicCache(long timeout, int size)
    {
        this.timeout = timeout;
        if (size < 1)
        {
            throw new IllegalArgumentException("Cache size must be +ve value");
        }
        this.cache = new HashMap<String, CacheItem<K>>(size);
    }

    /* (non-Javadoc)
     * @see com.mpaike.core.util.cache.ContentCache#get(java.lang.String)
     */
    public synchronized K get(String key)
    {
        // get the content item from the cache
        CacheItem<K> item = cache.get(key);
        
        // if the cache item is null, return right away
        if (item == null)
        {
            return null;
        }
        else
        {
            // ask the cache item if it's still valid
            if (item.isExpired())
            {
                // it's not valid, throw it away
                remove(key);
                return null;
            }
            
            return item.object;
        }
    }

    /* (non-Javadoc)
     * @see com.mpaike.core.util.cache.ContentCache#remove(java.lang.String)
     */
    public synchronized void remove(String key)
    {
        if (key == null)
        {
            return;
        }
        cache.remove(key);
    }

    /* (non-Javadoc)
     * @see com.mpaike.core.util.cache.ContentCache#put(java.lang.String, java.lang.Object)
     */
    public synchronized void put(String key, K obj)
    {
        put(key, obj, timeout);
    }

    /* (non-Javadoc)
     * @see com.mpaike.core.util.cache.ContentCache#put(java.lang.String, java.lang.Object, long)
     */
    public synchronized void put(String key, K obj, long timeout)
    {
        // create the cache item
        CacheItem<K> item = new CacheItem<K>(key, obj, timeout);
        cache.put(key, item);
    }

    /* (non-Javadoc)
     * @see com.mpaike.core.util.cache.ContentCache#invalidate()
     */
    public synchronized void invalidate()
    {
        cache.clear();
    }
}
