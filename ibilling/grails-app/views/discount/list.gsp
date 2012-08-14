<html>
<head>
    <meta name="layout" content="panels" />
</head>
<body>	
	<g:if test ="${!selected}">
        <content tag="column1">
            <g:render template="discountsType" model="[typeList:typeList]"/>
        </content>

        <content tag="column2">
        	 <g:if test="${items}">
            <g:render template="discounts"  model="[items:items,typeid:typeid]"/>
            </g:if>
        </content>
        </g:if>
        <g:else>
        <content tag="column1">
            <g:render template="discounts" model="[items:items,typeid:typeid]"/>
        </content>

        <content tag="column2">
            <g:render template="show" model="[selected:selected]"/>
        </content>
        </g:else>
</body>
</html>