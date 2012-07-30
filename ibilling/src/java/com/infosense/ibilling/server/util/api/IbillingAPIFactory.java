package com.infosense.ibilling.server.util.api;

import java.io.IOException;

public final class IbillingAPIFactory {
    
    private IbillingAPIFactory() {}; // a factory should not be instantiated
    
    static public IbillingAPI getAPI() 
            throws IbillingAPIException, IOException {
        return new SpringAPI();
    }
}
