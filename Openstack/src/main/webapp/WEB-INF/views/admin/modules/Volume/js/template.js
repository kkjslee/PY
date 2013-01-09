// JavaScript Document
// Author: Bill, 2011

var Template_VolumePanel='\
<div id="${id}">\
	<span class="headerRow">\
		<span class="volumeName volumeRowHeader">'+Locale["volume.template.volume.name"]+':<input name="filter_volumename" style="width:60px;" placeholder="'+Locale["global.placeholder.enter"]+'" /></span>\
		<span class="volumeZone volumeRowHeader">'+Locale["volume.template.volume.zone"]+':<input name="filter_volumezone" style="width:60px;" placeholder="'+Locale["global.placeholder.enter"]+'" /></span>\
		<span class="volumeOwner volumeRowHeader">'+Locale["volume.template.volume.owner"]+':<input name="filter_volumeowner" style="width:60px;" placeholder="'+Locale["global.placeholder.enter"]+'" /></span>\
		<span class="volumeSize volumeRowHeader">'+Locale["volume.template.volume.size"]+'</span>\
		<span class="volumeVm volumeRowHeader">'+Locale["volume.template.volume.vm"]+'</span>\
		<span class="volumeType volumeRowHeader">'+Locale["volume.template.volume.type"]+'</span>\
		<span class="volumeTarget volumeRowHeader">'+Locale["volume.template.volume.target"]+'</span>\
		<span class="volumeAssignedTo volumeRowHeader">'+Locale["volume.template.volume.assigned.to"]+'</span>\
		<span class="volumeOperation volumeRowHeader">'+Locale["volume.template.volume.operation"]+'</span>\
	</span>														\
	<span zmlm:item="volumeList" class="volumeList"></span>		\
	<span class="footerRow">\
		<button onclick="loadVolume()">'+Locale["volume.template.volume.refresh"]+'</button>\
	</span>\
</div>\
';

var Template_VolumekRow='\
<span class="volumeRow">\
	<input zmlm:item="volumeId" type="hidden" value="${volumeid}" />\
	<input zmlm:item="volumeVm" type="hidden" value="${vmid}" />\
	<input zmlm:item="volumeZone" type="hidden" value="${zone}" />\
	<input zmlm:item="volumeName" type="hidden" value="${displayname}" />\
	<input zmlm:item="volumeOwner" type="hidden" value="${volumeowner}" />\
	<input zmlm:item="volumeDrive" type="hidden" value="${targetdrive}" />\
	<input zmlm:item="volumeAssignedTo" type="hidden" value="${assignedto}" />\
	<span class="volumeRowCell volumeName textCollapse">${displayname}</span>\
	<span class="volumeRowCell volumeZone textCollapse">${zonename}</span>\
	<span class="volumeRowCell volumeOwner textCollapse">${volumeowner}</span>\
	<span class="volumeRowCell volumeSize textCollapse">${volumesize} GB</span>\
	<span class="volumeRowCell volumeVm textCollapse" title="${vmname}">${vmname}</span>\
	<span class="volumeRowCell volumeType textCollapse">{{html formatVolumetype(volumetype)}}</span>\
	<span class="volumeRowCell volumeTarget textCollapse">${targetdrive}</span>\
	<span class="volumeRowCell volumeAssignedTo textCollapse" title="${assignedto}">${assignedto}</span>\
	<span class="volumeRowCell volumeOperation textCollapse">\
		<a href="#" onclick="showAttachVolumeDialog(this);return false;">'+Locale["volume.template.volume.attach"]+'</a>\
		{{if vmid && vmid!=""}}\
		<a href="#" onclick="detachVolume(this);return false;">'+Locale["volume.template.volume.detach"]+'</a>\
		{{/if}}\
		<a href="#" onclick="removeVolume(this);return false;">'+Locale["volume.template.volume.remove"]+'</a>\
		{{if assignedto}}\
		<a href="#" onclick="unassign(this);return false;">'+Locale["volume.template.volume.unassign"]+'</a>\
		{{/if}}\
	</span>\
</span>\
';

var Template_MessageBox='\
<div title="'+Locale["volume.dialog.title.tips"]+'">\
	<p class="message">{{html message}}</p>\
</div>';







