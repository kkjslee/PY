<div class="plansGroup table-box">
<table cellpadding="0" cellspacing="0">
    <thead>
        <tr>
       	  <th class="medium"><g:message code="plugins.plugin.groupname"/></th>
    	   <th class="medium"><g:message code="plugins.plugin.groupdesc"/></th>
    	   <th class="tiny"><g:message code="plugins.plugin.plancount"/></th>
        </tr>
    </thead>

    <tbody>
		<g:each in="${planesGroup}" status="idx" var="dto">
		   <tr>
		     <td>
		     <g:set var="tId" value="${planDesc(mapKey:dto.key,key:'id')}" scope="page" />
		     <g:set var="tDesc" value="${planDesc(mapKey:dto.key,key:'desc')}" scope="page" />
		     <g:remoteLink action="plans" before="register(this);" 
                           onSuccess="render(data, next);" params="${[pvalue:tId]}">
		         <em>
		           	${tId}
		         </em>
			 </g:remoteLink>
             </td>
               <td>
		     <g:remoteLink action="plans"  before="register(this);" 
                           onSuccess="render(data, next);" params="${[pvalue:tId]}">
		         <em>
		           	&nbsp;${tDesc}
		         </em>
			 </g:remoteLink>
             </td>
		     <td>
		     <g:remoteLink action="plans" before="register(this);"  onSuccess="render(data, next);" params="${[pvalue:tId]}">
		     	${dto.value}
			 </g:remoteLink>
             </td>
		   </tr>
        </g:each>
    </tbody>
</table>
</div>
<div class="btn-box">
    <a href="${createLink(action: 'showForm')}" class="submit add"><span><g:message code="button.create"/></span></a>
</div>
