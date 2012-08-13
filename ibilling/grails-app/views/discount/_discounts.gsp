<div class="plans table-box">
<table cellpadding="0" cellspacing="0">
    <thead>
        <tr>
    	   	 <th class="small"><g:message code="discount.th.name"/></th>
    		  <th class="medium"><g:message code="discount.th.content"/></th>
    	  </th>
        </tr>
    </thead>

    <tbody>
		<g:each in="${items}" status="idx" var="item">
		   <tr class="discountrow">
	                <td class="innerContent small" style="text-align:left">
	               	 	 <g:remoteLink class="cell" action="show" id="${item.id}"  params="${[taskid:item.taskId,typeid:typeid]}" before="register(this);" onSuccess="render(data, next);">${item.name}</g:remoteLink>
	                </td>
	                   <td class="innerContent medium" style="text-align:left">
	               	 	 <g:remoteLink class="cell" action="show" id="${item.id}"  params="${[taskid:item.taskId,typeid:typeid]}" before="register(this);" onSuccess="render(data, next);">${item.strValue}</g:remoteLink>
	                </td>
		   </tr>
		   </g:each>
    </tbody>
</table>
</div>
<div class="btn-box">
   <g:remoteLink action='add' class="submit add"  params="${[typeid:typeid]}" before="register(this);" onSuccess="render(data, next);">
        <span><g:message code="button.create"/></span>
    </g:remoteLink>
</div>
<script>
$(".discountrow").hover(function(){
	$(this).find(".hidden").toggle();
});
</script>
