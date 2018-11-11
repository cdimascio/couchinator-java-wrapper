package io.github.cdimascio.couchinatorw;

public class CouchinatorException extends Exception {
    public CouchinatorException() {
    }

    public CouchinatorException(String message) {
        super(message);
    }

    public CouchinatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public CouchinatorException(Throwable cause) {
        super(cause);
    }

    public CouchinatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
