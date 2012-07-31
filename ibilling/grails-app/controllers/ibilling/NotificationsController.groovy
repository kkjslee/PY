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

package ibilling

import com.infosense.ibilling.server.notification.MessageDTO
import com.infosense.ibilling.server.notification.MessageSection
import com.infosense.ibilling.server.notification.db.NotificationMessageDTO
import com.infosense.ibilling.server.notification.db.NotificationMessageTypeDTO

import com.infosense.ibilling.server.user.db.CompanyDTO
import com.infosense.ibilling.server.util.Constants
import com.infosense.ibilling.server.util.db.AbstractDescription;
import com.infosense.ibilling.server.util.db.LanguageDTO
import com.infosense.ibilling.server.util.db.NotificationCategoryDTO
import com.infosense.ibilling.server.util.db.PreferenceDTO
import com.infosense.ibilling.server.ws.PreferenceTypeWS;
import com.infosense.ibilling.server.ws.PreferenceWS;

import grails.plugins.springsecurity.Secured
import com.infosense.ibilling.common.SessionInternalError;


@Secured(["MENU_99"])
class NotificationsController {
    
    def webServicesSession
	def breadcrumbService
	def viewUtils
	
    def index = { 
        redirect (action: 'listCategories')
    }
    
    def listCategories = {
        List categorylist= NotificationCategoryDTO.list()
        log.debug  "Categories found= ${categorylist?.size()}"
		breadcrumbService.addBreadcrumb(controllerName, actionName, null, null)
        [lst:categorylist]
    }

    def preferences = {
        render template: "preferences", model: [subList:getPreferenceMapByTypeId()]
    }
	
	def list = {
		
		log.debug  "METHOD: list\nId=${params.id} selectedId= ${params.selectedId}"
		Integer categoryId= params.int('id')
		def lstByCateg= NotificationMessageTypeDTO.findAllByCategory(new NotificationCategoryDTO(categoryId))
		breadcrumbService.addBreadcrumb(controllerName, actionName, null, categoryId)
		log.debug  "size of messages=" + lstByCateg.size() + " of total " + NotificationMessageTypeDTO.list()?.size()
		if (params.template)
			render template: 'list', model:[lstByCategory:lstByCateg]
		else 
			render (view: 'listCategories', model: [selected: categoryId, lst: NotificationCategoryDTO.list(), lstByCategory: lstByCateg])
	}

	def getShowModel () {
		Integer messageTypeId= params.id.toInteger()
		
		Integer _languageId= session['language_id']
		if (params.get('language.id')) {
			log.debug  "params.language.id is not null= " + params.get('language.id')
			_languageId= params.get('language.id')?.toInteger()
			log.debug  "setting language id from requrest= " + _languageId
		} 
		
		Integer entityId = webServicesSession.getCallerCompanyId();
		
		NotificationMessageTypeDTO typeDto= NotificationMessageTypeDTO.findById(messageTypeId)
		NotificationMessageDTO dto=null
		NotificationMessageDTO defulatDto=null
		for (NotificationMessageDTO messageDTO: typeDto.getNotificationMessages()) {
			if (messageDTO?.getEntity()?.getId()?.equals(entityId) 
				&& messageDTO.getLanguage().getId().equals(_languageId)) {
				dto= messageDTO;
			}
				
			if (messageDTO?.getEntity()?.getId()?.equals(entityId)
				&& messageDTO.getLanguage().getId().equals(AbstractDescription.DEFAULT_LANGUAGE)) {
				defulatDto= messageDTO;
			}
			
			if(dto!=null && defulatDto!=null){
				break
			}
		}
		
		if (!params.get('language.id')) {
			if(defulatDto == null || defulatDto.useFlag==0){
				dto = defulatDto
				_languageId = AbstractDescription.DEFAULT_LANGUAGE
			}else{
				if(dto==null || dto.useFlag==0){
					dto = defulatDto
					_languageId = AbstractDescription.DEFAULT_LANGUAGE
				}
			}
		}
		
		return [dto:dto, messageTypeId:messageTypeId, languageDto: LanguageDTO.findById(_languageId), entityId:entityId]
	}
	
	def show = {
		log.debug  "METHOD: show"
		log.debug  "Id is=" + params.id
		
		render template:"show", model: getShowModel()
	}
	
