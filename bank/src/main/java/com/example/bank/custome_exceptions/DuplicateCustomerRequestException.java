package com.example.bank.custome_exceptions;

public class DuplicateCustomerRequest extends RuntimeException {

    public DuplicateCustomerRequest() {
        super();
    }

    public DuplicateCustomerRequest(final String message) {
        super(message);
    }

    public DuplicateCustomerRequest(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCustomerRequest(Throwable cause) {
        super(cause);
    }

    public DuplicateCustomerRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
