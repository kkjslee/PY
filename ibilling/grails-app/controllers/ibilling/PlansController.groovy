package ibilling

import grails.plugins.springsecurity.Secured
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.infosense.ibilling.common.SessionInternalError
import com.infosense.ibilling.server.item.db.ItemDAS
import com.infosense.ibilling.server.item.db.ItemDTO
import com.infosense.ibilling.server.pluggableTask.PluggableTask
import com.infosense.ibilling.server.pluggableTask.admin.ParameterDescription;
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskDTO;
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskManager
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskParameterDTO
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskTypeCategoryDAS
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskTypeCategoryDTO
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskTypeDAS
import com.infosense.ibilling.server.pluggableTask.admin.PluggableTaskTypeDTO
import com.infosense.ibilling.server.ws.PluggableTaskWS

@Secured(["MENU_98"])
class PlansController {
	
	def webServicesSession
	def messageSource
	def pluggableTaskDAS
	def breadcrumbService
	def recentItemService
	def viewUtils
	
	String selectedTypeName = "Plan"
	Integer selectedTypeId = 90
	//display order depends on the add order
	Set<String> paraNames = new HashSet<String>(){{
			add("Item")
			add("Quality")
			add("Plan")
		}}
	Integer categoryId = 1
	
	def index = { redirect action: list }
	def list = {
		breadcrumbService.addBreadcrumb("plans", "list", null, null);
		Integer entityId = session.company_id
		Integer languageId = session.language_id;
		log.info "entityId=" + entityId;
		List<PluggableTaskDTO> newList = filteTask(selectedTypeId, selectedTypeName,entityId)
		Map<String , Integer> paramMap = groupTaskList(newList,selectedTypeName,languageId)
		
		log.info "number of paramMap=" + paramMap.size()
		render view: "list", model:[planesGroup:paramMap,selectedType:selectedTypeName]
	}


	def plans = {
		Integer entityId = session.company_id;
		String paramValue = params.pvalue;
		List<PluggableTaskDTO> newList = filteTask(selectedTypeId, selectedTypeName,entityId)
		List<PluggableTaskDTO> paramsList = findTaskPramsUnderGroup(newList,selectedTypeName,paramValue)
		List mapList = formatMapWithTaskList(paramsList)
		render template: "planes", model:[planes:mapList,paraNames:paraNames]
	}

	def show = {
		Integer taskId = params.id.toInteger();
		PluggableTaskDTO dto = pluggableTaskDAS.find(taskId);
		render template: "show", model:[plugin:dto,selectedPlanes:taskId]
	}
	
	def showForm = {
		Integer entityId = session.company_id
		PluggableTaskTypeCategoryDTO category =  new PluggableTaskTypeCategoryDAS().find(categoryId);
		//for now , find 90 type id
		PluggableTaskTypeDTO type = new PluggableTaskTypeDAS().find(selectedTypeId);
		Integer maxOrderId = pluggableTaskDAS.findMaxOrderByEntityAndTypeId(entityId, categoryId)
		Random random = new Random()
		Integer nextOrderId = (maxOrderId?:0) + random.nextInt(5)+1
		
		// show the form with the description
		render (view:"form", model:
				[description:category.getDescription(session.language_id),
				 type:type, nextOrderId:nextOrderId,
				 parametersDesc : getDescriptions(selectedTypeId)])
	}
	
	def save = {
		// Create a new object from the form
		PluggableTaskWS newTask = new PluggableTaskWS();
		bindData(newTask, params);
		for(String key: params.keySet()) { // manually bind the plug-in parameters
			def value = params.get(key)
			if (key.startsWith("plg-parm-") && value) {
				newTask.getParameters().put(key.substring(9), value);
			}
		}
		
		// save
		Locale locale = session.locale;
		try {
			log.info "now saving " + newTask + " by " + session.user_id;
			Integer pluginId;
			if (newTask.getId() == null || newTask.getId() == 0) {
				pluginId = webServicesSession.createPlugin(newTask);
				pluggableTaskDAS.invalidateCache(); // or the list won't have the new plug-in
			
				// the message
				flash.message = messageSource.getMessage("plugins.create.new_plan_saved", [pluginId].toArray(), locale);
			} else {
				// it is an update
				webServicesSession.updatePlugin(newTask);
				flash.message = messageSource.getMessage("plugins.create.plan_updated", [newTask.getId()].toArray(), locale);
				pluginId = newTask.getId();
			}
			redirect (action: edit, params: [ id : pluginId])
		
		} catch(SessionInternalError e) {
			// process the exception so the error messages from validation are
			// put in the flash
			viewUtils.resolveException(flash, locale, e);
			PluggableTaskTypeCategoryDTO category =  new PluggableTaskTypeCategoryDAS().find(categoryId);
			
			// render the form again, with all the data
			render (view:"form", model:
				[description: category.getDescription(session.language_id),
				 types: new PluggableTaskTypeDAS().findAllByCategory(category.getId()),
				 pluginws: newTask,
				 parametersDesc : getDescriptions(newTask.getTypeId())])
		}
	}
	
	def cancel = {
		redirect (action:list)
	}
	
