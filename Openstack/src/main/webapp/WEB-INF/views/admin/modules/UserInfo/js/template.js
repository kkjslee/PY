var Template_UserInfoList = '\
<div>\
	<table class="finder" zmlm:item="finder">\
		<thead class="finder-indicator">\
			<tr>\
				<th><span class="ui-icon ui-icon-search"></span></th>\
				<th>Finder</th>\
			</tr>\
		</thead>\
		<tbody>\
			<tr>\
				<td id="filterHead0"><span>' + Locale["userinfo.label.header.name"] + ' : </span><span><input type="text" style="width:100px;" name="filter_loginid"></span>\
				<td id="filterHead1"><span>' + Locale["userinfo.label.header.cellphone"] + ' : </span><span><input type="text" style="width:100px;"></span>\
		</tbody>\
	</table>\
</div>\
<div id="${id}" >\
	<table class="dataTableFile" >\
	<thead>\
		<tr class="headerRow">\
			<th class="userListName">' + Locale["userinfo.label.header.name"] + '</th>\
			<th class="userListFirstname">' + Locale["userinfo.label.header.firstname"] + '</th>\
			<th class="userListLastname">' + Locale["userinfo.label.header.lastname"] + '</th>\
			<th class="userListEmail">' + Locale["userinfo.label.header.email"] + '</th>\
			<th class="userListCellphone">' + Locale["userinfo.label.header.cellphone"] + '</th>\
			<th class="userListBalance">' + Locale["userinfo.label.header.balance"] + '</th>\
			<th class="userListStatus">' + Locale["userinfo.label.header.status"] + '\
				<select style="width:80px;" name="filter_loginstatus">\
					<option value="">' + Locale["userinfo.message.choose.status.filter"] + '</option>\
					<option value="'+Locale["userinfo.message.choose.status.zero"]+'">' + Locale["userinfo.message.choose.status.zero"] + '</option>\
					<option value="'+Locale["userinfo.message.choose.status.one"]+'">' + Locale["userinfo.message.choose.status.one"] + '</option>\
					<option value="'+Locale["userinfo.message.choose.status.three"]+'">' + Locale["userinfo.message.choose.status.three"] + '</option>\
				</select>\
			</th>\
			<th class="userListRegtime">' + Locale["userinfo.label.header.regtime"] + '</th>\
			<th class="userListOrganisation">' + Locale["userinfo.label.header.organisation"] + '</th>\
			<th class="usermListRole">' + Locale["userinfo.label.header.role"] + '</th>\
			<th class="usermListOperation">' + Locale["userinfo.label.header.operation"] + '</th>\
		</tr>\
	</thead>\
	<tbody>\
	</tbody>\
	<tfoot>\
	<tr class="footerRow">\
	<td colspan="8" style="text-align:center;">\
	</td>\
	<td colspan="3" style="text-align:right;">\
	</td>\
	</tr>\
	</tfoot>\
	</table>\
</div>\
';

var Template_UserInfoRow = '\
<tr class="userInfoRow">\
	<input zmlm:item="loginid" type="hidden" value="${loginid}" />\
	<input zmlm:item="role" type="hidden" value="${role}" />\
	<input zmlm:item="loginstatus" type="hidden" value="${loginstatus}" />\
	<input zmlm:item="loginstatusdisplay" type="hidden" value="${loginstatusdisplay}" />\
	<input zmlm:item="firstname" type="hidden" value="${firstname}" />\
	<input zmlm:item="lastname" type="hidden" value="${lastname}" />\
	<input zmlm:item="email" type="hidden" value="${email}" />\
	<input zmlm:item="phone" type="hidden" value="${phone}" />\
	<input zmlm:item="status" type="hidden" value="${status}" />\
	<input zmlm:item="organisation" type="hidden" value="${organisation}" />\
	<input zmlm:item="regtime" type="hidden" value="${regtime}" />\
	<input zmlm:item="balance" type="hidden" value="${balance}" />\
	<td class="userListName" >${loginid}</td>\
	<td class="userListFirstname" >${firstname}</td>	\
	<td class="userListLastname" >${lastname}</td>\
	<td class="userListEmail" >${email}</td>\
	<td class="userListCellphone" >${phone}</td>\
	<td class="userListBalance" >${balance.toFixed(2)}</td>\
	<td class="userListStatus {{if loginstatus}} {{if loginstatus==1}}green{{/if}}  {{if loginstatus==3}}orange{{/if}} {{/if}}" >${loginstatusdisplay}</td>\
	<td class="userListRegtime">${formatDate(regtime.time)}</td>\
	<td class="userListOrganisation">${organisation}</td>\
	<td class="usermListRole">${role}</td>\
	<td class="usermListOperation">\
		<a href="#" onclick="showUpdateUserInfoItem(this);return false;">' + Locale["userinfo.label.operation.update"] + '</a>\
		<a href="#" onclick="showRechargeUserInfoItem(this);return false;">' + Locale["userinfo.label.operation.recharge"] + '</a>\
	</td>\
</tr>\
';

