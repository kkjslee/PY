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

<%@page import="com.infosense.ibilling.server.util.db.LanguageDTO"%>
<g:applyLayout name="form/input">
	<content tag="label"><g:message code="${descriptionEntityName}.label.description"/></content>
	<content tag="label.for">${descriptionEntityName}.description</content>
	
	<g:textField class="field" style="width: 192px" name="${descriptionEntityName}.descriptions.1" value="${descriptionEntity?.getDescription(1)}"/>
	<a onclick="showDescriptionsDialog()" style="width:16px; height:16px; float: right;
                    background:url(../images/add.png) no-repeat;
                    background-position:center;"></a>
</g:applyLayout>

<%
	languanges = languanges?:LanguageDTO.list()
	
	languanges.each{
		if(it.id==1){
			languanges.remove(it)
		}
	}
%>

<div id="description-dialog-${descriptionEntityName}-container" ></div>
<div id="description-dialog-${descriptionEntityName}" class="bg-lightbox" title="<g:message code="popup.description.title"/>" style="display:none;">
	
	 <table>
		 <tbody><tr><td style="padding: 0 20px 0 0; ">
		 	<g:select id="${descriptionEntityName}.language" name="${descriptionEntityName}.language" from="${languanges}"
	                                              optionKey="id" optionValue="description" />
	     </td><td>
		     <div class="buttons" style="border: none;">
		         <ul>
		             <li>
		                 <a onclick="addDescriptionItem()" class="submit add"><span><g:message code="button.add"/></span></a>
		             </li>
		         </ul>
		     </div>
	     </td></tr></tbody>
	</table>
	
	<div style="height: 300px; scroll: auto;">
	    <table id="${descriptionEntityName}-description-table" class="innerTable" style="width: 100%;">
	    	<thead class="innerHeader"><tr>
	            <td valign="middle" align="center" width="150px">
	            	<g:message code="popup.language.label" />
	            </td>
	            <td valign="middle" align="center" width="212px">
	            	<g:message code="popup.description.label" />
	            </td>
	            <td width="30">
	            </td>
	        </tr></thead>
	        <tbody>
	        </tbody>
	    </table>
    </div>
</div>

<script type="text/javascript">

	function showDescriptionsDialog(){
		var el = jQuery('#description-dialog-${descriptionEntityName}');
		var pEl = jQuery('#description-dialog-${descriptionEntityName}-container');
		el.dialog({
			width : 502,
			modal: true,
			buttons: [
			    {
			        text: "Ok",
			        click: function() {
			        	$(this).dialog("close");
			        }
			    }
			]
		});
		el.parent().appendTo(pEl);
	}
	
	function addDescriptionItem(){
		var languageEl = document.getElementById('${descriptionEntityName}.language');
		var tableName = '${descriptionEntity?.getTable()}';
		var foreignId = '${descriptionEntity?.id}';
			
		if(languageEl && tableName!='null' && foreignId!='null'){
			var language = languageEl.value;
			var input_id = "${descriptionEntityName}.descriptions." + language
			if(!input_id || document.getElementById(input_id)){
				return;
			}
		
			$.ajax({
			
				type : "POST",
				url : "/ibilling/description/getContent",
				data: { tableName : tableName, foreignId : foreignId, 
						psudoColumn : 'description', language : language},
				success : function(data){
					var table_id = '${descriptionEntityName}-description-table';
					var table = document.getElementById(table_id);
					var row = table.insertRow(-1);
					var cell1 = row.insertCell(-1);
					var cell2 = row.insertCell(-1);
					var cell3 = row.insertCell(-1);
					
					cell1.innerHTML = data.language
					var input_id = "${descriptionEntityName}.descriptions." + data.languageId
					cell2.innerHTML = "<input type='text' style='width: 100%' name='"
									+ input_id
									+ "' value='"
									+ (data.content? data.content : "")
									+ "' id='"
									+ input_id
									+ "'>"
					cell3.innerHTML = "<a onclick='removeThisRow(this)' style='margin: 0 0 0 10px;'>X</a>"
				},
				fail : function(data){
					alert('<g:message code="description.fetch.error" />')
				}
						
			})
		}
	}
	
	function removeThisRow(el){
		while(el){
			if(el.tagName.toLowerCase()=="tr"){
				el.parentNode.removeChild(el);
				break;
			}
			el = el.parentNode
		}
	}

</script>