	def edit = {
		PluggableTaskDTO dto =  pluggableTaskDAS.find(params.id as Integer);
		if (dto != null) {
			breadcrumbService.addBreadcrumb("plans", "edit", null, dto.getId());
			recentItemService.addRecentItem(dto.getId(), RecentItemType.PLUGIN);
			PluggableTaskTypeCategoryDTO category =  dto.getType().getCategory();
			PluggableTaskTypeDTO type = new PluggableTaskTypeDAS().find(selectedTypeId);
			render (view:"form", model:
				[description: category.getDescription(session.language_id),
				 type: type,
				 pluginws: new PluggableTaskWS(dto),
				 parametersDesc : getDescriptions(dto.getType().getId())])
		} else {
			flash.error="plugins.error.invalid_id";
			redirect (action:list);
		}
	}
	
	def delete = {
		
		try {
			Integer id = params.id as Integer;
			webServicesSession.deletePlugin(id);
			pluggableTaskDAS.invalidateCache(); // or the list will still show the deleted plug-in
			flash.message = messageSource.getMessage("plans.delete.done",[id].toArray(), session.locale);
		} catch (SessionInternalError e) {
			viewUtils.resolveException(flash, session.locale, e);
		}
		redirect (action:list)
	}
	
	private List<ParameterDescription> getDescriptions(Integer typeId) {
		PluggableTaskTypeDTO type = new PluggableTaskTypeDAS().find(typeId);
		// create a new class to extract the parameters descriptions
		PluggableTask thisTask = PluggableTaskManager.getInstance(type.getClassName(),
			type.getCategory().getInterfaceName());
		return thisTask.getParameterDescriptions();
	}

	/**
	 * filter by type id 90 and type name 'Plan'
	 * @param typeId
	 * @param typeName
	 * @param entityId
	 * @return
	 */
	def filteTask(Integer typeId, String typeName, Integer entityId){
		List<PluggableTaskDTO> taskList = pluggableTaskDAS.findByEntityCategory(entityId, categoryId)
		List<PluggableTaskDTO> newList = new ArrayList<PluggableTaskDTO>()
		for(PluggableTaskDTO dto : taskList){
			Collection<PluggableTaskParameterDTO> parameters = dto.getParameters()
			//make sure the param contains all the required fields
			List<String> names = new ArrayList<String>()
			for(PluggableTaskParameterDTO param:parameters){
				names.add(param.getName())
			}
			if(!names.containsAll(paraNames)){
				continue
			}
			if(dto.getType() != null && dto.getType().getId()==typeId){
				newList.add(dto)
				//continue
			}
			/*for(PluggableTaskParameterDTO param:parameters){
				if(param.getName() != null && param.getName().equals(typeName)){
					newList.add(dto)
					continue
				}
			}*/
		}
		return newList;
	}
	/**
	 * return count map
	 * @param taskList
	 * @param typeName
	 * @return
	 */
	def groupTaskList(List<PluggableTaskDTO> taskList, String typeName, Integer languageId){
		Map<String , Integer> paramMap = new HashMap<String, Integer>()
		Integer count = 0
		for(PluggableTaskDTO dto : taskList){
			Collection<PluggableTaskParameterDTO> parameters = dto.getParameters()
			for(PluggableTaskParameterDTO param:parameters){
				if(param.getName().equals(typeName)){
					String paramValue = param.getStrValue()
					//find related item desc
					Integer itemId = paramValue.toInteger()
					ItemDTO item = new ItemDAS().findNow(itemId)
					String desc = null
					desc = item?.getDescription(languageId, "title")
					desc = desc ?: item?.getDescription(languageId, "description")
					paramValue = paramValue + "#" + (desc?:"")
					if(paramMap.containsKey(paramValue)){
						count = paramMap.get(paramValue)
						paramMap.put(paramValue, ++count)
					}else{
						paramMap.put(paramValue, 1)
					}
					break;
				}
			}

		}
		return paramMap;
	}
	/**
	 * return group members with specified type value
	 * @param taskList
	 * @param typeName
	 * @param typeValue
	 * @return
	 */
	def findTaskPramsUnderGroup(List<PluggableTaskDTO> taskList, String typeName, String typeValue){
		List<PluggableTaskDTO> newList = new ArrayList<PluggableTaskDTO>()
		for(PluggableTaskDTO dto : taskList){
			Collection<PluggableTaskParameterDTO> parameters = dto.getParameters()
			for(PluggableTaskParameterDTO param:parameters){
				if(param.getName().equals(typeName)){
					String paramValue = param.getStrValue()
					if(paramValue.equals(typeValue)){
						newList.add(dto)
						break
					}
				}
			}

		}
		return newList;
	}
	
	/**
	 * ([name:val,name2:val2,name3:val3],...)
	 */
	def formatMapWithTaskList(List<PluggableTaskDTO> taskList){
		HashMap<String, String> paramMap
		List mapList = new ArrayList()
		for(PluggableTaskDTO dto : taskList){
			Collection<PluggableTaskParameterDTO> parameters = dto.getParameters()
			paramMap = new HashMap<String, String>()
			for(PluggableTaskParameterDTO param:parameters){
				/*if(!paraNames.contains(name)){
					paraNames.add(param.getName())
				}*/
				paramMap.put(param.getName(), param.getStrValue())
			}
			paramMap.put("tId", dto.getId())
			mapList.add(paramMap)
			
		}
		return mapList
	}
}
