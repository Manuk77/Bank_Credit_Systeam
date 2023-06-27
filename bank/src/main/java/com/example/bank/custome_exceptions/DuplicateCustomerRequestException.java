package com.example.bank.custome_exceptions;

public class DuplicateCustomerRequestException extends RuntimeException {

    public DuplicateCustomerRequestException() {
        super();
    }

    public DuplicateCustomerRequestException(final String message) {
        super(message);
    }

    public DuplicateCustomerRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateCustomerRequestException(Throwable cause) {
        super(cause);
    }

    public DuplicateCustomerRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
