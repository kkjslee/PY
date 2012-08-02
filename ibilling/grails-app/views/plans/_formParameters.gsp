<div id="plugin-parameters" class="box-card-hold">
    <div class="form-columns">
		<div class="one_column">
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

<g:each in="${parametersDesc}">
			    <div class="row">
			     <g:set var="nfilter" value="${nameFilter(name:it.name)}" scope="page" />
			     <g:set var="ndisabled" value="disabled='true'" scope="page" />
				<label>
					${it.name}<g:if test='${nfilter!=ndisabled}'><font color="red">*</font></g:if>
				</label>
				
				<div class="inp-bg inp4">
				  <input type="text" class="field"  ${nfilter} name="plg-parm-${it.name}" id="plg-parm-${it.name}" value="${pluginws?.getParameters()?.get(it.name)}" />
				</div>
                </div>
            </g:each>
        </div>
    </div>
</div>