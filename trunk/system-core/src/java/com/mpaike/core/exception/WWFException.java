package com.mpaike.core.exception;

import com.mpaike.core.util.location.Locatable;
import com.mpaike.core.util.location.Location;
import com.mpaike.core.util.location.LocationUtils;


public class WWFException extends RuntimeException implements Locatable {

    private Location location;


    /**
     * Constructs a <code>WWFException</code> with no detail message.
     */
    public WWFException() {
    }

    /**
     * Constructs a <code>WWFException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public WWFException(String s) {
        this(s, null, null);
    }
    
    /**
     * Constructs a <code>WWFException</code> with the specified
     * detail message and target.
     *
     * @param s the detail message.
     * @param target the target of the exception.
     */
    public WWFException(String s, Object target) {
        this(s, (Throwable) null, target);
    }

    /**
     * Constructs a <code>WWFException</code> with the root cause
     *
     * @param cause The wrapped exception
     */
    public WWFException(Throwable cause) {
        this(null, cause, null);
    }
    
    /**
     * Constructs a <code>WWFException</code> with the root cause and target
     *
     * @param cause The wrapped exception
     * @param target The target of the exception
     */
    public WWFException(Throwable cause, Object target) {
        this(null, cause, target);
    }

    /**
     * Constructs a <code>WWFException</code> with the specified
     * detail message and exception cause.
     *
     * @param s the detail message.
     * @param cause the wrapped exception
     */
    public WWFException(String s, Throwable cause) {
        this(s, cause, null);
    }
    
    
     /**
     * Constructs a <code>WWFException</code> with the specified
     * detail message, cause, and target
     *
     * @param s the detail message.
     * @param cause The wrapped exception
     * @param target The target of the exception
     */
    public WWFException(String s, Throwable cause, Object target) {
        super(s, cause);
        
        this.location = LocationUtils.getLocation(target);
        if (this.location == Location.UNKNOWN) {
            this.location = LocationUtils.getLocation(cause);
        }
    }


    /**
     * Gets the underlying cause
     * 
     * @return the underlying cause, <tt>null</tt> if no cause
     * @deprecated Use {@link #getCause()} 
     */
    @Deprecated public Throwable getThrowable() {
        return getCause();
    }


    /**
     * Gets the location of the error, if available
     *
     * @return the location, <tt>null</tt> if not available 
     */
    public Location getLocation() {
        return this.location;
    }
    
    
    /**
     * Returns a short description of this throwable object, including the 
     * location. If no detailed message is available, it will use the message
     * of the underlying exception if available.
     *
     * @return a string representation of this <code>Throwable</code>.
     */
    @Override
    public String toString() {
        String msg = getMessage();
        if (msg == null && getCause() != null) {
            msg = getCause().getMessage();
        }

        if (location != null) {
            if (msg != null) {
                return msg + " - " + location.toString();
            } else {
                return location.toString();
            }
        } else {
            return msg;
        }
    }
}
