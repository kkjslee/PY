package com.infosense.ibilling.client.i18n

import org.codehaus.groovy.grails.web.metaclass.BindDynamicMethod

class DescriptionHelper {
	
	public static def bindDataAndDescriptions(Object model, modelParams, String prefix){
		bindData(model, modelParams, prefix)
		bindDescription(model, modelParams, prefix)
	}
	
	public static def bindDescription(Object model, modelParams, String prefix){
		def o = prefix? modelParams.get(prefix) : modelParams
		o.descriptions?.each {k, v ->
			model.getDescriptions().put(new Integer(k), v)
		}
	}
	
	private static def bindData(Object model, modelParams, String prefix) {
		def args = [ model, modelParams, [exclude:[], include:[]]]
		if (prefix) args << prefix

		new BindDynamicMethod().invoke(model, 'bind', (Object[]) args)
	}
}
