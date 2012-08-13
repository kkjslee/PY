<div class="plansGroup table-box">
<table cellpadding="0" cellspacing="0">
    <thead>
        <tr>
       	  <th class="medium"><g:message code="discount.thead.typeid"/></th>
    	   <th class="large"><g:message code="discount.thead.classname"/></th>
        </tr>
    </thead>

    <tbody>
		<g:each in="${typeList}" status="idx" var="dto">
		   <tr>
		     <td>
		     <g:remoteLink action="items"  before="register(this);" 
                           onSuccess="render(data, next);" params="${[typeid:dto.id]}">
		         <em>
		           	${dto.id}
		         </em>
			 </g:remoteLink>
             </td>
               <td>
		     <g:remoteLink action="items"  before="register(this);" 
                           onSuccess="render(data, next);" params="${[typeid:dto.id]}">
		         <em>
		           	&nbsp;${dto.className}
		         </em>
			 </g:remoteLink>
             </td>
		   </tr>
        </g:each>
    </tbody>
</table>
</div>