var Template_UpdateUserItem = '\
	<div id="${id}" style="font-size:12px;">\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.name"] + ':</span>\
			<span class="dialogLineRight">\
				<span zmlm:item="loginid"></span>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.role"] + ':</span>\
			<span class="dialogLineRight">\
			<select id="selRole" zmlm:item="role" style="width:140px;height:24px">\
				<option value="is_user" >is_user</option>\
				<option value="is_admin">is_admin</option>\
				<option value="is_2ndadmin">is_2ndadmin</option>\
			</select>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.status"] + ':</span>\
			<span class="dialogLineRight">\
			<select id="selStatus" zmlm:item="loginstatus" style="width:140px;height:24px">\
				<option value="0">' + Locale["userinfo.message.choose.status.zero"] + '</option>\
				<option value="1">' + Locale["userinfo.message.choose.status.one"] + '</option>\
				<option value="3">' + Locale["userinfo.message.choose.status.three"] + '</option>\
			</select>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.firstname"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="firstname" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.lastname"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="lastname" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.email"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="email" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.cellphone"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="phone" type="text" maxlength="20" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.organisation"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="organisation" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.operation.ifchangepw"] + ':</span>\
			<span class="dialogLineRight changePW">\
				<input class="ifchangepw" type="checkbox"  zmlm:item="ifchangepw" />\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.operation.newpassword"] + ':</span>\
			<span class="dialogLineRight">\
				<input type="password" zmlm:item="newpassword" disabled="disabled" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.operation.confirmpw"] + ':</span>\
			<span class="dialogLineRight">\
				<input type="password" zmlm:item="confirmpw" disabled="disabled" value=""/>\
			</span>\
		</span>\
	</div>';

var Template_RechargeUserItem = '\
	<div id="${id}" style="font-size:12px;">\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.name"] + ':</span>\
			<span class="dialogLineRight">\
				<span zmlm:item="requestedloginname"></span>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.header.balance"] + ':</span>\
			<span class="dialogLineRight">\
				<span zmlm:item="balance"></span>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.recharge.type"] + ':</span>\
			<span class="dialogLineRight">\
			<select id="selStatus" zmlm:item="type" style="width:140px;height:24px">\
				<option value="">' + Locale["userinfo.label.recharge.choosetype"] + '</option>\
				<option value="credit">' + Locale["userinfo.label.recharge.payment"] + '</option>\
				<option value="payment">' + Locale["userinfo.label.recharge.credit"] + '</option>\
				<option value="refund">' + Locale["userinfo.label.recharge.refund"] + '</option>\
			</select>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.recharge.amount"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="amount" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.recharge.notes"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="notes" type="text" value=""/>\
			</span>\
		</span>\
		<span class="dialogLine">\
			<span class="dialogLineLeft">' + Locale["userinfo.label.recharge.reason"] + ':</span>\
			<span class="dialogLineRight">\
				<input zmlm:item="reason" type="text" value=""/>\
			</span>\
		</span>\
	</div>';

var Template_MessageBox = '\
<div title="' + Locale["userinfo.dialog.tips"] + '">\
	<p class="message">{{html message}}</p>\
</div>\
';