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

import com.infosense.ibilling.client.ViewUtils
import com.infosense.ibilling.client.user.UserHelper
import com.infosense.ibilling.common.CommonConstants;
import com.infosense.ibilling.common.SessionInternalError
import com.infosense.ibilling.server.user.UserBL
import com.infosense.ibilling.server.user.contact.db.ContactDTO
import com.infosense.ibilling.server.user.db.CompanyDTO
import com.infosense.ibilling.server.user.db.UserDTO
import com.infosense.ibilling.server.user.permisson.db.PermissionDTO
import com.infosense.ibilling.server.user.permisson.db.PermissionTypeDTO
import com.infosense.ibilling.server.user.permisson.db.RoleDTO
import com.infosense.ibilling.server.util.Constants
import com.infosense.ibilling.server.util.IWebServicesSessionBean
import com.infosense.ibilling.server.ws.UserWS;

import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.hibernate.FetchMode as FM

@Secured(["MENU_99"])
class UserController {

    static pagination = [ max: 10, offset: 0 ]

    IWebServicesSessionBean webServicesSession
    ViewUtils viewUtils

    def breadcrumbService


    def index = {
        redirect action: list, params: params
    }

    def getList(GrailsParameterMap params) {
        params.max = params?.max?.toInteger() ?: pagination.max
        params.offset = params?.offset?.toInteger() ?: pagination.offset

        return UserDTO.createCriteria().list(
                max:    params.max,
                offset: params.offset
        ) {


            and {
                or {
                    isEmpty('roles')
                    roles {
						or{
							eq('roleTypeId', Constants.TYPE_ROOT)
							eq('roleTypeId', Constants.TYPE_CLERK)
						}
                    }
                }

                eq('company', new CompanyDTO(session['company_id']))
                eq('deleted', 0)
            }
            order('id', 'desc')
        }
    }

    def list = {
        def users = getList(params)
        def selected = params.id ? UserDTO.get(params.int("id")) : null
        def contact = selected ? ContactDTO.findByUserId(selected.id) : null

        def crumbDescription = selected ? UserHelper.getDisplayName(selected, contact) : null
        breadcrumbService.addBreadcrumb(controllerName, 'list', null, selected?.id, crumbDescription)

        if (params.applyFilter || params.partial) {
            render template: 'users', model: [ users: users, selected: selected, contact: contact ]
        } else {
            [ users: users, selected: selected, contact: contact ]
        }
    }

    def show = {
        def user = UserDTO.get(params.int('id'))
        def contact = user ? ContactDTO.findByUserId(user.id) : null

        breadcrumbService.addBreadcrumb(controllerName, 'list', null, user.userId, UserHelper.getDisplayName(user, contact))

        render template: 'show', model: [ selected: user, contact: contact ]
    }

    def edit = {
        def user
        def contacts

        try {
            user = params.id ? webServicesSession.getUserWS(params.int('id')) : new UserWS()
            contacts = params.id ? webServicesSession.getUserContactsWS(user.userId) : null

        } catch (SessionInternalError e) {
            log.error("Could not fetch WS object", e)

            flash.error = 'user.not.found'
            flash.args = [ params.id as String ]

            redirect controller: 'user', action: 'list'
            return
        }
		
		def company = CompanyDTO.createCriteria().get{
			 eq("id", session['company_id'])
			 fetchMode('contactFieldTypes', FM.JOIN)
		}
		
		def roles = RoleDTO.createCriteria().list() {
			eq('company', new CompanyDTO(session['company_id']))
			order('id', 'asc')
		}

        [ user: user, contacts: contacts, company: company, roles: roles ]
    }


