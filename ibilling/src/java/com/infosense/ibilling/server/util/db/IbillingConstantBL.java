package com.infosense.ibilling.server.util.db;

import org.springmodules.cache.FlushingModel;
import org.springmodules.cache.provider.CacheProviderFacade;

import com.infosense.ibilling.server.util.Context;

public class IbillingConstantBL {
	
	private IbillingConstantDAS das = null;
    private IbillingConstant constant = null;
    
    private CacheProviderFacade cache;
    private FlushingModel flushModel;
    
    public IbillingConstantBL() {
    	init();
    }
    
    public IbillingConstantBL(IbillingConstant ib) {
    	this.constant = ib;
    	init();
    }
    
    public IbillingConstant getEntity() {
        return constant;
    }
    
    public Integer create(IbillingConstant dto) {
        constant = das.save(dto);
        Integer id = constant.getId();
        invalidateCache();
        return id;
    }
    
    public void update(IbillingConstant dto) {
    	constant.setName(dto.getName());
    	constant.setContent(dto.getContent());
    	constant = das.save(constant);
    	invalidateCache();
    }
    
    public void invalidateCache() {
        cache.flushCache(flushModel);
    }
    
    private void init() {
    	das = new IbillingConstantDAS();
        cache = (CacheProviderFacade) Context.getBean(Context.Name.CACHE);
        flushModel = (FlushingModel) Context.getBean(Context.Name.CACHE_FLUSH_MODEL_ITEM_PRICE);
    }

}
