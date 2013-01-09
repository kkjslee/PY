// JavaScript Document
// Author: Bill, 2011

/**
 * Template - Box Info
 */
var Template_Box='																												\
<span class="boxInfo">																											\
	<span class="boxTitle"></span>																								\
																																\
	<span class="boxLine">																										\
    	<span class="boxLeft">'+Locale["template.manage.template.label.temp.zone"]+'</span>											\
    	<span class="boxRight">${$("#zone").val()}</span>																		\
    	<input zmlm:item="zone" type="hidden" value="${$("#zone").val()}" />													\
    </span>																														\
	<span class="boxLine">																										\
    	<span class="boxLeft">'+Locale["template.manage.template.label.temp.id"]+'</span>																						\
    	<span zmlm:item="id" class="boxRight" title="${id}">${id}</span>														\
    </span>																														\
	<span class="boxLine">																										\
    	<span class="boxLeft">'+Locale["template.manage.template.label.temp.name"]+'</span>																						\
    	<span zmlm:item="name" class="boxRight" title="${name}"><b><u>${name}</u></b></span>									\
    </span>																														\
	<span class="boxLine">																										\
    	<span class="boxLeft">'+Locale["template.manage.template.label.temp.desc"]+'</span>																					\
    	<span zmlm:item="description" class="boxRight" title="${description}">${description}</span>								\
    </span>																														\
	<span class="boxLine">																										\
    	<span class="boxLeft">'+Locale["template.manage.template.label.temp.notes"]+'</span>																					\
    	<span zmlm:item="notes" class="boxRight" title="${notes}">${notes}</span>												\
    </span>																														\
    																															\
    																															\
	<span class="boxLine">																										\
    	<span class="boxLeft">'+Locale["template.manage.template.label.temp.inst"]+'</span>																					\
    	<span zmlm:item="softwares" class="boxRight boxRightMulti">																			\
		    {{if !softwares.length}}<em><b>'+Locale["template.message.empty"]+'</b></em>{{/if}}															\
			{{each softwares}}																									\
        	<span zmlm:item="software" class="boxRightBox">																		\
				<span class="boxLine">																							\
                    <span class="boxLeft">'+Locale["template.manage.template.label.soft.name"]+'</span>																			\
                    <span zmlm:item="softwarename" class="boxRight" title="${$value.softwarename}">\
                    	<b><i><u>${$value.softwarename}</u></i></b>\
                    </span>\
                </span>																											\
				<span class="boxLine" style="display:none;">																							\
                    <span class="boxLeft">'+Locale["template.manage.template.label.soft.id"]+'</span>																			\
                    <span zmlm:item="softwareid" class="boxRight" title="${$value.softwareid}">${$value.softwareid}</span>		\
                </span>																											\
                																												\
                <span class="splitter"></span>																					\
                																												\
			    {{if !resources.length}}<em><b>'+Locale["template.message.empty"]+'</b></em>																\
				{{else}}																										\
				<span class="boxResourceTitle" onclick="$(this).next().slideToggle(\'fast\')" 									\
						onmouseover="$(this).addClass(\'highlight\')" onmouseout="$(this).removeClass(\'highlight\')">			\
					<span class="ui-icon ui-icon-image smallIcon"></span>														\
					Show/Hide Resources																							\
				</span>																													\
				<span class="boxResourceBody" zmlm:item="resources">																	\
					{{each resources}}																									\
						<span class="boxLine">																							\
							<span class="boxLeft">'+Locale["template.manage.template.label.cpu"]+'</span>																			\
							<span class="boxRight">${$value.cpu}</span>																	\
						</span>  																										\
						<span class="boxLine">																							\
							<span class="boxLeft">'+Locale["template.manage.template.label.cpu.max"]+'</span>																		\
							<span class="boxRight">${$value.maxcpu}</span>																\
						</span>      																									\
						<span class="boxLine">																							\
							<span class="boxLeft">'+Locale["template.manage.template.label.mem"]+'</span>																			\
							<span class="boxRight">${$value.mem} ( ${formatSize($value.mem*1024)} )</span>								\
						</span>          																								\
						<span class="boxLine">																							\
							<span class="boxLeft">'+Locale["template.manage.template.label.mem.max"]+'</span>																		\
							<span class="boxRight">${$value.maxmem} ( ${formatSize($value.maxmem*1024)} )</span>						\
						</span>																											\
																																		\
						<span class="splitter"></span>																					\
					{{/each}}																											\
				</span>																													\
				{{/if}}																											\
                																												\
            </span>																												\
			{{/each}}																											\
        </span>																													\
    </span>																														\
    																															\
    <span class="splitter"></span>																								\
     																															\
    <span class="boxLine">																										\
        <span class="boxCtrl">																									\
			<button name="deleteTemplate" onclick="$(this).parents(\'.boxInfo\').trigger(\'delete\');">'+Locale["template.manage.button.remove"]+'</button>				\
			<button name="updateTemplate" onclick="$(this).parents(\'.boxInfo\').trigger(\'update\');">'+Locale["template.manage.button.update"]+'</button>																			\
		</span>																													\
    </span>																														\
                																												\
</span>';


