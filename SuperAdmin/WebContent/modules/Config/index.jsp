<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Zhufeng Configuration</title>

<link rel="shortcut icon" href="../../favicon.ico">

<script src="../../locale.jsp" language="javascript"></script>
<script type="text/javascript" src="../../js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="../../js/String.js"></script>
<script type="text/javascript" src="../../js/server.js"></script>

<style>
*{
	font-family: 微软雅黑;
	font-size:14px;
}
</style>

<script type="text/javascript">

<%
// global variables
String version="1.0.56";
boolean advance=false;

String isSuperAdmin=request.getParameter("bill.chaos");
if(null!=isSuperAdmin) {
	advance=true;
}

String template="var comProps=new Object();"
	+"comProps['smtpPwd']=Locale['config.label.smtpPwd'];"
	+"comProps['smtpAccount']=Locale['config.label.smtpAccount'];"
	+"comProps['smtpPort']=Locale['config.label.smtpPort'];"
	+"comProps['smtpHost']=Locale['config.label.smtpHost'];"
	+"comProps['smtpType']=Locale['config.label.smtpType'];"
	+"comProps['supportEmailAddress']=Locale['config.label.supportEmailAddress'];";

out.println(template);

%>





$(function(){
	getComputeZone();
	getVolumesZone();
	getProperties();
	getNetwork();
});


var url=Server+'/RedDragonEnterprise/admin/zonectrl';
var url2=Server+'/RedDragonEnterprise/admin/propctrl';
var url3=Server+'/RedDragonEnterprise/admin/dhcpgenerator';

function getNetwork() {
	
}


//Get System Properties Info
function getProperties() {
	var args={operation:'getpropinfor'};
	
	$('#pstatus').show();
	$.post(url2, args, function(retVal){
		var json=eval(retVal);

		$('#properties').empty();
		
		for(i=0; i<json.length; i++){
			var item=eval(json[i]);
			
			for(var key in item) {
				//$('#properties').append("item="+key);
				var disKey=null;
				if(<% out.println(""+advance); %> || null!=(disKey=comProps[key])) {
					appendProperty(key, item[key], disKey, true);
				}else {
					appendProperty(key, item[key], disKey, false);
				}
			}
		}
		
		$('#pstatus').hide();
	});
}

function appendProperty(key, value, display, visible) {
	var template='<tr style="display:'+(visible?'':'none')+';"><td style="width:15%;text-align:right;">@display</td>'
		+'<td style="text-align:left;"><input size="60" name="propGroup" id="@key" value="@value" type="text" /></td></tr>';
	template=template.replace(/@key/g, key);
	template=template.replace(/@value/g, value);
	template=template.replace(/@display/g, display==null?key:display);
	
	$('#properties').append(template);
	
}


//Get Compute Zone Info
function getComputeZone() {
	var args={operation:'fetchzoneinfor',zonetype:'zone'};

	$('#cstatus').show();
	$.post(url, args, function(retVal){
		var json=eval(retVal);

		var str='';
		for(i=0; i<json.length; i++) {
			str+=json[i].zonename;
			str+='/';
			str+=json[i].zonedes;
			str+='/';
			str+=json[i].zonedisplayname;
			str+='/';
			str+=json[i].zoneentryip;
			str+=',\n';
		}
		
		$('#czones').val(str);

		$('#cstatus').hide();
	});
}

// Get Disk Zone Info
function getVolumesZone() {
	var args={operation:'fetchzoneinfor',zonetype:'diskzone'};

	$('#vstatus').show();
	$.post(url, args, function(retVal){
		var json=eval(retVal);

		var str='';
		for(i=0; i<json.length; i++) {
			str+=json[i].zonename;
			str+='/';
			str+=json[i].zonedes;
			str+='/';
			str+=json[i].zonedisplayname;
			str+='/';
			str+=json[i].zoneentryip;
			str+=',\n';
		}
		
		$('#vzones').val(str);
		
		$('#vstatus').hide();
	});
}

