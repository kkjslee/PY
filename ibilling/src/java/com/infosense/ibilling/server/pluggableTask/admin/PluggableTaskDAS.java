/*
 * JBILLING CONFIDENTIAL
 * _____________________
 *
 * [2003] - [2012] Enterprise jBilling Software Ltd.
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Enterprise jBilling Software.
 * The intellectual and technical concepts contained
 * herein are proprietary to Enterprise jBilling Software
 * and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden.
 */
package com.infosense.ibilling.server.pluggableTask.admin;

import java.util.HashMap;
import java.util.List;
import java.lang.Integer;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springmodules.cache.CachingModel;
import org.springmodules.cache.FlushingModel;
import org.springmodules.cache.provider.CacheProviderFacade;

import com.infosense.ibilling.server.util.db.AbstractDAS;


public class PluggableTaskDAS extends AbstractDAS<PluggableTaskDTO> {

    private CacheProviderFacade cache;
    private CachingModel cacheModel;
    private FlushingModel flushModel;
    private static final Logger LOG = Logger.getLogger(PluggableTaskDAS.class);


    // QUERIES
    private static final String findAllByEntitySQL =
        "SELECT b " +
        "  FROM PluggableTaskDTO b " + 
        " WHERE b.entityId = :entity";
    
    private static final String findByEntityTypeSQL =
        findAllByEntitySQL + 
        "   AND b.type.id = :type";

    private static final String findByEntityCategoryOrderSQL =
        findAllByEntitySQL + 
        "   AND b.type.category.id = :category" +
        "   AND b.processingOrder = :pr_order";

    private static final String findByEntityCategorySQL =
        "SELECT b " +
        "  FROM PluggableTaskDTO b " + 
        " WHERE b.entityId = :entity " +
        "   AND b.type.category.id = :category" +
        " ORDER BY b.processingOrder";
    
    
    private static final String findAllByEntityAndTypeIdSQL =
            "SELECT b " +
            "  FROM PluggableTaskDTO b " + 
            " WHERE b.entityId = :entity and b.type.pk = :typeId";
    
    private static final String findAllTaskParamWithGroup = 
    		"SELECT count(*),p.strValue " +
    		"FROM PluggableTaskParameterDTO p left join PluggableTaskDTO t on t.id=p.task.id " +
    		"where t.entityId=:entity and t.type.pk= :tId and p.name= :pName " +
    		"group by p.strValue";
    
    private static final String findTaskParamsUnderGroup = 
    		"SELECT p " +
    		"FROM PluggableTaskParameterDTO p left join PluggableTaskDTO t on t.id=p.task.id " +
    		"where t.entityId=:entity and t.type.pk= :tId and p.name= :pName " +
    		"and p.strValue=: sVal ";
    
    private static final String findMaxOrderByEntityAndTypeIdSQL = 
    		 "SELECT MAX(b.processingOrder) " +
	        "  FROM PluggableTaskDTO b " + 
	        " WHERE b.entityId = :entity " +
	        "   AND b.type.category.id = :category" ;
    
    // END OF QUERIES
   
    private PluggableTaskDAS() {
        super();
    }
    
    public List<PluggableTaskDTO> findAllByEntity(Integer entityId) {
        Query query = getSession().createQuery(findAllByEntitySQL);
        query.setParameter("entity", entityId);
        query.setCacheable(true);
        query.setComment("PluggableTaskDAS.findAllByEntity");
        return query.list();
    }
    
