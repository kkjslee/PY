package com.infosense.ibilling.server.util.db;

import org.hibernate.criterion.Restrictions;

public class IbillingConstantDAS extends AbstractDAS<IbillingConstant> {
	
	protected IbillingConstantDAS() {
		super();
	}
	
	public IbillingConstant findByName(String name) {
		IbillingConstant table = findByCriteriaSingle(Restrictions.eq("name", name));
		return table;
    }
	
	public static IbillingConstantDAS getInstance() {
        return new IbillingConstantDAS();
    }

}
