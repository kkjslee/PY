%{--
  JBILLING CONFIDENTIAL
  _____________________

  [2003] - [2012] Enterprise jBilling Software Ltd.
  All Rights Reserved.

  NOTICE:  All information contained herein is, and remains
  the property of Enterprise jBilling Software.
  The intellectual and technical concepts contained
  herein are proprietary to Enterprise jBilling Software
  and are protected by trade secret or copyright law.
  Dissemination of this information or reproduction of this material
  is strictly forbidden.
  --}%

<html>
<head>
    <meta name="layout" content="main"/>
</head>
<body>
<div class="form-edit">

    <div class="heading">
        <strong>
            <g:message code="role.title"/>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="user-role-edit-form" action="saveRoles">
            <fieldset>
            	
            	<!-- user information -->
            	<g:render template="user" />
            	
            	<div class="form-columns">
            		<h3><g:message code="userRole.title"/></h3>
            		
            		<!-- column 1 -->
                    <div class="column">
                    	<g:each var="role" status="i" in="${roles}">
                    		<g:set var="userRole" value="${userRoles.find{ it.id == role.id }}"/>
                    			
                    		<g:if test="${i % 2 == 0}">
                    			%{
                                    role.initializeAuthority()
                                }%
                    		
                    			<g:applyLayout name="form/checkbox">
	                                <content tag="group.label">${role.id}:</content>
	                                <content tag="label">${role.getDescription(session['language_id']) ?: role.authority}</content>
	                                <content tag="label.for">role.${role.id}</content>
	
	                                <g:checkBox name="role.${role.id}" class="check cb" checked="${userRole}"/>
	                            </g:applyLayout>
                    		</g:if>
                    	</g:each>
                    </div>
                    
                    <!-- column 2 -->
                    <div class="column">
                    	<g:each var="role" status="i" in="${roles}">
                    		<g:set var="userRole" value="${userRoles.find{ it.id == role.id }}"/>
                    			
                    		<g:if test="${i % 2 == 1}">
                    			%{
                                    role.initializeAuthority()
                                }%
                    		
	                            <g:applyLayout name="form/checkbox">
	                                <content tag="group.label">${role.id}:</content>
	                                <content tag="label">${role.getDescription(session['language_id']) ?: role.authority}</content>
	                                <content tag="label.for">role.${role.id}</content>
	
	                                <g:checkBox name="role.${role.id}" class="check cb" checked="${userRole}"/>
	                            </g:applyLayout>
                    		</g:if>
                    	</g:each>
                    </div>
            	</div>

                <!-- spacer -->
                <div>
                    &nbsp;<br/>
                </div>

                <div class="buttons">
                    <ul>
                        <li>
                            <a onclick="$('#user-role-edit-form').submit()" class="submit save"><span><g:message code="button.save"/></span></a>
                        </li>
                        <li>
                            <g:link action="list" class="submit cancel"><span><g:message code="button.cancel"/></span></g:link>
                        </li>
                    </ul>
                </div>

            </fieldset>
        </g:form>
    </div>
</div>
</body>
</html>