import java.util.List;

import com.infosense.ibilling.server.user.permisson.db.RoleDTO;
import com.infosense.ibilling.server.user.db.CompanyDTO
import com.infosense.ibilling.server.user.db.UserStatusDTO;
import com.infosense.ibilling.server.user.db.SubscriberStatusDTO;
import com.infosense.ibilling.server.process.db.PeriodUnitDTO;

class SelectionTagLib {
	
	def accountType = { attrs, bodyy -> 
		println System.currentTimeMillis();
		String checking, savings;
		savings=checking= ""
		println "taglib: accountType=" + attrs.value + " " + attrs.name
		
		if (attrs.value == 1 ) {
			checking= "checked=checked" 
		} else if (attrs.value == 2 ){
			savings="checked=checked"
		}
		
		out << "Checking<input type='radio' name=\'" + attrs.name + "\' value=\'1\' " + checking + ">"
		out << "Savings<input type='radio' name=\'"  + attrs.name + "\' value=\'2\' "  + savings + ">"
		
	}
	
	def selectRoles = { attrs, body ->
		
		Integer langId= attrs.languageId?.toInteger();
		String name= attrs.name;		
		String value = attrs.value?.toString()
		
		List list= new ArrayList();
		String[] sarr= null;
		for (RoleDTO role: RoleDTO.createCriteria.list(){
			eq('company', new CompanyDTO(session['company_id']))
			order('roleTypeId', 'asc')
		}) {
			String title= role.getTitle(langId);
			sarr=new String[2]
			sarr[0]= role.getRoleTypeId()
			sarr[1]= title
			list.add(sarr)
		}
		out << render(template:"/selectTag", model:[name:name, list:list, value:value])
	}

	def userStatus = { attrs, body ->
		
		Integer langId= attrs.languageId?.toInteger();
		String name= attrs.name;
		String value = attrs.value?.toString()
        List except = attrs.except ?: []

		List list= new ArrayList();
		String[] sarr= null;
		for (UserStatusDTO status: UserStatusDTO.list()) {
			String title= status.getDescription(langId);			
			sarr=new String[2]
			sarr[0]= status.getId()
			sarr[1]= title
            // add the status if its id is not in the exception List
            if(!except.contains(status.getId())){
                list.add(sarr)
            }
		}
		out << render(template:"/selectTag", model:[name:name, list:list, value:value])
		
	}
	
	def subscriberStatus = { attrs, body ->
		
		Integer langId= attrs.languageId?.toInteger();
		String name= attrs.name;
		String value = attrs.value?.toString()
        String cssClass = attrs.cssClass?.toString()

		log.info "Value of tagName=" + name + " is " + value
		
		List list= new ArrayList();
		String[] sarr= null;
		for (SubscriberStatusDTO status: SubscriberStatusDTO.list()) {
			String title= status.getDescription(langId);
			sarr=new String[2]
			sarr[0]= status.getId()
			sarr[1]= title
			list.add(sarr)
		}
		
		out << render(template:"/selectTag", model:[name:name, list:list, value:value, cssClass: cssClass])
	}

	def periodUnit = { attrs, body ->
		
		Integer langId= attrs.languageId?.toInteger();
		String name= attrs.name;
		String value = attrs.value?.toString()

		log.info "Value of tagName=" + name + " is " + value
		
		List list= new ArrayList();
		String[] sarr= null;
		for (PeriodUnitDTO period: PeriodUnitDTO.list()) {
			String title= period.getDescription(langId);
			sarr=new String[2]
			sarr[0]= period.getId()
			sarr[1]= title
			list.add(sarr)
		}
		
		out << render(template:"/selectTag", model:[name:name, list:list, value:value])
	}
	
	
	def planDesc = { attrs, body ->
		String idDesc = attrs.mapKey
		if(idDesc == null)
			out << ""
		def index= idDesc.indexOf("#")
		
		if(attrs.containsKey("key")){
			if(attrs.key =="id"){
				out << idDesc.substring(0, index)
			}else if(attrs.key == "desc"){
				out << idDesc.substring(index+1)
			}
		}
	}
	def nameFilter = { attrs, body ->
		String name = attrs.name
		if(name == "file" || name== "url" || name =="dir"){
			out << "disabled='true'"
		}
	}
}