function updateNetwork() {
	if(""==$('#ip_s').val() || ""==$('#ip_e').val() 
			|| ""==$('#broadcast').val()
			|| ""==$('#gateway').val()
			|| ""==$('#dns').val()
			|| ""==$('#interface').val()
			|| ""==$('#subnet').val()
			|| ""==$('#zone').val()) {
		
		alert(Locale['config.message.network.script.empty']);
	}else {
		var ip_s=$('#ip_s').val();
		var ip_e=$('#ip_e').val();
		var broadcast=$('#broadcast').val();
		var gateway=$('#gateway').val();
		var dns=$('#dns').val();
		var interf=$('#interface').val();
		var subnet=$('#subnet').val();
		var netmask=$('#netmask').val();
		var zone=$('#zone').val();
		
		var args={startip:ip_s, endip:ip_e, netmask:netmask, 
				subnet:subnet, gateway:gateway, dns:dns, 
				interf:interf, broadcast:broadcast, zone:zone};
		
		if(confirm(Locale['config.confirm.dhcp'])){
			$('#nstatus').show();
			$.post(url3, args, function(retVal){
				$('#dhcp').val(retVal);
				$('#nstatus').hide();
			});
		}
	}
	
}

function updateProps() {
	var str='';
	$('input[name="propGroup"]').each(function(index, element){
		str+=($(this).attr('id')+'='+$(this).val()+',');
	});

	var args={operation:'updateprops',pairs:str};
	if(confirm(Locale['config.confirm.properties'])){
		$('#pstatus').show();
		$.post(url2, args, function(retVal){
			if(-1!=retVal.indexOf('@succeed')) {
				alert(Locale['config.message.properties.success']);
			}else {
				alert(Locale['config.message.properties.failure'].sprintf(retVal));
			}
			$('#pstatus').hide();
			getProperties();
		});
	}
}

function updateCZone() {
	var tmp=$('#czones').val().replace(/<[b|B][r|R]>/g,"");;
	var args={operation:'updatezone',zonetype:'zone',zoneinfor:tmp};

	if(confirm(Locale['config.confirm.zone.compute'])){
		$('#cstatus').show();
		$.post(url, args, function(retVal){
			if(-1!=retVal.indexOf('@succeed')) {
				alert(Locale['config.message.zone.compute.success']);
			}else {
				alert(Locale['config.message.zone.compute.failure'].sprintf(retVal));				
			}
			$('#cstatus').hide();
			getComputeZone();
		});
	}
}

function updateVZone() {
	var tmp=$('#vzones').val().replace(/<[b|B][r|R]>/g,"");;
	var args={operation:'updatezone',zonetype:'diskzone',zoneinfor:tmp};

	if(confirm(Locale['config.confirm.zone.volume'])){
		$('#vstatus').show();
		$.post(url, args, function(retVal){
			if(-1!=retVal.indexOf('@succeed')) {
				alert(Locale['config.message.zone.volume.success']);
			}else {
				alert(Locale['config.message.zone.volume.failure'].sprintf(retVal));				
			}
			$('#vstatus').hide();
			getVolumesZone();
		});
	}
}

</script>


</head>

<body>


<div style="position:fixed;top:0;left:0;background:#f8f8f8;margin:0px;line-height:32px;width:100%;border-bottom:1px solid silver;font-size:18px;font-weight:bold;">
	<!--<img src="css/img/logo.png" style="width:108px;"/>-->
	&nbsp;&nbsp;
	<script>document.write(Locale['config.title'])</script>
	<% 
	if(advance){
		out.println("&nbsp;(<script>document.write(Locale['config.subtitle.advance'])</script>)&nbsp;");
	} 
	%>
	&nbsp;&nbsp;
	<%
	if(advance) { 
	%>
		<a href="index.jsp" style="font-size:10px;color:blue;font-weight:normal;">
		(<script>document.write(Locale['config.anchor.common'])</script>)
		</a>&nbsp;
	<% 
	} else { 
	%>
		<a href="index.jsp?bill.chaos" style="font-size:10px;color:blue;font-weight:normal;">
		(<script>document.write(Locale['config.anchor.advance'])</script>)
		</a>
	<% 
	}
	%>
	&nbsp;
	<label style="font-size:12px;color:#4e4e4e;">ver.<% out.print(version); %></label>
</div>

