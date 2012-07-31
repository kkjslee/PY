package com.infosense.ibilling.server.user;

import java.sql.SQLException;
import java.util.Locale;
import java.util.List;

import javax.naming.NamingException;

import javax.sql.rowset.CachedRowSet;

import com.infosense.ibilling.common.SessionInternalError;
import com.infosense.ibilling.server.list.ResultList;
import com.infosense.ibilling.server.user.contact.db.ContactDAS;
import com.infosense.ibilling.server.user.contact.db.ContactDTO;
import com.infosense.ibilling.server.user.db.CompanyDAS;
import com.infosense.ibilling.server.user.db.CompanyDTO;
import com.infosense.ibilling.server.user.permisson.db.RoleDAS;
import com.infosense.ibilling.server.user.permisson.db.RoleDTO;
import com.infosense.ibilling.server.util.Constants;
import com.infosense.ibilling.server.util.audit.EventLogger;
import com.infosense.ibilling.server.util.db.LanguageDAS;
import com.infosense.ibilling.server.util.db.LanguageDTO;
import com.infosense.ibilling.server.ws.CompanyWS;
import com.infosense.ibilling.server.ws.ContactWS;

import java.util.ArrayList;

/**
 * @author Emil
 */
public class EntityBL extends ResultList 
        implements EntitySQL {
    private CompanyDAS das = null;
    private CompanyDTO entity = null;
    private EventLogger eLogger = null;
    
    public EntityBL()  {
        init();
    }
    
    public EntityBL(Integer id)  {
        init();
        entity = das.find(id);
    }

    /*
    public EntityBL(String externalId) 
            throws FinderException, NamingException {
        init();
        entity = entityHome.findByExternalId(externalId);
    }
    */
    
    private void init() {
        das = new CompanyDAS();
        eLogger = EventLogger.getInstance();
    }
    
    public CompanyDTO getEntity() {
        return entity;
    }
    
    public Locale getLocale()  {
        // get the language first
        Integer languageId = entity.getLanguageId();
        LanguageDTO language = new LanguageDAS().find(languageId);
        String languageCode = language.getCode();
        String countryCode = language.getCountryCode();
        
        return new Locale(languageCode, countryCode);
    }

    public ContactDTO getContact() {
        //get company contact
        ContactBL contact = new ContactBL();
        contact.setEntity(entity.getId());
        return contact.getEntity();
    }
    
    public Integer[] getAllIDs() 
            throws SQLException, NamingException {
        List list = new ArrayList();
        
        prepareStatement(EntitySQL.listAll);
        execute();
        conn.close();
        
        while (cachedResults.next()) {
            list.add(new Integer(cachedResults.getInt(1)));
        } 
        
        Integer[] retValue = new Integer[list.size()];
        list.toArray(retValue);
        return retValue;
    }
    
    public CachedRowSet getTables() 
            throws SQLException, NamingException {
        prepareStatement(EntitySQL.getTables);
        execute();
        conn.close();
        
        return cachedResults;
    }
    
    public Integer getRootUser(Integer entityId) {
        try {
        	RoleDTO rootRole = new RoleDAS().findByRoleTypeIdAndCompanyId(Constants.TYPE_ROOT, entityId);
            prepareStatement(EntitySQL.findRoot);
            cachedResults.setInt(1, entityId);
            cachedResults.setInt(2, rootRole.getId());

            execute();
            conn.close();
            
            cachedResults.next();
            return cachedResults.getInt(1);
        } catch (Exception e) {
            throw new SessionInternalError("Finding root user for entity " + 
                    entity.getId(), EntityBL.class, e);
        } 
    }
    
    public void updateEntityAndContact(CompanyWS companyWS, Integer entityId, Integer userId) {
        CompanyDTO dto= companyWS.getDTO();
            ContactWS contactWs= companyWS.getContact();
            ContactBL contactBl= new ContactBL();
            contactBl.setEntity(entityId);
            ContactDTO contact= contactBl.getEntity();
            contact.setAddress1(contactWs.getAddress1());
            contact.setAddress2(contactWs.getAddress2());
            contact.setCity(contactWs.getCity());
            contact.setCountryCode(contactWs.getCountryCode());
            contact.setPostalCode(contactWs.getPostalCode());
            contact.setStateProvince(contactWs.getStateProvince());
            contact.setCountryCode(contactWs.getCountryCode());
            new ContactDAS().save(contact);
            eLogger.auditBySystem(entityId,
                    userId, Constants.TABLE_CONTACT,
                    contact.getId(),
                    EventLogger.MODULE_WEBSERVICES,
                    EventLogger.ROW_UPDATED, null, null, null);
        new CompanyDAS().save(dto);
        eLogger.auditBySystem(entityId,
                userId, Constants.TABLE_ENTITY,
                entityId,
                EventLogger.MODULE_WEBSERVICES,
                EventLogger.ROW_UPDATED, null, null, null);
    }
}
