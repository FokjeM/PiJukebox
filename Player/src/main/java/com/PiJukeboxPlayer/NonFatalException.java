package com.PiJukeboxPlayer;

/**
 * Non-fatal exception to be thrown in the PiJukebox
 * @author Riven
 */
class NonFatalException extends Exception {

    /**
     * A generic, non-fatal exception.
     * We can get back on our feet after this happens
     * This is the protected, complete version
     * 
     * @param message The message that identifies what went wrong, specifically
     * @param cause The cause this error was thrown.
     *          WE NEED THIS (even though it's probably an IOException)
     * @param enableSuppression Whether or not we can suppress this Exception
     * @param writableStackTrace Whether or not we can write the StackTrace
     */
    protected NonFatalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super("NON-FATAL: " + message, cause, enableSuppression, writableStackTrace);
    }
    
    /**
     * A generic, non-fatal exception.
     * We can get back on our feet after this happens
     * We can suppress this one, always. StackTrace is read-only though.
     * 
     * @param message The message that identifies what went wrong, specifically
     * @param cause The cause this error was thrown.
     *          WE NEED THIS (even though it's probably an IOException)
     */
    public NonFatalException(String message, Throwable cause) {
        this(message, cause, true, false);
    }
    
    /**
     * Something went horribly wrong. Someone provided no details and expects
     * we can still recover from this. Nope, FATAL EXCEPTION INCOMING
     * 
     * @throws FatalException Don't do this, it makes you evil.
     */
    public NonFatalException() throws FatalException {
        throw new FatalException(this);
    }
    
    /**
     * Something went very wrong. Someone provided no cause and expects we can
     * still recover from this. We might, but we won't. FATAL EXCEPTION INCOMING
     * 
     * @param message The message providing some detail of the error. Good start
     * @throws FatalException Don't do this, it's almost completely useless
     */
    public NonFatalException(String message) throws FatalException {
        throw new FatalException(message, this);
    }
    
    /**
     * Something went wrong. Someone forgot to provide a message to indicate the
     * more detailed information of the error/exception they caught. At least we
     * can figure out the cause, which *might* help somewhat.
     * @param cause The cause this error was thrown.
     *          WE NEED THIS (even though it's probably an IOException)
     * @throws FatalException Don't do this, we need more detail
     */
    public NonFatalException(Throwable cause) throws FatalException {
        throw new FatalException(cause);
    }
    
    
}
