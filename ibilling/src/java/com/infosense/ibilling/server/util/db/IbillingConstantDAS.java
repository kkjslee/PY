package com.infosense.ibilling.server.util.db;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springmodules.cache.CachingModel;
import org.springmodules.cache.provider.CacheProviderFacade;

import com.infosense.ibilling.common.SessionInternalError;

public class IbillingConstantDAS extends AbstractDAS<IbillingConstant> {
	
	private CacheProviderFacade cache;
    private CachingModel cacheModel;

    private static final Logger LOG = Logger.getLogger(IbillingConstantDAS.class);

	protected IbillingConstantDAS() {
		super();
	}
	
	public IbillingConstant findByName(String name) {
		IbillingConstant table = (IbillingConstant) cache.getFromCache("IbillingConstant" + name, cacheModel);
        if (table == null) {
            LOG.debug("Looking for constant + " + name);
            table = findByCriteriaSingle(Restrictions.eq("name", name));
            if (table == null) {
                throw new SessionInternalError("Can not find constant " + name);
            } else {
                cache.putInCache("IbillingConstant" + name, cacheModel, table);

            }
        }
        return table;
    }
	
	public void setCache(CacheProviderFacade cache) {
        this.cache = cache;
    }

    public void setCacheModel(CachingModel model) {
        cacheModel = model;
    }
	
	public static IbillingConstantDAS getInstance() {
        return new IbillingConstantDAS();
    }

}