/**
 * Template - Control Bar
 */
var Template_CtrlBar='															\
<div class="ctrlBar">															\
    <span class="ctrlBlock">													\
        <span class="ctrlItem ctrlSplitter">									\
            <span><button id="newTemplateBtn" onclick="showNewTemplateDialog()">'+Locale["template.manage.button.new"]+'</button></span>			\
        </span>																	\
        																		\
        <span class="ctrlItem">													\
            <span>'+Locale["template.option.label.zone"]+'</span>													\
            <span>																\
                <select id="zone">												\
                    <optgroup label="'+Locale["template.option.choose.zone"]+'">								\
                    </optgroup>													\
                </select>														\
            </span>																\
        </span>																	\
        																		\
        <span class="ctrlItem ctrlSplitter">													\
            <span><button id="refreshBtn" onclick="reload()">'+Locale["template.button.refreshBtn"]+'</button></span>				\
        </span>																	\
        																		\
        <span class="ctrlItem">													\
            <span><button id="manageBtn" onclick="loadManPage();reload();">'+Locale["template.button.manageBtn"]+'</button></span>				\
        </span>																	\
        																		\
        <span class="ctrlItem">													\
            <span><button id="examineBtn" onclick="loadExamPage()">'+Locale["template.button.examineBtn"]+'</button></span>				\
        </span>																	\
        																		\
        <span class="ctrlItem">													\
            <span id="loadingIcon" class="hidden"><img src="css/image/progress.gif" /></span>	\
        </span>																	\
    </span>																		\
</div>';


/**
 * Template - Zone Option Group
 */
var Template_ZoneGroup='\
<select id="${id}">																\
	<optgroup label="'+Locale["template.option.choose.zone"]+'">												\
		{{each options}}														\
			<option value="${$value.value}">${$value.lable}</option>			\
		{{/each}}																\
	</optgroup>																	\
</select>																		\
';


var Template_TheAppTempPanel='\
<div id="${id}">\
	<input zmlm:item="appTempId" type="hidden"/>\
	<input zmlm:item="appTempZone" type="hidden"/>\
	<span class="dialogLine"><span class="dialogLineLeft">'+Locale["template.manage.template.label.temp.name"]+'</span><span class="dialogLineRight"><input zmlm:item="appTempName" type="text"/></span></span>\
	<span class="dialogLine"><span class="dialogLineLeft">'+Locale["template.manage.template.label.temp.desc"]+'</span><span class="dialogLineRight"><input zmlm:item="appTempDesc" type="text"/></span></span>\
	<span class="dialogLine"><span class="dialogLineLeft">'+Locale["template.manage.template.label.temp.notes"]+'</span><span class="dialogLineRight"><input zmlm:item="appTempNotes" type="text"/></span></span>\
	<span class="splitter\"></span>\
	<span class="listPanel">\
		<span class="listItem">Sample</span>\
	</span>\
</div>';

/**
 * Template - Software Item
 */
var Template_SoftwareItem='\
<span class="listItem">											\
	<span class="itemMain"><em>${name}</em></span>								\
	<span class="itemExtra">${type}</span>										\
	<span class="itemExtra">${desc}</span>										\
	<span class="itemExtra">${intro}</span>										\
	<span class="itemExtra NOT-IMPLEMENTED"><a target="_blank" href="#" class="detail">详细</a></span>			\
</span>';


var Template_MessageBox='\
<div title="'+Locale["template.dialog.title.tips"]+'">\
	<p class="message">{{html message}}</p>\
</div>';


