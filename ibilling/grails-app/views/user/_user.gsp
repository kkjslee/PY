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

<%@ page contentType="text/html;charset=UTF-8" %>

<div class="form-columns">
    <div class="column">
        <g:applyLayout name="form/text">
            <content tag="label"><g:message code="prompt.customer.number"/></content>
            ${user.userId}
            <g:hiddenField name="id" value="${user.userId}"/>
        </g:applyLayout>

        <g:applyLayout name="form/text">
            <content tag="label"><g:message code="prompt.login.name"/></content>
            ${user?.userName}
        </g:applyLayout>

        <g:applyLayout name="form/text">
            <content tag="label"><g:message code="prompt.user.role"/></content>
            <g:if test="${userRoles}">
            	<g:each var="role" in="${userRoles}">
                	${role.getTitle(session['language_id'])}&nbsp;;&nbsp;
                </g:each>
            </g:if>
            <g:else>
                 -
            </g:else>
        </g:applyLayout>
    </div>

    <div class="column">

        <g:applyLayout name="form/text">
            <content tag="label"><g:message code="prompt.organization.name"/></content>
            ${contact?.organizationName}
        </g:applyLayout>

        <g:applyLayout name="form/text">
            <content tag="label"><g:message code="prompt.user.name"/></content>
            ${contact?.firstName} ${contact?.lastName}
        </g:applyLayout>
    </div>
</div>