	private getPreferenceMapByTypeId () {
		Map<PreferenceDTO> subList= new HashMap<PreferenceDTO>();
		List<PreferenceDTO> masterList= PreferenceDTO.findAllByForeignId(webServicesSession.getCallerCompanyId())
		log.debug  "masterList.size=" + masterList.size()
		for(PreferenceDTO dto: masterList) {
			Integer prefid= dto.getPreferenceType().getId()
			switch (prefid) {
				case Constants.PREFERENCE_TYPE_SELF_DELIVER_PAPER_INVOICES:
				case Constants.PREFERENCE_TYPE_INCLUDE_CUSTOMER_NOTES:
				case Constants.PREFERENCE_TYPE_DAY_BEFORE_ORDER_NOTIF_EXP:
				case Constants.PREFERENCE_TYPE_DAY_BEFORE_ORDER_NOTIF_EXP2:
				case Constants.PREFERENCE_TYPE_DAY_BEFORE_ORDER_NOTIF_EXP3:
				case Constants.PREFERENCE_TYPE_USE_INVOICE_REMINDERS:
				case Constants.PREFERENCE_TYPE_NO_OF_DAYS_INVOICE_GEN_1_REMINDER:
				case Constants.PREFERENCE_TYPE_NO_OF_DAYS_NEXT_REMINDER:
					log.debug  "Adding dto: " + dto.getPreferenceType().getId()
					subList.put(dto.getPreferenceType().getId(), dto)
					break;
			}
		}
		subList
	}
	
	def cancelEditPrefs = {
		render view: "viewPrefs", model: [lst:NotificationCategoryDTO.list(), subList:getPreferenceMapByTypeId()]
	}
	
	def editPreferences = {
		Map<PreferenceDTO> subList= new HashMap<PreferenceDTO>();
		List<PreferenceDTO> masterList= PreferenceDTO.findAllByForeignId(webServicesSession.getCallerCompanyId())
		log.debug  "masterList.size=" + masterList.size()
		for(PreferenceDTO dto: masterList) {
			Integer prefid= dto.getPreferenceType().getId()
			switch (prefid) {
				case Constants.PREFERENCE_TYPE_SELF_DELIVER_PAPER_INVOICES:
				case Constants.PREFERENCE_TYPE_INCLUDE_CUSTOMER_NOTES:
				case Constants.PREFERENCE_TYPE_DAY_BEFORE_ORDER_NOTIF_EXP:
				case Constants.PREFERENCE_TYPE_DAY_BEFORE_ORDER_NOTIF_EXP2:
				case Constants.PREFERENCE_TYPE_DAY_BEFORE_ORDER_NOTIF_EXP3:
				case Constants.PREFERENCE_TYPE_USE_INVOICE_REMINDERS:
				case Constants.PREFERENCE_TYPE_NO_OF_DAYS_INVOICE_GEN_1_REMINDER:
				case Constants.PREFERENCE_TYPE_NO_OF_DAYS_NEXT_REMINDER:
					log.debug  "Adding dto: " + dto.getPreferenceType().getId()
					subList.put(dto.getPreferenceType().getId(), dto)
					break;
			}
		}
		breadcrumbService.addBreadcrumb(controllerName, actionName, null, null)
		[subList:subList, languageId:session['language_id']]
	}

    def savePrefs ={
        log.debug  "pref[5].value=" + params.get("pref[5].value")
		List<PreferenceWS> prefDTOs
		
		try {
			prefDTOs=bindDTOs(params)
		} catch (SessionInternalError e) {
			viewUtils.resolveExceptionForValidation(flash, session.locale, e);
			redirect action: "editPreferences"
		}
        log.debug  "Calling: webServicesSession.saveNotificationPreferences(prefDTOs); List Size: " + prefDTOs.size()
		PreferenceWS[] array= new PreferenceWS[prefDTOs.size()]
		array= prefDTOs.toArray(array)
        try {
			webServicesSession.updatePreferences(array)
        } catch (SessionInternalError e) {
			log.error "Error: " + e.getMessage()
			flash.errorMessages= e.getErrorMessages()
			//boolean retValue = viewUtils.resolveExceptionForValidation(flash, session.'org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE', e);			
		}
        log.debug  "Finished: webServicesSession.saveNotificationPreferences(prefDTOs);"
		if (flash.errorMessages?.size() > 0 )
		{
			redirect (action:editPreferences)
		} else {
	        flash.message = 'preference.saved.success'
	        redirect (action:cancelEditPrefs)
		}
    }


