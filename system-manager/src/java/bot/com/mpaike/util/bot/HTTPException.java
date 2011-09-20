package com.mpaike.util.bot;

/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class HTTPException extends java.io.IOException {

    /**
     * Creates a new instance of <code>HTTPException</code> without detail message.
     */
    public HTTPException() {
    }


    /**
     * Constructs an instance of <code>HTTPException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public HTTPException(String msg) {
        super(msg);
    }
}
