<div id="plugin-parameters" class="box-card-hold">
    <div class="form-columns">
		<div class="one_column">

<g:each in="${parametersDesc}">
			<g:if test="${it.name=='url' && aname=='showForm'}">
				<div class="row">
	            		<label>${it.name}</label>
	            	<div class="inp-bg inp4">
					  <input type="text" class="field" style="width:172px" readonly name="plg-parm-${it.name}" id="plg-parm-${it.name}" value="${urlvalue}" />
					</div>
				</div>
			</g:if>
			 <g:else>
			    <div class="row">
			    	 <g:set var="nfilter" value="${nameFilter(name:it.name)}" scope="page" />
			    	 <g:set var="ndisabled" value="disabled='true'" scope="page" />
					<label>
						${it.name}<g:if test='${nfilter!=ndisabled}'><font color="red">*</font></g:if>
					</label>
					
					<div class="inp-bg inp4">
					  <input type="text" class="field" ${nfilter} name="plg-parm-${it.name}" id="plg-parm-${it.name}" value="${pluginws?.getParameters()?.get(it.name)}" />
					</div>
                </div>
            	
            </g:else>
            </g:each>
        </div>
    </div>
</div>