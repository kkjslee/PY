package com.infosense.ibilling.server.util.api;

/**
 * This is a checked exception because the client should always catch it and decide
 * what to do. A failure on a call is not necessary an unrecoverable error.
 *
 */
public class IbillingAPIException extends Exception {
    public IbillingAPIException() {
        super();
    }

    public IbillingAPIException(String s) {
        super(s);
    }
    
    public IbillingAPIException(Exception e) {
        super(e);
    }
}
