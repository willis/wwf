package com.mpaike.core.util.bbcode;

/**
 * 格式化异常
 */
public class ForumException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ForumException(String message) {
		super(message);
	}

	public ForumException(Throwable t) {
		this(t.toString(), t);
	}

	public ForumException(String message, Throwable t) {
		super(message, t);
		this.setStackTrace(t.getStackTrace());
	}
}