var Template_ExamineTabs='\
<div id="${id}">													\
	<ul>															\
		<li><a href="#tab-unapproved"><span class="ui-icon ui-icon-signal-diag smallIcon"></span>'+Locale["template.verify.tab.unapproved"]+'</a></li>\
		<li><a href="#tab-approved"><span class="ui-icon ui-icon-circle-check smallIcon"></span>'+Locale["template.verify.tab.proved"]+'</a></li>\
		<li><a href="#tab-rejected"><span class="ui-icon ui-icon-circle-close smallIcon"></span>'+Locale["template.verify.tab.rejected"]+'</a></li>\
	</ul>															\
																	\
	<div id="tab-unapproved">										\
		<span style="display:block">\
			<span class="examTempName appReqListHeader">'+Locale["template.manage.template.column.examTempName"]+'</span>\
			<span class="examTempUser appReqListHeader">'+Locale["template.manage.template.column.examTempUser"]+'</span>\
			<span class="examReqName appReqListHeader">'+Locale["template.manage.template.column.examReqName"]+'</span>\
			<span class="examTempRes appReqListHeader">'+Locale["template.manage.template.column.examTempRes"]+'</span>\
			<span class="examStatus appReqListHeader">'+Locale["template.manage.template.column.examStatus"]+'</span>\
			<span class="examSubTime appReqListHeader">'+Locale["template.manage.template.column.examSubTime"]+'</span>\
			<span class="examAdminActTime appReqListHeader">'+Locale["template.manage.template.column.examAdminActTime"]+'</span>\
		</span>														\
		<span class="splitter"></span>								\
		<span zmlm:item="appReqList" class="appReqList"></span>		\
																	\
		<span class="splitter"></span>								\
		<span class="appReqBtnPanel">\
			<button zmlm:item="buttonApproved">'+Locale["template.verify.button.buttonApproved"]+'</button>\
			<button zmlm:item="buttonRejected">'+Locale["template.verify.button.buttonRejected"]+'</button>\
		</span>														\
	</div>															\
	<div id="tab-approved">											\
		<span style="display:block">\
			<span class="examTempName appReqListHeader">'+Locale["template.manage.template.column.examTempName"]+'</span>\
			<span class="examTempUser appReqListHeader">'+Locale["template.manage.template.column.examTempUser"]+'</span>\
			<span class="examReqName appReqListHeader">'+Locale["template.manage.template.column.examReqName"]+'</span>\
			<span class="examTempRes appReqListHeader">'+Locale["template.manage.template.column.examTempRes"]+'</span>\
			<span class="examStatus appReqListHeader">'+Locale["template.manage.template.column.examStatus"]+'</span>\
			<span class="examSubTime appReqListHeader">'+Locale["template.manage.template.column.examSubTime"]+'</span>\
			<span class="examAdminActTime appReqListHeader">'+Locale["template.manage.template.column.examAdminActTime"]+'</span>\
		</span>														\
		<span class="splitter"></span>								\
		<span zmlm:item="appReqList" class="appReqList"></span>		\
																	\
		<span class="splitter"></span>								\
	</div>															\
	<div id="tab-rejected">											\
		<span style="display:block">\
			<span class="examTempName appReqListHeader">'+Locale["template.manage.template.column.examTempName"]+'</span>\
			<span class="examTempUser appReqListHeader">'+Locale["template.manage.template.column.examTempUser"]+'</span>\
			<span class="examReqName appReqListHeader">'+Locale["template.manage.template.column.examReqName"]+'</span>\
			<span class="examTempRes appReqListHeader">'+Locale["template.manage.template.column.examTempRes"]+'</span>\
			<span class="examStatus appReqListHeader">'+Locale["template.manage.template.column.examStatus"]+'</span>\
			<span class="examSubTime appReqListHeader">'+Locale["template.manage.template.column.examSubTime"]+'</span>\
			<span class="examAdminActTime appReqListHeader">'+Locale["template.manage.template.column.examAdminActTime"]+'</span>\
		</span>														\
		<span class="splitter"></span>								\
		<span zmlm:item="appReqList" class="appReqList"></span>		\
																	\
		<span class="splitter"></span>								\
	</div>															\
</div>																\
';

var Template_ExamineRow='\
<span class="appReqListRow">\
	<input zmlm:item="examReqId" type="hidden" value="${requestid}" />\
	<span zmlm:item="examTempName" class="appReqListCell examTempName textCollapse" title="${templatename}">${templatename}</span>	\
	<span zmlm:item="examTempUser" class="appReqListCell examTempUser textCollapse" title="${userlogin}">${userlogin}</span>	\
	<span zmlm:item="examReqName" class="appReqListCell examReqName textCollapse" title="${requestedname}">${requestedname}</span>	\
	<span zmlm:item="examTempRes" class="appReqListCell examTempRes textCollapse appReqResCollapse">								\
		{{each sftresources}}																\
			<span class="appResBox">\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["template.manage.template.label.soft.id"]+'</span><span class="boxRight" title="${$value.softwareid}">${$value.softwareid}</span></span>\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["template.manage.template.label.soft.name"]+'</span><span class="boxRight" title="${$value.softwarename}">${$value.softwarename}</span></span>\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["template.manage.template.label.cpu"]+'</span><span class="boxRight">${$value.cpu} / ${$value.maxcpu}</span></span>\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["template.manage.template.label.mem"]+'</span><span class="boxRight">${formatSize($value.mem*1024)} / ${formatSize($value.maxmem*1024)}</span></span>\
			</span>\
		{{/each}}																			\
	</span>																															\
	<span zmlm:item="examStatus" class="appReqListCell examStatus textCollapse" title="${formatExamStatus(status)}">${formatExamStatus(status)}</span>\
	<span zmlm:item="examSubTime" class="appReqListCell examSubTime textCollapse">{{if submissiontime}}${formatDate(submissiontime.time)}{{/if}}</span>\
	<span zmlm:item="examAdminActTime" class="appReqListCell examAdminActTime textCollapse">{{if adminactiontime}}${formatDate(adminactiontime.time)}{{/if}}</span>\
</span>\
';









