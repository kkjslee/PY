package ibilling

import grails.plugins.springsecurity.Secured
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.infosense.ibilling.server.pluggableTask.PluggableTask
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskParameterDTO
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskDAS
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskTypeDAS
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskTypeDTO
import com.infosense.ibilling.server.ws.PluggableTaskWS
import com.infosense.ibilling.server.util.Constants
import com.infosense.ibilling.common.SessionInternalError
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskTypeDAS
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskParameterDAS
import org.hibernate.criterion.Restrictions
@Secured(["MENU_98"])
class DiscountController {
	
	def webServicesSession
	def messageSource
	def pluggableTaskDAS
	def breadcrumbService
	def recentItemService
	def viewUtils
	def ibillingConstantDAS
	
	Integer selectedCategoryId = Constants.PLUGGABLE_TASK_ITEM_PRICING
	
	def index = { 
		redirect action: list 
	}
	
	def list = {
		breadcrumbService.addBreadcrumb("discount", "list", null, null);
		Integer entityId = session.company_id
		Integer languageId = session.language_id;
		List<PluggableTaskTypeDTO> typeList = new PluggableTaskTypeDAS().findAllByCategory(selectedCategoryId)
		render view: "list", model:[typeList:typeList]
	}


	def items = {
		Integer entityId = session.company_id;
		Integer typeid = params.typeid as Integer;
		PluggableTaskDTO task = pluggableTaskDAS.findByEntityType(entityId, typeid)
		List<PluggableTaskParameterDTO> items = new ArrayList()
		if(task != null){
			items = new PluggableTaskParameterDAS().findByCriteria(Restrictions.eq("task.id",task.getId()))
		}
		PluggableTaskParameterDTO selected = null
		def  cid = params.cid?:null
		if(cid !=null ) {
			selected = new PluggableTaskParameterDAS().find(params.int('cid'))
			render view: "list", model:[items:items,typeid:typeid,selected:selected]
			return
		}
		render template: "discounts", model:[items:items,typeid:typeid]
	}
	
	def show = {
		Integer taskid = params.taskid as Integer;
		Integer typeid = params.typeid as Integer;
		def selected = new PluggableTaskParameterDAS().find(params.int('id'))
		render template: 'show', model: [ selected: selected,taskid:taskid,typeid:typeid]
	}
	
	def add = {
		Integer typeid = params.typeid as Integer;
		render template: 'show', model: [typeid:typeid]
	}

	def save = {
		//Integer taskid = params.taskid as Integer;
		Integer typeid = params.typeid as Integer;
		Integer entityId = session.company_id;
		PluggableTaskDTO task = pluggableTaskDAS.findByEntityType(entityId, typeid)
		Integer taskid = task.getId()
		Locale locale = session.locale
		def tid = params?.id?:''
		Integer cid = 0
		if(tid != ''){
				cid = tid as Integer
		}
		def name = params?.name?:''
		def content = params?.strValue?:''
		def create = false
		if(name == '' || content == ''){
			flash.error = messageSource.getMessage("discount.name.required",null,locale);
			redirect action: list
			return
		}
		PluggableTaskParameterDTO item = new PluggableTaskParameterDAS().findByName(name,taskid)
		if(cid == 0 ){
			if(item !=null){
				flash.error = messageSource.getMessage("discount.name.unique",[name].toArray(), locale)
				redirect action: list
				return
			}
			create = true
		}else{
			item = new PluggableTaskParameterDAS().find(cid)
		}
		if(item == null){
			item = new PluggableTaskParameterDTO()
		}
		item.setName(name)
		item.setStrValue(content)
		item.setTask(task)
		try {
			item = new PluggableTaskParameterDAS().save(item)
			cid = item.getId()
			if(create){
				flash.message = 'discount.name.create'
			}else{
			flash.message = 'discount.name.update'
			}
			

		} catch (SessionInternalError e) {
			viewUtils.resolveException(flash, session.locale, e)
		}

		redirect (action:items, params:[cid:cid,typeid:typeid])
	}
	
	def delete = {
		
		try {
			Integer id = params.id as Integer;
			PluggableTaskParameterDAS das = new PluggableTaskParameterDAS()
			PluggableTaskParameterDTO item = das.find(id)
			if(item != null && item.getId() >0 ){
				das.delete(item)
				flash.message = messageSource.getMessage("discount.delete.done",[item.getName()].toArray(), session.locale);
			}else{
			flash.error = messageSource.getMessage("discount.delete.error",[item.getName()].toArray(), session.locale);
			}
			
		} catch (SessionInternalError e) {
			viewUtils.resolveException(flash, session.locale, e);
		}
		redirect (action:list)
	}

	
}
