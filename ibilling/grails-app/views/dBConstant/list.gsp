<html>
<head>
    <meta name="layout" content="configuration" />
</head>
<body>
    <!-- selected configuration menu item -->
    <content tag="menu.item">dbconstants</content>

    <content tag="column1">
        <g:render template="dbConstants" />
    </content>

    <content tag="column2">
        <g:if test="${selected}">
            <g:render template="show" model="['selected': selected]"/>
        </g:if>
    </content>
</body>
</html>