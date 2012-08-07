<div class="plans table-box">
<table cellpadding="0" cellspacing="0">
    <thead>
        <tr>
         <g:if test="${paraNames}">
       	  	<g:each in="${paraNames}">
    	   	 <th class="medium">${it}</th>
    	    </g:each>
    	  </g:if>
    	  <th class="medium">
    	  </th>
        </tr>
    </thead>

    <tbody>
		<g:each in="${planes}" status="idx" var="pmap">
		   <tr class="planrow">
		   	  <g:hiddenField name="id" value="${pmap.get('tId')}"/>
		   	  <g:each in="${paraNames}" var="name">
	                <td class="innerContent double" style="text-align:left" >
	                 <g:set var="itemId" value="${planDesc(mapKey:pmap.get(name),key:'id')}" scope="page" />
		 		     <g:set var="itemDesc" value="${planDesc(mapKey:pmap.get(name),key:'desc')}" scope="page" />
		 		      <strong><a title="${itemDesc}" href="${createLink(controller:'plans', action:'edit', id:pmap.get('tId')) }" >${itemDesc}</a></strong>
	               	  <em><a title="${itemId}" href="${createLink(controller:'plans', action:'edit', id:pmap.get('tId')) }" >${itemId}</a></em> 
	                </td>
               </g:each>
               <td class="innerContent ope" >
             	  <div class="hidden">
					        <a href="${createLink(controller:'plans', action:'edit', id:pmap.get('tId')) }" class="submit2 editp" >
					            <span><g:message code="plans.plan.edit"/></span>
					        </a>
					        <a onclick="$('#confirm-dialog-delete-${pmap.get('tId')}').dialog('open');"  class="submit2">
					            <span><g:message code="plans.plan.delete"/></span>
					        </a>
					        <g:render template="/confirm" 
            			  model="['message':'plans.delete.confirm','controller':'plans','action':'delete','id':pmap.get('tId')]"/>
            		</div>
               </td>
		   </tr>
		   </g:each>
    </tbody>
</table>
</div>
<script>
$(".planrow").hover(function(){
	$(this).find(".hidden").toggle();
});
</script>