	def List<PreferenceWS> bindDTOs(params)  {
        log.debug  "bindDTOs"
        List<PreferenceWS> prefDTOs= new ArrayList<PreferenceWS>();

        def count = params.recCnt.toInteger()

        for (int i = 0; i < count; i++) {
            log.debug  "loop=" + params.get("pref["+i+"].id")
            PreferenceWS dto = new PreferenceWS()
            dto.setPreferenceType(new PreferenceTypeWS())

            dto.setForeignId(webServicesSession.getCallerCompanyId())

            bindData(dto, params["pref["+i+"]"])

            switch (i) {
                case 0:
                case 1:
                case 5:
                    if (params["pref["+i+"].value"]) {
                        dto.setValue("1")
                    } else {
                        dto.setValue("0")
                    }
                    break;
                default:
                    if (params["pref["+i+"].value"]) {
                        def val = params["pref["+i+"].value"]
                        try {
                            Integer value = val.toInteger()
                            dto.setValue(value?.toString())
                        } catch (NumberFormatException e) {
                            SessionInternalError exception = new SessionInternalError("Validation of Preference Value");
                            String [] errmsgs= new String[1]
                            errmsgs[0]="PreferenceWS,intValue,validation.error.nonnumeric.days.order.notification," + val;
                            exception.setErrorMessages(errmsgs);
                            throw exception;
                        }
                    } else {
                        dto.setValue("0")
                    }
            }
            log.debug  "dto.intValue=" + dto.value
            prefDTOs.add(dto);
        }
        return prefDTOs;
    }

	def edit = {
        log.debug  "METHOD: edit"
        
		//set cookies here..
		log.debug  ("doNotAskAgain=" + params.doNotAskAgain + " askPreference=" + params.askPreference)
		
		def askPreference=request.getCookie("doNotAskAgain")
		log.debug  ("Cooke set to was=" + askPreference)
		if ( "true".equals(params.doNotAskAgain) ){
			response.setCookie('doNotAskAgain', String.valueOf(params.askPreference),604800)
			log.debug  ("Setting the cookie to value ${params.askPreference}")
			askPreference= params.askPreference
		}
		
		log.debug  "Id is=" + params.id
		Integer messageTypeId= params.id?.toInteger()
		
		if (!messageTypeId) {
			redirect action: 'listCategories'
		}
		
		Integer _languageId= session['language_id']
		if (params.get('language.id')) {
			log.debug  "Param 'language.id' is Not Null [${params.language.id}]"
			_languageId= params.get('language.id')?.toInteger()
		}
		Integer entityId = webServicesSession.getCallerCompanyId()?.toInteger()

		log.debug  "Language Id Set to ${_languageId}, Entity ${entityId}, askPreference= $askPreference}"

		NotificationMessageTypeDTO typeDto= NotificationMessageTypeDTO.findById(messageTypeId)
		NotificationMessageDTO dto=null
		NotificationMessageDTO defulatDto=null
		for (NotificationMessageDTO messageDTO: typeDto.getNotificationMessages()) {
			if (messageDTO?.getEntity()?.getId()?.equals(entityId)
				&& messageDTO.getLanguage().getId().equals(_languageId)) {
				dto= messageDTO;
			}
				
			if (messageDTO?.getEntity()?.getId()?.equals(entityId)
				&& messageDTO.getLanguage().getId().equals(AbstractDescription.DEFAULT_LANGUAGE)) {
				defulatDto= messageDTO;
			}
			
			if(dto!=null && defulatDto!=null){
				break
			}
		}
		
		if (params.get('language.id')) {
			if(defulatDto == null || defulatDto.useFlag==0){
				if(_languageId != AbstractDescription.DEFAULT_LANGUAGE){
					flash.error = 'notification.edit.default.first'
				}
				
				dto = defulatDto
				_languageId = AbstractDescription.DEFAULT_LANGUAGE
			}
		}else{
			if(defulatDto == null || defulatDto.useFlag==0){
				dto = defulatDto
				_languageId = AbstractDescription.DEFAULT_LANGUAGE
			}
		}
		breadcrumbService.addBreadcrumb(controllerName, actionName, null, messageTypeId)
		
		[dto:dto, messageTypeId: messageTypeId, languageId:_languageId, entityId:entityId, askPreference:askPreference]
	}
	
	def saveAndRedirect = {
        log.debug  "METHOD: saveAndRedirect"
		try {
			saveAction(params)
		} catch (SessionInternalError e) {
			log.error "Error: " + e.getMessage()
			flash.error= "error.illegal.modification"
		}
        redirect (action:edit, params:params)
    }
    
