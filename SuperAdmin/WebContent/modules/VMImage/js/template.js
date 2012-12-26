var Template_VMImageList = '\
<div id="${id}" >\
	<table class="dataTableFile">\
	<thead>\
		<tr>\
			<th class="vmListPath">' + Locale["vmimage.label.header.path"] + '</th>\
			<th class="vmListFolder">' + Locale["vmimage.label.header.folder"] + '</th>\
			<th class="vmListName">' + Locale["vmimage.label.header.name"] + '</th>\
			<th class="vmListOS">' + Locale["vmimage.label.header.os"] + '</th>\
			<th class="vmListSize">' + Locale["vmimage.label.header.size"] + '</th>\
			<th class="vmListType">' + Locale["vmimage.label.header.type"] + '</th>\
			<th class="vmListOperation">' + Locale["vmimage.label.header.operation"] + '</th>\
		</tr>\
		<tr >\
			<th colspan="7" class="splitter">\
			</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	<tr>\
	<td colspan="7" class="splitter" >\
	</td>\
	</tr>\
	<tr>\
	<td colspan="7">\
	<button onclick="loadVMImageList()">' + Locale["vmimage.label.table.refresh"] + '</button>\
	<button onclick="showCreateVMImageItem()">' + Locale["vmimage.lael.operation.create"] + '</button>\
	</td>\
	</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_VMImagekRow = '\
<tr class="vmImagekRow">\
	<input zmlm:item="imageid" type="hidden" value="${imageid}" />\
	<input zmlm:item="imagepath" type="hidden" value="${imagepath}" />\
	<input zmlm:item="imagefolder" type="hidden" value="${imagefolder}" />\
	<input zmlm:item="imagename" type="hidden" value="${imagename}" />\
	<input zmlm:item="imageos" type="hidden" value="${imageos}" />\
	<input zmlm:item="imagesize" type="hidden" value="${imagesize}" />\
	<input zmlm:item="imagetype" type="hidden" value="${imagetype}" />\
	<td class="vmListPath" title="${imagepath}">${imagepath}</td>\
	<td class="vmListFolder" title="${imagefolder}">${imagefolder}</td>\
	<td class="vmListName" title="${imagename}">${imagename}</td>\
	<td class="vmListOS" title="${imageos}">{{html formatOstype(imageos)}}</td>	\
	<td class="vmListSize">${formatSize(imagesize)}</td>\
	<td class="vmListType" title="${imagetype}">${imagetype}</td>\
	<td class="vmListOperation">\
		<a href="#" onclick="showUpdateVMImageItem(this);return false;">' + Locale["vmimage.label.operation.update"] + '</a>\
		<a href="#" onclick="remove(this);return false;">' + Locale["vmimage.label.operation.remove"] + '</a>\
	</td>\
</tr>\
';

var Template_CreateVMImageItem = '\
	<div id="${id}" style="font-size:12px;">\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["vmimage.label.header.path"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="imagepath" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["vmimage.label.header.folder"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="imagefolder" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["vmimage.label.header.name"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="imagename" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["vmimage.label.header.os"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="imageos" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["vmimage.label.header.size"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="imagesize" type="text" value=""/> ' + Locale["vmimage.label.unit.bytes"] + '</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["vmimage.label.header.type"] + ':</span>\
			<span class="dialogLineRight typeSelect">\
				<select id="selimagetype" zmlm:item="imagetype" style="width:140px;height:24px">\
					<option value="hvm" selected>windows</option>\
					<option value="linux">linux</option>\
					<option value="other">other</option>\
				</select>\
				<input zmlm:item="imagetype" type="hidden" value="hvm"/>\
			</span>\
		</span>\
	</div>';

var Template_MessageBox = '\
<div title="' + Locale["vmimage.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';