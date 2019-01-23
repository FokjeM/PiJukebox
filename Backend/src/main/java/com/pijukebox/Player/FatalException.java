package com.pijukebox.Player;

/**
 * An exception that poses a valid reason to halt execution of the program
 *
 * @author Riven
 */
class FatalException extends Exception {

    /**
     * Fatal Exception with all parameters
     */
    protected FatalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message + "\r\n\tCould not recover from this error.\r\n\tEXECUTION WILL NOW HALT!", cause, enableSuppression, writableStackTrace);
    }

    public FatalException(String message, Throwable cause) {
        this(message + "\r\n\tCould not recover from this error.\r\n\tEXECUTION WILL NOW HALT!", cause, false, false);
    }

    public FatalException(Throwable cause) {
        this("No message was given, this is NOT a non-fatal error.\r\nEXECUTION WILL NOW HALT!", cause, false, false);
    }

    public FatalException() {
        super("No message or cause was given. This error is regarded as FATAL.\r\nEXECUTION WILL NOW HALT!");
    }
}
