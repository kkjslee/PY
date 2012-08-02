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
    <meta name="layout" content="panels" />
</head>
<body>
    <g:if test="${!selectedPlanes}">
        <!-- show all planes and products -->
        <content tag="column1">
            <g:render template="planesGroup" model="[planesGroup: planesGroup]"/>
        </content>

        <content tag="column2">
            <g:render template="planes" model="[planes: planes]"/>
        </content>
    </g:if>
    <g:else>
        <!-- show product list and selected product -->
        <content tag="column1">
            <g:render template="planes" model="[planes:planes]"/>
        </content>

        <content tag="column2">
            <g:render template="show" model="[selectedPlan: selectedPlan]"/>
        </content>
    </g:else>
</body>
</html>