    /**
     * Validate and save a user.
     */
    def save = {
        def user = new UserWS()
		
		if(params.user.userId){
			def userId = params.int("user.userId")
			if(userId != 0){
				user.mainRoleId= new UserBL(userId).getMainRole()
			}
		}
		
		if(!user.mainRoleId){
			user.mainRoleId= Constants.TYPE_CLERK
		}
		
        UserHelper.bindUser(user, params)
		
        def contacts = []

		def company = CompanyDTO.createCriteria().get{
			 eq("id", session['company_id'])
			 fetchMode('contactFieldTypes', FM.JOIN)
		}
		
        UserHelper.bindContacts(user, contacts, company, params)

        def oldUser = (user.userId && user.userId != 0) ? webServicesSession.getUserWS(user.userId) : null
        UserHelper.bindPassword(user, oldUser, params, flash)

        if (flash.error) {
            render view: 'edit', model: [ user: user, contacts: contacts, company: company ]
            return
        }

        try {
            // save or update
            if (!oldUser) {
                log.debug("creating user ${user}")

                user.userId = webServicesSession.createUser(user)

                flash.message = 'user.created'
                flash.args = [ user.userId as String ]

            } else {
                log.debug("saving changes to user ${user.userId}")

                webServicesSession.updateUser(user)

                flash.message = 'user.updated'
                flash.args = [ user.userId as String ]
            }

            // save secondary contacts
            if (user.userId) {
                contacts.each{
                    webServicesSession.updateUserContact(user.userId, it.type, it);
                }
            }

        } catch (SessionInternalError e) {
            viewUtils.resolveException(flash, session.locale, e)
            render view: 'edit', model: [ user: user, contacts: contacts, company: company ]
            return
        }

        chain action: 'list', params: [ id: user.userId ]
    }

    def delete = {
        if (params.id) {
            webServicesSession.deleteUser(params.int('id'))
            log.debug("Deleted user ${params.id}.")
        }

        flash.message = 'user.deleted'
        flash.args = [ params.id as String ]

        // render the partial user list
        params.applyFilter = true
        list()
    }
	
	def permission = {
		def bl = new UserBL(params.int('id'))
		
		def user
		try{
			user = bl.getEntity()
		} catch (SessionInternalError e) {
            log.error("Could not find user", e)

            flash.error = 'user.not.found'
            flash.args = [ params.id as String ]

            redirect controller: 'user', action: 'list'
            return
        }
		
		def contact = user ? ContactDTO.findByUserId(user.id) : null
		def roles = user? user.roles : []
		
		def rolePermissions = new HashSet()
		roles.each {
			rolePermissions.addAll(it.permissions)
		}
		
		def userPermission = user? user.permissions : []
		def permissions = new HashSet();
		userPermission.each{
			permissions.add(it.permission)
		}
		
		def permissionTypes = PermissionTypeDTO.createCriteria().list(){
			order('id', 'asc')
		}

		[ user: user , userRoles: roles , contact : contact , permissions : permissions , rolePermissions: rolePermissions, permissionTypes : permissionTypes]
	}
	
	def savePermissions = {
		def user = UserDTO.get(params.int("id"))
		
		def permissions = [] as Set
		params.permission.each{
			log.debug(it.key + "  "+it.value)
			if(it.value == "on"){
				permissions.add(it.key.toInteger())
			}
		}
		
		webServicesSession.updateUserPermission(user, permissions)
		
		chain action: 'list', params: [ id: user.userId ]
	}
	
	def role = {
		def bl = new UserBL(params.int('id'))
		
		def user
		try{
			user = bl.getEntity()
		} catch (SessionInternalError e) {
			log.error("Could not find user", e)

			flash.error = 'user.not.found'
			flash.args = [ params.id as String ]

			redirect controller: 'user', action: 'list'
			return
		}
		
		def contact = user ? ContactDTO.findByUserId(user.id) : null
		def userRoles =  user ? user.roles : []
		
		def roles = RoleDTO.createCriteria().list{
			and {
				eq('company', new CompanyDTO(session['company_id']))
				gt('roleTypeId', CommonConstants.TYPE_CUSTOMER)
			}
			order('id', 'desc')
		}
		
		[ user: user , contact : contact , userRoles : userRoles, roles : roles]
	}
	
	
	def saveRoles = {
		def user = UserDTO.get(params.int("id"))
		
		def roles = [] as Set
		params.role.each{
			log.debug(it.key + "  "+it.value)
			if(it.value == "on"){
				roles.add(it.key.toInteger())
			}
		}
		
		webServicesSession.updateUserRole(user, roles)
		
		chain action: 'list', params: [ id: user.userId ]
	}
	
}
