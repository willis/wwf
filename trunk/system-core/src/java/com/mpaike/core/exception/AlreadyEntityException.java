package com.mpaike.core.exception;

public class AlreadyEntityException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3219969756601750680L;

	public AlreadyEntityException(){}
	
	public AlreadyEntityException(String msg){
		super(msg);
	}

}