    def saveNotification = {
        log.debug  "METHOD: saveNotification"
		def _id=params._id
        try {
			saveAction(params)
		} catch (SessionInternalError e) {
			log.error "Error: " + e.getMessage()
			flash.error= "error.illegal.modification"
		}
        redirect (action: 'cancelEdit', params: [id: _id])
    }
    
    def saveAction(params) {

        log.debug "METHOD: saveAction\nAll params\n${params}"
        
        NotificationMessageDTO msgDTO = new NotificationMessageDTO()
        msgDTO.setLanguage(new LanguageDTO())
        msgDTO.setEntity(new CompanyDTO())
        bindData(msgDTO, params)
        def _id = null;
        if (params._id) {
            _id=params._id?.toInteger()
            msgDTO.setId(_id)
        }
        
        log.debug  "useFlag: '${params.useFlag}', NotificationMessageDTO.useFlag=${msgDTO.getUseFlag()}"
        if ('on' == params.useFlag) {
            msgDTO.setUseFlag((short)1)
        } else {
            msgDTO.setUseFlag((short)0)
        }
        
        log.debug  "NotificationMessageType ID=${_id}, Entity=${params.get('entity.id')?.toInteger()}, Language = ${params._languageId}"
        MessageDTO messageDTO= new MessageDTO()
        messageDTO.setTypeId(_id)
        messageDTO.setLanguageId(params.get('_languageId')?.toInteger())
        messageDTO.setUseFlag(1 == msgDTO.getUseFlag())
        messageDTO.setContent(bindSections(params))

        Integer entityId= params.get('entity.id')?.toInteger()
        Integer messageId= null;
        if (params.msgDTOId) {
            messageId= params.msgDTOId.toInteger()
        } else {
            //new record
            messageId= null;
        }        

        log.debug  "msgDTO.language.id=" + messageDTO?.getLanguageId()
        log.debug  "msgDTO.type.id=" + messageDTO?.getTypeId()
        log.debug  "msgDTO.use.flag=" + messageDTO.getUseFlag()
        
        log.debug  "EntityId = ${entityId?.intValue()}, callerCompanyId= ${webServicesSession.getCallerCompanyId()?.intValue()}"
		if (entityId?.intValue() == webServicesSession.getCallerCompanyId()?.intValue()) {
            log.debug "Calling createUpdateNotifications..."
            try {
                webServicesSession.createUpdateNofications(messageId, messageDTO)
                flash.message = 'notification.save.success'
            } catch (Exception e) {
                log.error("ERROR: " + e.getMessage())
                throw new SessionInternalError(e)
            }
		} else {
            log.error("ERROR: Entity Idis do not match.")
            throw new SessionInternalError("Cannot update another company data.")
        }
    }

    def MessageSection[] bindSections (params) {
        log.debug  "METHOD: bindSections"
        MessageSection[] lines= new MessageSection[3];
        Integer section= null;
        String content= null;
        MessageSection obj= null;
        
        for ( int i=1; i<= 3 ; i++) {
            log.debug  "messageSections[" + i + "].section=" + params.get("messageSections[" + i + "].section")
            log.debug  "messageSections[" + i + "].id=" + params.get("messageSections[" + i + "].id")
            
            if (params.get("messageSections[" + i + "].notificationMessageLines.content")) {
                content= params.get("messageSections[" + i + "].notificationMessageLines.content")
                obj= new MessageSection(i, content)
            } else {
                obj= new MessageSection(i, "")
            }	        
            lines[(i-1)]= obj;
        }
        log.debug  "Line 1= " + lines[0]
        log.debug  "Line 2= " + lines[1]
        log.debug  "Line 3= " + lines[2]
        return lines;
    }
	
    def saveAndCancel = {
        log.debug  "METHOD: saveAndCancel"
        try {
            saveAction(params)
        } catch (SessionInternalError e) {
            log.error "Error: " + e.getMessage()
            flash.error= "error.illegal.modification"
        }
        redirect (action:cancelEdit, params:params)
    }
    
	def cancelEdit = {
		log.debug  "METHOD: cancelEdit\nid=${params.id}"
		
		def model = getShowModel()
		NotificationMessageTypeDTO typeDto= NotificationMessageTypeDTO.findById(model.messageTypeId)
		def lstByCateg= NotificationMessageTypeDTO.findAllByCategory(new NotificationCategoryDTO(typeDto.getCategory().getId()))
		
		model.put('lstByCategory', lstByCateg)
		
		return model
	}

}
