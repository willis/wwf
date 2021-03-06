/*
 * Copyright (C) 2009-2010 WWF Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in WWF's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.42y.net/legal/licensing"
 */
package com.mpaike.core.util.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.mpaike.core.util.ParameterCheck;

/**
 * An generic registry of objects held by name.  This is effectively a strongly-typed,
 * synchronized map. 
 */
public class NamedObjectRegistry<T>
{
    private final ReentrantReadWriteLock.ReadLock readLock;
    private final ReentrantReadWriteLock.WriteLock writeLock;
    
    private Class<T> storageType;
    private final Map<String, T> objects;

    /**
     * Default constructor.  The {@link #setStorageType(Class)} method must be called.
     */
    public NamedObjectRegistry()
    {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
        this.storageType = null;                          // Deliberately null
        this.objects = new HashMap<String, T>(13);
    }
    
    /**
     * Constructor that takes care of {@link #setStorageType(Class)}.
     * 
     * @see #setStorageType(Class)
     */
    public NamedObjectRegistry(Class<T> type)
    {
        this();
        setStorageType(type);
    }

    /**
     * Set the type of class that the registry holds.  Any attempt to register a
     * an instance of another type will be rejected.
     * 
     * @param clazz                     the type to store
     */
    public void setStorageType(Class<T> clazz)
    {
        writeLock.lock();
        try
        {
            this.storageType = clazz;
        }
        finally
        {
            writeLock.unlock();
        }
    }

    /**
     * Register a named object instance.
     * 
     * @param name          the name of the object
     * @param object        the instance to register, which correspond to the type
     *                      
     */
    public void register(String name, T object)
    {
        ParameterCheck.mandatoryString("name", name);
        ParameterCheck.mandatory("object", object);
        
        if (!storageType.isAssignableFrom(object.getClass()))
        {
            throw new IllegalArgumentException(
                    "This NameObjectRegistry only accepts objects of type " + storageType);
        }
        writeLock.lock();
        try
        {
            if (storageType == null)
            {
                throw new IllegalStateException(
                        "The registry has not been configured (setStorageType not yet called yet)");
            }
            objects.put(name, object);
        }
        finally
        {
            writeLock.unlock();
        }
    }
    
    /**
     * Get a named object if it has been registered
     * 
     * @param name          the name of the object to retrieve
     * @return              Returns the instance of the object, which will necessarily
     *                      be of the correct type, or <tt>null</tt>
     */
    public T getNamedObject(String name)
    {
        readLock.lock();
        try
        {
            // Get it
            return objects.get(name);
        }
        finally
        {
            readLock.unlock();
        }
    }
    
    /**
     * @return              Returns a copy of the map of instances 
     */
    public Map<String, T> getAllNamedObjects()
    {
        readLock.lock();
        try
        {
            // Get it
            return new HashMap<String, T>(objects);
        }
        finally
        {
            readLock.unlock();
        }
    }
    
    public void reset()
    {
        writeLock.lock();
        try
        {
            if (storageType == null)
            objects.clear();
        }
        finally
        {
            writeLock.unlock();
        }
    }
}
