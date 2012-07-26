package ibilling

import grails.plugins.springsecurity.Secured;

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap;

import com.infosense.ibilling.client.i18n.DescriptionHelper;
import com.infosense.ibilling.common.CommonConstants;
import com.infosense.ibilling.server.user.UserBL
import com.infosense.ibilling.server.user.db.CompanyDTO
import com.infosense.ibilling.server.user.permisson.db.PermissionDTO;
import com.infosense.ibilling.server.user.permisson.db.PermissionTypeDTO;
import com.infosense.ibilling.server.user.permisson.db.RoleDAS;
import com.infosense.ibilling.server.user.permisson.db.RoleDTO;
import com.infosense.ibilling.server.user.permisson.db.RoleDTOEx;
import com.infosense.ibilling.server.util.IWebServicesSessionBean;

@Secured(["MENU_99"])
class RoleController {
	
	static pagination = [ max: 10, offset: 0 ]
	
	IWebServicesSessionBean webServicesSession
	
	def breadcrumbService
	
	def index = {
		redirect action: list, params: params
	}
	
	def getList(GrailsParameterMap params) {
		params.max = params?.max?.toInteger() ?: pagination.max
		params.offset = params?.offset?.toInteger() ?: pagination.offset

		return RoleDTO.createCriteria().list(
				max:    params.max,
				offset: params.offset
		) {
			and {
				eq('company', new CompanyDTO(session['company_id']))
				gt('roleTypeId', CommonConstants.TYPE_CUSTOMER)
			}
			order('id', 'desc')
		}
	}
	
	def getRole(Integer id){
		def roles = RoleDTO.createCriteria().list{
			and {
				eq('id', id)
				eq('company', new CompanyDTO(session['company_id']))
				gt('roleTypeId', CommonConstants.TYPE_CUSTOMER)
			}
		}
		
		if(roles && roles.size>0){
			return roles[0]
		}
		
		return null
	}
	
	def list = {
		def roles = getList(params)
		def selected = params.id ? getRole(params.int("id")) : null
		
		breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id)
		
		if (params.applyFilter || params.partial) {
			render template: 'roles', model: [ roles: roles, selected: selected ]
		} else {
			[ roles: roles, selected: selected ]
		}
	}
	
	def show = {
		def role = getRole(params.int('id'))
		
		breadcrumbService.addBreadcrumb(controllerName, 'list', null, role?.id)

		render template: 'show', model: [ selected: role ]
	}
	
	def edit = {
		def role
		def role_id
		
		try {
			role = params.id ? getRole(params.int('id')) : new RoleDTO()
			
			role_id = role.id
        } catch (Exception e) {
            log.error("Could not find role", e)

            flash.error = 'role.not.found'
            flash.args = [ params.id as String ]

            redirect controller: 'role', action: 'list'
            return
        }
		
		def permissionTypes = PermissionTypeDTO.createCriteria().list(){
			order('id', 'asc')
		}

        [ role: role , permissionTypes : permissionTypes]
	}
	
	
	def save = {
		def role = new RoleDTOEx()
		DescriptionHelper.bindDataAndDescriptions(role, params, "role")
		
		def permissions = [] as Set
		params.permission.each{
			log.debug(it.key + "  "+it.value) 
			if(it.value == "on"){
				permissions.add(it.key.toInteger())
			}
		}
		
		if(role.id && role.id != 0){
			if( getRole(role.id) ){
				webServicesSession.updateRole(role, permissions)
			}
		}else{
			role.id = webServicesSession.createRole(role, permissions)
		}
		
		chain action: 'list', params: [ id: role.id ]
	}
	
	def delete = {
		if (params.id) {
			def role = getRole(params.int('id'))
			if(role){
				webServicesSession.deleteRole(role.id)
				log.debug("Deleted role ${params.id}.")
			}
		}

		flash.message = 'role.deleted'
		flash.args = [ params.id as String ]

		// render the partial user list
		params.applyFilter = true
		list()
	}

}
