<html>
<head>
    <meta name="layout" content="panels" />
</head>
<body>	
        <content tag="column1">
            <g:render template="discountsType" model="[typeList:typeList]"/>
        </content>

        <content tag="column2">
        	 <g:if test="${task}">
            <g:render template="discounts" model="[task:task]"/>
            </g:if>
        </content>
</body>
</html>