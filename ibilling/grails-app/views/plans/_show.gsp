
<div class="column-hold">

    <!-- the plug-in details -->
    <div class="heading">
        <strong>${plugin.type.getDescription(session['language_id'], "title")}</strong>
    </div>
    <div class="box">
        <strong><g:message code="plugins.plugin.description"/></strong>
        <p>${plugin.type.getDescription(session['language_id'])}</p>
        <br/>
        
        <table class="dataTable">
           <tr>
            <td><g:message code="plugins.plugin.id-long"/></td>
            <td class="value">${plugin.getId()}</td>
           </tr>
           <tr>
            <td><g:message code="plugins.plugin.notes"/></td>
            <td class="value">
                <g:if test="${plugin.getNotes() != null}">
                    ${plugin.getNotes()}
                </g:if>
                <g:else>
                    <g:message code="plugins.plugin.noNotes"/>
                </g:else>
            </td>
           </tr>
           <tr>
            <td><g:message code="plugins.plugin.order"/></td>
            <td class="value">${plugin.getProcessingOrder()}</td>
           </tr>
           <g:if test="${plugin.parameters.size() == 0}">
           <tr>
            <td><g:message code="plugins.plugin.noParamaters"/></td>
            <td class="value"><g:message code="plugins.plugin.noParamatersTxt"/></td>
           </tr>
           </g:if>
        </table>
        
        <g:if test="${plugin.parameters}">
        <table class="innerTable">
             <thead class="innerHeader">
             <tr>
                <th><g:message code="plugins.plugin.parameter"/></th>
                <th><g:message code="plugins.plugin.value"/></th>
             </tr>
             </thead>
             <tbody>
             <g:each in="${plugin.parameters}">
             <tr>
                <td class="innerContent">${it.name}</td>
                <td class="innerContent">${it.value}</td>
             </tr>         
             </g:each>
             </tbody>
        </table>
        </g:if>
    </div>


    <div class="btn-box">
    </div>
</div>
