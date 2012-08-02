
<html>
<head>
<title>Main Page</title>
<meta name="layout" content="main" />

</head>
<body>
            <div class="form-edit">
                <div class="heading">
                    <strong>
                    <g:if test="${pluginws}">
                        <g:message code="plans.update.title"/>
                    </g:if>
                    <g:else>
                        <g:message code="plans.create.title"/>
                    </g:else>
                    </strong>
                </div>
                <div class="form-hold">
                    <g:form name="plugin-form" action="save">
                        <fieldset>
                            <div class="form-columns">
                                <div class="one_column" style="width:650px">
                                    <g:hiddenField name="versionNumber" value="${pluginws?.versionNumber}" />
                                    <g:if test="${pluginws?.id > 0}">
                                        <g:set var="this_plugin_id" value="${pluginws?.id}"/>
                                        <g:hiddenField name="id" value="${pluginws?.id}" />
                                        <div class="row">
                                        	<p><g:message code="plugins.plugin.id-long"/></p>
                                        	<span>${pluginws?.id}</span>
                                    	</div>
                                    </g:if>
                                    <g:else>
                                        <g:set var="this_plugin_id" value="0"/>
                                    </g:else>
                                    <div class="row">
                                        <p><g:message code="plugins.create.category"/></p>
                                        <span>${description}</span>
                                    </div>
                                    <div class="row">
                                        <p><g:message code="plugins.plugin.type"/></p>
                                        <span>
                                        ${type.className}
                                         <g:hiddenField name="typeId" value="${type.id}" />
                                        </span>
                                    </div>
                                    <div class="row">
                                        <label> <g:message code="plugins.plugin.order"/> </label>
                                        <div class="inp-bg inp4">
                                         <g:if test="${pluginws?.id > 0}">
                                           <g:textField class="field" name="processingOrder" size="2" 
                                                        value="${pluginws?.processingOrder}" />
                                          </g:if>
                                          <g:else>
                                          	<g:textField class="field" name="processingOrder" size="2" 
                                                        value="${nextOrderId}" />
                                          </g:else>
                                        </div>
                                    </div>
                                </div>
                            </div>
 
                            <!-- box cards -->
                            <div class="box-cards box-cards-open">
                                <div class="box-cards-title">
                                    <span style="float:left;padding-left:20px;">
                                         <g:message code="plugins.create.parameters"/>
                                    </span>
                                </div>
                                <g:render template="formParameters" model="[parametersDesc:parametersDesc]"/>
                            </div>
                            <!-- box text -->
                            <div class="box-text">
                                <g:textArea name="notes" rows="7" cols="63" value="${pluginws?.notes}" />
                            </div>
                            <div class="buttons">
                                <ul>
                                    <li><a class="submit save" onclick="$('#plugin-form').submit();" href="#">
                                        <span><g:message code="plugins.create.save"/></span>
                                    </a></li>
                                    <li><a class="submit cancel" href="${createLink(action:'cancel',params:[plugin_id:this_plugin_id])}"><span>Cancel</span></a></li>
                                </ul>
                            </div>
                        </fieldset>
                    </g:form>
                </div>
            </div>
            
</body>
</html>