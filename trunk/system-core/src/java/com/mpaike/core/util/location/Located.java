package com.mpaike.core.util.location;

/**
 * Base class for location aware objects
 */
public abstract class Located implements Locatable {
    
    protected Location location;
    
    /**
     * Get the location of this object
     * 
     * @return the location
     */
    public Location getLocation() {
        return location;
    }
    
    /**
     * Set the location of this object
     * 
     * @param loc the location
     */
    public void setLocation(Location loc) {
        this.location = loc;
    }
}
