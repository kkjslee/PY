<%@ page contentType="text/html;charset=UTF-8" %>
<div class="table-box">
    <table id="constants" cellspacing="0" cellpadding="0">
        <thead>
            <tr>
                <th class="small"><g:message code="dbconstant.th.name"/></th>
                <th class="large2 double"><g:message code="dbconstant.th.content"/></th>
            </tr>
        </thead>

        <tbody>
            <g:each var="constant" in="${constants}">
                <tr id="constant-${constant.id}" class="${selected?.id == constant.id ? 'active' : ''}">
                    <td  class="small" title="${constant.name}">
                        <g:remoteLink class="cell" action="show" id="${constant.id}" before="register(this);" onSuccess="render(data, next);">
                            <strong>${constant.name}</strong>
                        </g:remoteLink>
                    </td>

                    <td title="${constant.content}">
                        <g:remoteLink class="cell  double" action="show" id="${constant.id}" before="register(this);" onSuccess="render(data, next);">
                           <strong>${constant.content}</strong>
                        </g:remoteLink>
                    </td>
                </tr>
            </g:each>
        </tbody>
    </table>
</div>

<div class="btn-box">
    <g:remoteLink action='add' class="submit add" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="button.create"/></span>
    </g:remoteLink>
</div>