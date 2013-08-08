package com.mikedandy.knob.exception;

public class KnobRequiredArgException extends KnobGenericException {

	private static final long serialVersionUID = -8433691294489121363L;

	public KnobRequiredArgException() {
		super();
	}
	
	public KnobRequiredArgException(String message) {
		super(message);
	}
}
