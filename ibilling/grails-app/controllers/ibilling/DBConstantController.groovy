
package ibilling

import grails.plugins.springsecurity.Secured
import com.infosense.ibilling.server.user.contact.db.ContactTypeDTO
import com.infosense.ibilling.server.user.contact.db.ContactDTO
import com.infosense.ibilling.server.user.db.CompanyDTO
import com.infosense.ibilling.server.util.db.LanguageDTO
import com.infosense.ibilling.server.util.db.InternationalDescription
import com.infosense.ibilling.server.ws.ContactTypeWS;
import com.infosense.ibilling.server.ws.InternationalDescriptionWS;
import com.infosense.ibilling.common.SessionInternalError

import com.infosense.ibilling.server.util.db.IbillingConstant
import com.infosense.ibilling.server.util.db.IbillingConstantDAS
@Secured(["MENU_99"])
class DBConstantController {

    def viewUtils
	def messageSource
    def recentItemService
    def breadcrumbService
	def ibillingConstantDAS

    def index = {
        redirect action: list, params: params
    }

    def list = {

        def constants = ibillingConstantDAS.findAll()

        def selected = params.id ? ibillingConstantDAS.find(params.int("id")) : null

        breadcrumbService.addBreadcrumb(controllerName, actionName, null, null)

        [ constants: constants, selected: selected]
    }
	
    def show = {
        def selected = ibillingConstantDAS.find(params.int('id'))
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, params.int('id'))

        render template: 'show', model: [ selected: selected]
    }

    def add = {
        render template: 'show'
    }

    def save = {
		Locale locale = session.locale
		def tid = params?.id?:''
		Integer cid = 0
		if(tid != ''){
				cid = tid as Integer
		}
		def name = params?.name?:''
		def content = params?.content?:''
		def create = false
		if(name == '' || content == ''){
			flash.error = messageSource.getMessage("dbconstant.name.required",null,locale);
			redirect action: list
			return
		}
		//make sure the name is unique
		def ibillingConstant =ibillingConstantDAS.findByName(name)
		if(cid == 0 ){
			if(ibillingConstant !=null){
				flash.error = messageSource.getMessage("dbconstant.name.unique",[name].toArray(), locale)
				redirect action: list
				return
			}
			create = true
		}
		if(ibillingConstant == null){
			ibillingConstant = new IbillingConstant()
		}
		bindData(ibillingConstant, params)
		
        try {
            log.debug("creating new constant")
            ibillingConstant = ibillingConstantDAS.save(ibillingConstant)
			cid = ibillingConstant.getId()
			if(create){
				flash.message = 'dbconstant.name.create'
			}else{
			flash.message = 'dbconstant.name.update'
			}
			

        } catch (SessionInternalError e) {
            viewUtils.resolveException(flash, session.locale, e)
        }

        redirect action:list, id:cid
    }
	
	def delete = {
		
		try {
			Integer id = params.id as Integer;
			IbillingConstant ibillingConstant = ibillingConstantDAS.find(id)
			if(ibillingConstant != null && ibillingConstant.getId() >0 ){
				ibillingConstantDAS.delete(ibillingConstant)
				flash.message = messageSource.getMessage("dbconstant.delete.done",[ibillingConstant.getName()].toArray(), session.locale);
			}else{
			flash.error = messageSource.getMessage("dbconstant.delete.error",[ibillingConstant.getName()].toArray(), session.locale);
			}
			
		} catch (SessionInternalError e) {
			viewUtils.resolveException(flash, session.locale, e);
		}
		redirect (action:list)
	}
}

