// JavaScript Document
// Author: Bill, 2011

/**
 * Template - Software Item
 */
var Template_SoftwareItem='\
<span class="listItem">											\
	<span class="itemMain"><em>${name}</em></span>								\
	<span class="itemExtra">${type}</span>										\
	<span class="itemExtra">${desc}</span>										\
	<span class="itemExtra">${intro}</span>										\
	<span class="itemExtra NOT-IMPLEMENTED">\
		<a target="_blank" href="#" class="detail">'+Locale["project.manage.template.button.detail"]+'</a>\
	</span>			\
</span>';


var Template_MessageBox='\
<div title="'+Locale["project.dialog.title.tips"]+'">\
	<p class="message">{{html message}}</p>\
</div>';


var Template_ExamineTabs='\
<div id="${id}">													\
	<ul>															\
		<li><a href="#tab-unapproved"><span class="ui-icon ui-icon-signal-diag smallIcon"></span>'+Locale["project.apply.tab.unapproved"]+'</a></li>					\
		<li><a href="#tab-approved"><span class="ui-icon ui-icon-circle-check smallIcon"></span>'+Locale["project.apply.tab.proved"]+'</a></li>					\
		<li><a href="#tab-rejected"><span class="ui-icon ui-icon-circle-close smallIcon"></span>'+Locale["project.apply.tab.rejected"]+'</a></li>					\
	</ul>															\
																	\
	<div id="tab-unapproved">										\
		<span style="display:block">\
			<span class="examTempName appReqListHeader">'+Locale["project.manage.template.column.project.examTempName"]+'</span>\
			<span class="examTempUser appReqListHeader">'+Locale["project.manage.template.column.project.examTempUser"]+'</span>\
			<span class="examReqName appReqListHeader">'+Locale["project.manage.template.column.project.examReqName"]+'</span>\
			<span class="examTempRes appReqListHeader">'+Locale["project.manage.template.column.project.examTempRes"]+'</span>\
			<span class="examDuration appReqListHeader">'+Locale["project.manage.template.column.project.examDuration"]+'</span>\
			<span class="examStatus appReqListHeader">'+Locale["project.manage.template.column.project.examStatus"]+'</span>\
			<span class="examSubTime appReqListHeader">'+Locale["project.manage.template.column.project.examSubTime"]+'</span>\
			<span class="examAdminActTime appReqListHeader">'+Locale["project.manage.template.column.project.examAdminActTime"]+'</span>\
		</span>														\
		<span class="splitter"></span>								\
		<span zmlm:item="appReqList" class="appReqList"></span>		\
																	\
		<span class="splitter"></span>								\
		<span class="appReqBtnPanel">\
			<button zmlm:item="buttonApproved">'+Locale["project.manage.template.button.accept"]+'</button>\
			<button zmlm:item="buttonRejected">'+Locale["project.manage.template.button.reject"]+'</button>\
		</span>														\
	</div>															\
	<div id="tab-approved">											\
		<span style="display:block">\
			<span class="examTempName appReqListHeader">'+Locale["project.manage.template.column.project.examTempName"]+'</span>\
			<span class="examTempUser appReqListHeader">'+Locale["project.manage.template.column.project.examTempUser"]+'</span>\
			<span class="examReqName appReqListHeader">'+Locale["project.manage.template.column.project.examReqName"]+'</span>\
			<span class="examTempRes appReqListHeader">'+Locale["project.manage.template.column.project.examTempRes"]+'</span>\
			<span class="examDuration appReqListHeader">'+Locale["project.manage.template.column.project.examDuration"]+'</span>\
			<span class="examStatus appReqListHeader">'+Locale["project.manage.template.column.project.examStatus"]+'</span>\
			<span class="examSubTime appReqListHeader">'+Locale["project.manage.template.column.project.examSubTime"]+'</span>\
			<span class="examAdminActTime appReqListHeader">'+Locale["project.manage.template.column.project.examAdminActTime"]+'</span>\
		</span>														\
		<span class="splitter"></span>								\
		<span zmlm:item="appReqList" class="appReqList"></span>		\
																	\
		<span class="splitter"></span>								\
	</div>															\
	<div id="tab-rejected">											\
		<span style="display:block">\
			<span class="examTempName appReqListHeader">'+Locale["project.manage.template.column.project.examTempName"]+'</span>\
			<span class="examTempUser appReqListHeader">'+Locale["project.manage.template.column.project.examTempUser"]+'</span>\
			<span class="examReqName appReqListHeader">'+Locale["project.manage.template.column.project.examReqName"]+'</span>\
			<span class="examTempRes appReqListHeader">'+Locale["project.manage.template.column.project.examTempRes"]+'</span>\
			<span class="examDuration appReqListHeader">'+Locale["project.manage.template.column.project.examDuration"]+'</span>\
			<span class="examStatus appReqListHeader">'+Locale["project.manage.template.column.project.examStatus"]+'</span>\
			<span class="examSubTime appReqListHeader">'+Locale["project.manage.template.column.project.examSubTime"]+'</span>\
			<span class="examAdminActTime appReqListHeader">'+Locale["project.manage.template.column.project.examAdminActTime"]+'</span>\
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
	<input zmlm:item="examReqId" type="hidden" value="${projectid}" />\
	<span zmlm:item="examTempName" class="appReqListCell examTempName textCollapse" title="${projectname}">${projectname}</span>	\
	<span zmlm:item="examTempUser" class="appReqListCell examTempUser textCollapse" title="${userlogin}">${userlogin}</span>	\
	<span zmlm:item="examReqName" class="appReqListCell examReqName textCollapse" title="${projectdes}">${projectdes}</span>	\
	<span zmlm:item="examTempRes" class="appReqListCell examTempRes">								\
		{{if sftresources.length==0}}\
			<span class="appResBox">\
				<em>'+Locale["project.manage.template.label.no_res"]+'</em>\
			</span>\
		{{else}}\
		{{each sftresources}}\
			<span class="appResBox">\
				<b>'+Locale["project.manage.template.label.soft.res"].sprintf("${$value.vmprefix}", "${$value.quantity}")+'</b>\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["project.manage.template.label.soft.id"]+'</span><span class="boxRight" title="${$value.sftresourceid}">${$value.sftresourceid}</span></span>\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["project.manage.template.label.soft.name"]+'</span><span class="boxRight" title="${$value.softwarename}">${$value.softwarename}</span></span>\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["project.manage.template.label.soft.cpu"]+'</span><span class="boxRight">${$value.cpu} / ${$value.maxcpu}</span></span>\
				<span class="boxLine"><span class="boxLeft appResBoxLeft">'+Locale["project.manage.template.label.soft.mem"]+'</span><span class="boxRight">${formatSize($value.mem*1024)} / ${formatSize($value.maxmem*1024)}</span></span>\
			</span>\
		{{/each}}\
		{{/if}}\
		<!--IP configuration list-->\
		{{if ipresources.length>0}}\
			<span class="appResBox">\
				<b>'+Locale["project.manage.template.label.network"]+'</b>\
				{{each ipresources}}\
					{{if $value.iptype=="web"}}\
						<span class="boxLine">\
							<span class="boxLeft">'+Locale["project.manage.template.label.network.web"]+'</span>\
							<span class="boxRight">${$value.quantity} '+Locale["project.manage.template.label.unit"]+'</span>\
						</span>\
					{{else $value.iptype=="personal"}}\
						<span class="boxLine">\
							<span class="boxLeft">'+Locale["project.manage.template.label.network.personal"]+'</span>\
							<span class="boxRight">${$value.quantity} '+Locale["project.manage.template.label.unit"]+'</span>\
						</span>\
					{{/if}}\
				{{/each}}\
			</span>\
		{{/if}}\
		<!--Volume configuration list-->\
		{{if volumeresources.length>0}}\
			<span class="appResBox">\
				<b>'+Locale["project.manage.template.label.volume"]+'</b>\
				{{each volumeresources}}\
					<span class="boxLine" title="'+Locale["project.manage.template.label.volume.prefix"].sprintf("${$value.volumeprefix}")+'">\
						<span class="boxLeft boxLeftWider">{{html formatSize($value.volumesize)}}:</span>\
						<span class="boxRight">'+Locale["project.manage.template.label.volume.amount"].sprintf("${$value.quantity}", "${$value.volumeprefix}")+'</span>\
					</span>\
				{{/each}}\
			</span>\
		{{/if}}\
	</span>\
	<span zmlm:item="examStatus" class="appReqListCell examDuration textCollapse">${formatDate(projectstarttime.time)} ~ ${formatDate(projectendtime.time)}</span>\
	<span zmlm:item="examStatus" class="appReqListCell examStatus textCollapse" title="${formatExamStatus(requeststatus)}">${formatExamStatus(requeststatus)}</span>\
	<span zmlm:item="examSubTime" class="appReqListCell examSubTime textCollapse">{{if submissiontime}}${formatDate(submissiontime.time)}{{/if}}</span>\
	<span zmlm:item="examAdminActTime" class="appReqListCell examAdminActTime textCollapse">{{if adminactiontime}}${formatDate(adminactiontime.time)}{{/if}}</span>\
</span>\
';