    @Deprecated
    public List<PluggableTaskParameterDTO> findAllTaskParamsUnderGroup(Integer entityId, Integer typeId, String paramName, String paramValue) {
        Query query = getSession().createQuery(findTaskParamsUnderGroup);
        query.setParameter("entity", entityId);
        query.setParameter("tId", typeId);
        query.setParameter("pName", paramName);
        query.setParameter("sVal", paramValue);
        query.setCacheable(true);
        return query.list();
    }
    /**
     * 
     * @param entityId
     * @return [value,count]
     */
    @Deprecated
    public HashMap<String,String> findAllTaskParamWithGroup(Integer entityId, Integer typeId, String paramName) {
		Query query = getSession().createQuery(findAllTaskParamWithGroup);
	    query.setParameter("entity", entityId);
	    query.setParameter("tId", typeId);
	    query.setParameter("pName", paramName);
	    query.setCacheable(true);
	    List list = query.list();
    	HashMap<String,String> taskParamGroup = new HashMap<String, String>();
    	String paramValuetKey = null;
    	String paramCount = null;
    	for(int i = 0 ; i <list.size();i++){
    		Object ob[] = (Object[]) list.get(i);
    		paramValuetKey = ob[0].toString();
    		paramCount = ob[1].toString();
    		taskParamGroup.put(paramValuetKey, paramCount);
    	}
    	
    	return taskParamGroup;
    }
    
    public Integer findMaxOrderByEntityAndTypeId(Integer entityId, Integer typeId){
    	Integer orderMaxId = -1;
    	Query query = getSession().createQuery(findMaxOrderByEntityAndTypeIdSQL);
        query.setParameter("entity", entityId);
        query.setParameter("category", typeId);
        query.setCacheable(false);
        List list =  query.list();
        orderMaxId = (Integer) list.get(0);
        return orderMaxId;
    }
    
    public List<PluggableTaskDTO> findAllByEntityAndTypeId(Integer entityId, Integer typeId) {
        Query query = getSession().createQuery(findAllByEntityAndTypeIdSQL);
        query.setParameter("entity", entityId);
        query.setParameter("typeId", typeId);
        query.setCacheable(true);
        query.setComment("PluggableTaskDAS.findAllByEntityAndTypeIdSQL");
        return query.list();
    }
    
    public PluggableTaskDTO findByEntityType(Integer entityId, Integer typeId) {
        Query query = getSession().createQuery(findByEntityTypeSQL);
        query.setCacheable(true);
        query.setParameter("entity", entityId);
        query.setParameter("type", typeId);
        query.setComment("PluggableTaskDAS.findByEntityType");
        return (PluggableTaskDTO) query.uniqueResult();
    }
    
    public PluggableTaskDTO findByEntityCategoryOrder(Integer entityId, Integer categoryId, Integer processingOrder) {
        Query query = getSession().createQuery(findByEntityCategoryOrderSQL);
        query.setCacheable(true);
        query.setParameter("entity", entityId);
        query.setParameter("category", categoryId);
        query.setParameter("pr_order", processingOrder);
        query.setComment("PluggableTaskDAS.findByEntityTypeOrder");
        return (PluggableTaskDTO) query.uniqueResult();
    }

    public List<PluggableTaskDTO> findByEntityCategory(Integer entityId, Integer categoryId) {
        List<PluggableTaskDTO> ret = (List<PluggableTaskDTO>) cache.getFromCache("PluggableTaskDTO" +
                entityId + "+" + categoryId, cacheModel);
        if (ret == null) {
            Query query = getSession().createQuery(findByEntityCategorySQL);
            query.setCacheable(true);
            query.setParameter("entity", entityId);
            query.setParameter("category", categoryId);
            query.setComment("PluggableTaskDAS.findByEntityCategory");

            ret = query.list();
            cache.putInCache("PluggableTaskDTO" +
                entityId + "+" + categoryId, cacheModel, ret);
        }
        return ret;
    }

    public void setCache(CacheProviderFacade cache) {
        this.cache = cache;
    }

    public void setCacheModel(CachingModel model) {
        cacheModel = model;
    }

    public void setFlushModel(FlushingModel flushModel) {
        this.flushModel = flushModel;
    }

    public void invalidateCache() {
        cache.flushCache(flushModel);
    }

    public static PluggableTaskDAS getInstance() {
        return new PluggableTaskDAS();
    }

}