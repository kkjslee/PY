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
            <g:message code="permissions.title"/>
        </strong>
    </div>

    <div class="form-hold">
        <g:form name="user-permission-edit-form" action="savePermissions">
            <fieldset>

                <!-- user information -->
            	<g:render template="user" />

                <!-- user permissions -->
                <g:each var="permissionType" status="n" in="${permissionTypes}">
                    <div class="form-columns">
                        <h3>${permissionType.description}</h3>

                        <!-- column 1 -->
                        <div class="column">
                            <g:each var="permission" status="i" in="${permissionType.permissions}">
                                <g:set var="userPermission" value="${permissions.find{ it.id == permission.id }}"/>
                                <g:set var="rolePermission" value="${rolePermissions?.find{ it.id == permission.id }}"/>

                                <g:if test="${i % 2 == 0}">

                                    %{
                                        permission.initializeAuthority()
                                    }%

                                    <g:applyLayout name="form/checkbox">
                                        <content tag="group.label">
                                            ${permission.id}
                                        </content>
                                        <content tag="label">
                                            <g:if test="${rolePermission}">
                                                <strong>
                                                    ${permission.getDescription(session['language_id']) ?: permission.authority}
                                                </strong>
                                            </g:if>
                                            <g:else>
                                                ${permission.getDescription(session['language_id']) ?: permission.authority}
                                            </g:else>
                                        </content>
                                        <content tag="label.for">permission.${permission.id}</content>

                                        <g:checkBox name="permission.${permission.id}" class="check cb" checked="${userPermission}"/>
                                    </g:applyLayout>
                                </g:if>
                            </g:each>
                        </div>

                        <!-- column 2 -->
                        <div class="column">
                            <g:each var="permission" status="i" in="${permissionType.permissions}">
                                <g:set var="userPermission" value="${permissions.find{ it.id == permission.id }}"/>
                                <g:set var="rolePermission" value="${rolePermissions?.find{ it.id == permission.id }}"/>

                                <g:if test="${i % 2 == 1}">

                                    %{
                                        permission.initializeAuthority()
                                    }%

                                    <g:applyLayout name="form/checkbox">
                                        <content tag="group.label">
                                            ${permission.id}
                                        </content>
                                        <content tag="label">
                                            <g:if test="${rolePermission}">
                                                <strong>
                                                    ${permission.getDescription(session['language_id']) ?: permission.authority}
                                                </strong>
                                            </g:if>
                                            <g:else>
                                                ${permission.getDescription(session['language_id']) ?: permission.authority}
                                            </g:else>
                                        </content>
                                        <content tag="label.for">permission.${permission.id}</content>

                                        <g:checkBox name="permission.${permission.id}" class="check cb" checked="${userPermission}"/>
                                    </g:applyLayout>
                                </g:if>
                            </g:each>
                        </div>
                    </div>

                    <g:if test="${n < permissionTypes.size()}">
                        <!-- spacer between permission types -->
                        <div>
                            &nbsp;<br/>
                        </div>
                    </g:if>
                </g:each>

                <!-- spacer -->
                <div>
                    &nbsp;<br/>
                </div>

                <div class="buttons">
                    <ul>
                        <li>
                            <a onclick="$('#user-permission-edit-form').submit()" class="submit save"><span><g:message code="button.save"/></span></a>
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