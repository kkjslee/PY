/* JavaScript Document
Author: Bill, 2011~2012
<!--#config timefmt="%A %B %d, %Y %H:%M:%S" -->
This file last modified <!--#echo var="LAST_MODIFIED" -->

HTTP_REFERER: <!--#echo var="HTTP_REFERER" -->
REQUEST_METHOD: <!--#echo var="REQUEST_METHOD" -->
CONTENT_TYPE: <!--#echo var="CONTENT_TYPE" -->
UNIQUE_ID: <!--#echo var="UNIQUE_ID" -->
*/

/*
 * <!--#include virtual="../../../../config.shtml" -->
 */

var Locale={
	/*-- Global --*/
	"global.processing": "处理中, 请稍候...",
	
	/* Reference: http://datatables.net/plug-ins/i18n */
	dataTable: {
	    "sProcessing":   "处理中...",
	    "sLengthMenu":   "显示 _MENU_ 项结果",
	    "sZeroRecords":  "没有匹配结果",
	    "sInfo":         "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	    "sInfoEmpty":    "显示第 0 至 0 项结果，共 0 项",
	    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	    "sInfoPostFix":  "",
	    "sSearch":       "搜索:",
	    "sUrl":          "",
	    "oPaginate": {
	        "sFirst":    "首页",
	        "sPrevious": "上页",
	        "sNext":     "下页",
	        "sLast":     "末页"
	    }
	},
	
	/* ----Module Monitor Management---- */
	"gethost.banner" : "获取VM",
	
	"gethost.dialog.close" : "关闭",
	"gethost.dialog.create" : "创建",
	"gethost.dialog.modify" : "修改",
	
	"gethost.panel.title.datacenter": "1. 选择数据中心:",
	"gethost.panel.title.software": "2. 选择您所需要的镜像:",
	"gethost.panel.title.hardware": "3. 选择硬件配置:",
	"gethost.panel.title.plan": "4. 选择付费套餐:",
	"gethost.panel.title.naming": "5. 命名您的VM:",
	"gethost.panel.title.additional": "6. 附加配置:",
	
	"gethost.dialog.processing" : "处理中, 请稍候...",
	"gethost.dialog.title.tips" : "提示信息",
	"gethost.dialog.title.create" : "创建监视器",
	"gethost.dialog.title.update" : "修改监视器",
	
	"gethost.confirm.order.save" : "确认保存订单吗？",
	"gethost.confirm.order.submit" : "确认提交订单吗？",
	
	"gethost.message.type.in.saveorder.template.name" : "请填写模板名称。",
	"gethost.message.loading" : "数据加载中...",
	"gethost.message.error" : "出现异常, 请联系管理员",
	"gethost.message.undefined" : "未定义的返回 [%s]",
	"gethost.message.no_data": "无该模块数据",
	
	"gethost.template.tips" : "1. 请完整填写VM订单。<br/>2. 提交订单后请等待系统创建VM。",
	"gethost.template.tips.confirm" : "请仔细确认订单信息，订单中价格均以'元'为单位。",
	
	"gethost.template.software.name": "镜像名",
	"gethost.template.software.desc": "镜像描述",
	
	"gethost.template.hardware.type": "硬件类型",
	"gethost.template.hardware.dcpu": "默认CPU",
	"gethost.template.hardware.mcpu": "最大CPU",
	"gethost.template.hardware.dmem": "默认内存",
	"gethost.template.hardware.mmem": "最大内存",
	
	"gethost.template.plan.name": "付费套餐",
	"gethost.template.plan.desc": "套餐说明",
	"gethost.template.plan.price": "金额",
	"gethost.template.plan.cpuprice": "CPU价格（每运行1小时）",
	"gethost.template.plan.memprice": "内存价格",
	"gethost.template.plan.netprice": "网络流量（每GB）",
	
	"gethost.template.naming.name": "输入VM名",
	"gethost.template.naming.btncheck": "检查VM名，并进入下一步",
	"gethost.template.naming.tips": "（只允许输入英文字母, 数字或下划线, 数字不能作为开头）",
	
	"gethost.template.additional.network.subtitle": "公共网络:",
	"gethost.template.additional.network.needed": "是否购买公网IP",
	"gethost.template.additional.network.needed.yes": "是",
	"gethost.template.additional.network.needed.no": "否",
	"gethost.template.additional.network.type": "选择要使用的网络类型",
	"gethost.template.additional.network.type.public": "发布网站",
	"gethost.template.additional.network.type.private": "不发布网站",
	
	"gethost.template.additional.volume.subtitle": "扩展磁盘:",
	"gethost.template.additional.volume.needed": "是否购买公网IP",
	"gethost.template.additional.volume.needed.yes": "是",
	"gethost.template.additional.volume.needed.no": "否",
	"gethost.template.additional.volume.name": "扩展磁盘名",
	"gethost.template.additional.volume.name.tips": "（只允许输入英文字母, 数字或下划线, 数字不能作为开头）",
	"gethost.template.additional.volume.size": "选择扩展磁盘大小",
	
	"gethost.template.button.order.submit": "填写完毕，校验订单",
	
	"gethost.template.dialog.confirm.title": "订单",
	"gethost.template.dialog.confirm.software": "镜像",
	"gethost.template.dialog.confirm.vmname": "主机名",
	"gethost.template.dialog.confirm.datacenter": "数据中心",
	"gethost.template.dialog.confirm.dcpu": "默认CPU",
	"gethost.template.dialog.confirm.mcpu": "最大CPU",
	"gethost.template.dialog.confirm.dmem": "默认内存",
	"gethost.template.dialog.confirm.mmem": "最大内存",
	"gethost.template.dialog.confirm.plan": "付费套餐",
	"gethost.template.dialog.confirm.price": "金额",
	"gethost.template.dialog.confirm.cpuprice": "CPU价格",
	"gethost.template.dialog.confirm.memprice": "内存价格",
	"gethost.template.dialog.confirm.netprice": "网络流量价格",
	"gethost.template.dialog.confirm.hasnetwork": "是否购买公网IP",
	"gethost.template.dialog.confirm.networktype": "网络类型",
	"gethost.template.dialog.confirm.hasvolume": "是否购买扩展磁盘",
	"gethost.template.dialog.confirm.volumename": "扩展磁盘名",
	"gethost.template.dialog.confirm.volumesize": "磁盘大小",
	"gethost.template.dialog.confirm.fee.vm": "主机价格",
	"gethost.template.dialog.confirm.fee.network": "IP价格",
	"gethost.template.dialog.confirm.fee.volume": "磁盘价格",
	"gethost.template.dialog.confirm.fee.ordercount": "订单数量",
	"gethost.template.dialog.confirm.fee.total": "金额总计",
	"gethost.template.dialog.confirm.subtitle.software": "基础配置",
	"gethost.template.dialog.confirm.subtitle.hardware": "硬件配置",
	"gethost.template.dialog.confirm.subtitle.plan": "付费套餐",
	"gethost.template.dialog.confirm.subtitle.network": "网络配置",
	"gethost.template.dialog.confirm.subtitle.volume": "扩展磁盘配置",
	"gethost.template.dialog.confirm.subtitle.total": "订单合计",
	
	"gethost.template.dialog.saveorder.title": "保存订单模板",
	"gethost.template.dialog.saveorder.template.name": "模板名称",
	"gethost.template.dialog.saveorder.template.desc": "模板描述",

	"gethost.label.fee.network": "（IP价格： %s 元/个/30天）",
	"gethost.label.fee.volume": "（磁盘价格： %s 元/GB/天）",

	"gethost.label.tips.vmname.invalid": "该VM名不合法，请使用字母，数字或下划线组合。且数字不能作为开头，总字符数不超过8个。",
	"gethost.label.tips.vmname.unavailable": "该VM名不可用，请重新输入。",
	"gethost.label.tips.network.vacancy": "若需要使用网络设置，请填写网络的相关信息。",
	"gethost.label.tips.volume.vacancy": "若需要使用扩展磁盘，请填写扩展磁盘的相关信息。",
	"gethost.label.tips.volume.name.invalid": "该扩展磁盘名不合法，请使用字母，数字或下划线组合。且数字不能作为开头，总字符数不超过10个。",
	"gethost.label.tips.datacenter.unavailable": "数据中心不可用，请刷新页面或联系系统管理员。",
	
	"gethost.label.boolean.true": "是",
	"gethost.label.boolean.false": "否",
	"gethost.label.network.public": "发布网站",
	"gethost.label.network.private": "不发布网站",
	
	"gethost.button.order.save": "保存订单",
	"gethost.button.order.submit": "提交订单",
	
	"gethost.page.message.buy.prefix": "您的订单：",
	"gethost.page.message.buy.prefix.inst": "云主机：",
	"gethost.page.message.buy.NO_ENOUGH_STOCK": "对不起，系统无足够库存，请稍候再试",
	"gethost.page.message.buy.VM_EXCEEDED": "对不起，系统VM数已达许可证上限，请联系管理员",
	"gethost.page.message.buy.NOIDORDER": "对不起，无法下单，请稍后再试",
	"gethost.page.message.buy.ERROR": "对不起，无法下单，请稍后再试",
	"gethost.page.message.buy.EXCEPTION": "对不起，服务器异常，请稍候再试",
	"gethost.page.message.buy.LOWBALANCE": "对不起，您的余额已不足，请先充值",
	"gethost.page.message.buy.inst.NO_ENOUGH_STOCK": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"gethost.page.message.buy.inst.EXCEPTION": "对不起，服务器可能在维护，您的云主机购买操作未完成",
	"gethost.page.message.buy.inst.NAMENOTAVAILABLE": "对不起，您的云主机名字已被占用，您的云主机购买操作未完成",
	"gethost.page.message.buy.inst.OSTYPEERROR": "对不起，系统信息错误，请立即联系管理员",
	"gethost.page.message.buy.inst.ORDERED": "云主机申请成功，请等待管理员审核",
	"gethost.page.message.buy.inst.CREATED": "云主机购买提交成功，请等待Email确认，邮件确认后您可以到 “%s” 中操作您的云主机",
	"gethost.page.message.buy.inst.CREATED.keyword": "我的云产品",
	"gethost.page.message.buy.inst.LOWBALANCE": "对不起，由于您的余额不足，您的云主机购买操作未完成",
	"gethost.page.message.buy.inst.ONEVMONLY": "对不起，您已不能申请更多的云主机实例",
	"gethost.page.message.buy.inst.DONE": "云主机购买提交成功，成功 %s 个，失败 %s 个",
	"gethost.page.message.buy.inst.UNKNOWN_ERROR": "未知错误，请立即联系管理员",
	"gethost.page.message.buy.ip.NO_ENOUGH_STOCK": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"gethost.page.message.buy.ip.EXCEPTION": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"gethost.page.message.buy.ip.NOIP": "IP地址：对不起，您所选择的数据中心已无可用IP，您的IP购买操作未完成，请与管理员联系",
	"gethost.page.message.buy.ip.WEB": "IP地址：提交成功，如您需要发布网站，请提供您的备案信息",
	"gethost.page.message.buy.ip.LOWBALANCE": "IP地址：对不起，由于您的余额已不足，您的IP购买操作未完成",
	"gethost.page.message.buy.ip.PERSONAL": "IP地址：购买成功，您可以到 “%s” 中操作您的IP",
	"gethost.page.message.buy.ip.PERSONAL.keyword": "我的云产品",
	"gethost.page.message.buy.ip.BATCH": "IP地址：提交成功 %s 个，失败 %s 个",
	"gethost.page.message.buy.ip.UNKNOWN_ERROR": "IP地址：未知错误，请立即联系管理员",
	"gethost.page.message.buy.volume.NO_ENOUGH_STOCK": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"gethost.page.message.buy.volume.EXCEPTION": "磁盘：对不起，服务器可能在维护，您的磁盘购买操作未完成",
	"gethost.page.message.buy.volume.LOWBALANCE": "磁盘：对不起，由于您的余额已不足，您的磁盘购买操作未完成",
	"gethost.page.message.buy.volume.DONE": "磁盘：购买成功，您可以到 “%s” 中操作您的磁盘",
	"gethost.page.message.buy.volume.DONE.keyword": "我的云产品",
	"gethost.page.message.buy.volume.BATCH": "磁盘：购买成功 %s 个，失败 %s 个",
	"gethost.page.message.buy.volume.UNKNOWN_ERROR": "磁盘：未知错误，请立即联系管理员",
	
	"gethost.page.message.save.success": "保存完成。",
	"gethost.page.message.save.failure": "保存失败，请重试。"


	// please customize your locale string in file "extension/zh_CN.ex"
	//<!--#include virtual="extension/zh_CN.ex"-->
}


var Resource={
}




