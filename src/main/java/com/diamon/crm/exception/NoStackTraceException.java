package com.diamon.crm.exception;

@SuppressWarnings("serial")
public class NoStackTraceException extends RuntimeException {
	public NoStackTraceException() {
		super("", null, true, false);
	}
}
