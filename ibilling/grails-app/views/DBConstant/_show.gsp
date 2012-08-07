
<%@ page contentType="text/html;charset=UTF-8" %>

<div class="column-hold">
    <div class="heading">
        <strong>
            <em>${selected?.id}</em>
        </strong>
    </div>
	<g:form id="save-constant-form" name="notes-form" url="[action: 'save']">
	    <div class="box">
	        <fieldset>
	            <div class="form-columns">
	           	    <g:hiddenField name="id" value="${selected?.id}"/>
	            	 <g:applyLayout name="form/input">
	                        <content tag="label"><g:message code="dbconstant.th.name"/></content>
	                        <g:textField class="field"  name="name" value="${selected?.name}"/>
	                 </g:applyLayout>
	                  <g:applyLayout name="form/input">
	                        <content tag="label"><g:message code="dbconstant.th.content"/></content>
	                        <g:textField class="field"  name="content" value="${selected?.content}"/>
	                 </g:applyLayout>
	            </div>
	        </fieldset>
	    </div>
    </g:form>

   <div class="btn-box buttons">
        <ul>
            <li><a class="submit save" onclick="$('#save-constant-form').submit();"><span><g:message code="button.save"/></span></a></li>
            <li><a class="submit cancel" onclick="closePanel(this);"><span><g:message code="button.cancel"/></span></a></li>
             <g:if test="${selected}">
             <li><a class="submit delete" onclick="$('#confirm-dialog-delete-${selected.id}').dialog('open');"><span><g:message code="button.remove"/></span></a></li>
             <g:render template="/confirm" 
            			  model="['message':'dbconstant.delete.confirm','controller':'DBConstant','action':'delete','id':selected.id]"/>
             </g:if>
        </ul>
    </div>
</div>