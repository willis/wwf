package com.mpaike.core.cache;

import java.io.IOException;

/**
 * Wraps the cached item with metadata
 */
public final class CacheItem<K> implements java.io.Serializable
{
    private static final long serialVersionUID = 4526472295622776147L;

    private String key;
    K object;
    private long timeout;
    private long stamp;
    long lastChecked;
    
    /**
     * Instantiates a new cache item.
     * 
     * @param key the key
     * @param obj the obj
     * @param timeout the timeout
     */
    public CacheItem(String key, K obj, long timeout)
    {
        this.timeout = timeout;
        this.key = key;
        this.object = obj;
        this.lastChecked = this.stamp = System.currentTimeMillis();
    }

    /**
     * Checks if is expired.
     * 
     * @return true, if is expired
     */
    public boolean isExpired()
    {
        // never timeout for -1
        if (timeout == -1)
        {
            return false;
        }

        return (timeout < (System.currentTimeMillis() - stamp));
    }

    /**
     * Serializes the object to an output stream
     * 
     * @param out the out
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void writeObject(java.io.ObjectOutputStream out) throws IOException
    {
        out.writeObject(this.key);
        out.writeObject(new Long(this.timeout));
        out.writeObject(new Long(this.stamp));
        out.writeObject(this.object);
    }

    /**
     * Deserializes the object from an input stream
     * 
     * @param in the in
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    public void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException
    {
        this.key = (String) in.readObject();
        this.timeout = ((Long) in.readObject()).longValue();
        this.stamp = ((Long) in.readObject()).longValue();
        this.object = ((K) in.readObject());
    }
}
