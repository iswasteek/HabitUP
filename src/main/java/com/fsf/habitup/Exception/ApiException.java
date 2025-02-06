package com.fsf.habitup.Exception;

public class ApiException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Constructor that accepts a message
    public ApiException(String message) {
        super(message);
    }

    // Constructor that accepts both message and cause
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
