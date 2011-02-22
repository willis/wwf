package com.mpaike.core.exception;

public class FormatErrorException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6939481250571176917L;

	public FormatErrorException(){}
	
	public FormatErrorException(String msg){
		super(msg);
	}
	
}