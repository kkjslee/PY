package ibilling

import grails.converters.JSON;
import grails.plugins.springsecurity.Secured;

import com.infosense.ibilling.server.util.Context;
import com.infosense.ibilling.server.util.db.InternationalDescriptionDTO;
import com.infosense.ibilling.server.util.db.LanguageDAS
import com.infosense.ibilling.server.util.db.LanguageDTO;

@Secured("isAuthenticated()")
class DescriptionController {
	
	def getContent = {
		
		def das = Context.getBean(Context.Name.DESCRIPTION_DAS)
		def desc = das.findIt(
			params?.tableName, params?.int("foreignId"),
			params?.psudoColumn, params?.int("language")
		)
		
		def content;
		try{
			content = desc?.content
		}catch(Exception e){
			log.debug(e.getMessage())
		}
		
		def ret = [
			content : content,
			languageId : params?.int("language"),
			language : new LanguageDAS().find(params?.int("language")).description
		]
		render ret as JSON
	}
	
}