<div style="margin:20px;margin-top:60px;padding:5px;border:1px solid silver;">

<div style="margin:10px;">
<script>document.write(Locale['config.label.notice'])</script>
</div>

<div id="content" style="margin:10px;border:1px solid silver;text-align:left;">
	<div style="margin:5px;padding:10px;border-bottom:1px solid silver;font-size:16px;font-weight:bold;">
		<script>document.write(Locale['config.label.zoneconf'])</script>
	</div>
	<div style="margin:5px;padding:10px;border-bottom:1px solid silver;">
		<div>
			<label for="czones"><script>document.write(Locale['config.label.czones'])</script></label>
			<button onclick="updateCZone();"><script>document.write(Locale['config.button.czones'])</script></button>
			<img src="css/img/loading.gif" id="cstatus" style="display:none;width:16px;" />
		</div>
		<textarea id="czones" style="width:90%;resize:none;" rows="6"></textarea>
		<div style="color:red;"><script>document.write(Locale['config.label.czones.sample'])</script></div>
	</div>
	
	<div style="margin:5px;padding:10px;border-bottom:1px solid silver;">
		<div>
			<label for="vzones"><script>document.write(Locale['config.label.vzones'])</script></label>
			<button onclick="updateVZone();"><script>document.write(Locale['config.button.vzones'])</script></button>
			<img src="css/img/loading.gif" id="vstatus" style="display:none;width:16px;" />
		</div>
		<textarea id="vzones" style="width:90%;resize:none;" rows="6"></textarea>
		<div style="color:red;"><script>document.write(Locale['config.label.vzones.sample'])</script></div>
	</div>
	
	
	<div style="margin:5px;padding:10px;border-bottom:1px solid silver;font-size:16px;font-weight:bold;">
			<script>document.write(Locale['config.label.properties'])</script>
			<button onclick="updateProps();"><script>document.write(Locale['config.button.properties'])</script></button>
			<img src="css/img/loading.gif" id="pstatus" style="display:none;width:16px;" />
	</div>
	<div style="margin:5px;padding:10px;border-bottom:1px solid silver;">
		<div>
			<table id="properties" style="width:100%;"></table>			
		</div>
	</div>
	
	
	
	<div style="margin:5px;padding:10px;border-bottom:1px solid silver;font-size:16px;font-weight:bold;">
			<script>document.write(Locale['config.button.network'])</script>
			<label style="color:red;">(Important!!)</label>
			<img src="css/img/loading.gif" id="nstatus" style="display:none;width:16px;" />
	</div>
	<div style="margin:5px;padding:10px;border-bottom:1px solid silver;">
		<div>
			<div>
				<p>
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.ipfragment'])</script></label>
					<input title="eg: 192.168.1.100" id="ip_s" type="text" size="20"/>
					&nbsp;~&nbsp;
					<input title="eg: 192.168.1.200"  id="ip_e" type="text" size="20"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.netmask'])</script></label>
					<input title="eg: 255.255.255.0"  id="netmask" type="text" size="20"/>
				</p>
				<p>
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.broadcast'])</script></label>
					<input title="eg: 192.168.1.255"  id="broadcast" type="text" size="20"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.gateway'])</script></label>
					<input title="eg: 192.168.1.1"  id="gateway" type="text" size="20"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.dns'])</script></label>
					<input title="eg: 192.168.1.88"  id="dns" type="text" size="20"/>
				</p>
				<p>
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.interface'])</script></label>
					<input id="interface" type="text" size="20"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.subnet'])</script></label>
					<input id="subnet" type="text" size="20"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<label style="width:120px;text-align:right;"><script>document.write(Locale['config.label.zone'])</script></label>
					<input id="zone" type="text" size="20"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<button onclick="updateNetwork();">
						<script>document.write(Locale['config.button.network'])</script>
					</button>
				</p>
			</div>
			<div>
				<label style="width:120px;text-align:right;">
					<script>document.write(Locale['config.label.dhcpscript'])</script>
				</label><br/>
				<textarea id="dhcp" rows="20" style="width:90%;resize:none;"></textarea>
			</div>
		</div>
	</div>
	
	
	
</div>





</div>





</body>


</html>