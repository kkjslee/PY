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
	"ordertemplate.banner" : "获取VM",
	
	"ordertemplate.dialog.close" : "关闭",
	
	"ordertemplate.dialog.processing" : "处理中, 请稍候...",
	"ordertemplate.dialog.title.tips" : "提示信息",
	
	"ordertemplate.confirm.order.remove" : "确认删除订单模板吗？",
	"ordertemplate.confirm.order.submit" : "确认提交订单吗？",
	
	"ordertemplate.message.type.in.saveorder.template.name" : "请填写模板名称。",
	"ordertemplate.message.loading" : "数据加载中...",
	"ordertemplate.message.error" : "出现异常, 请联系管理员",
	"ordertemplate.message.undefined" : "未定义的返回 [%s]",
	"ordertemplate.message.no_data": "无该模块数据",
	"ordertemplate.message.remove.done" : "删除完毕",
	"ordertemplate.message.remove.failed" : "删除失败，请重试或联系管理员",
	
	"ordertemplate.template.tips" : "请从下方选择推荐的模板进行购买，或者点击自定义选项订购您所需要的VM。",
	"ordertemplate.template.tips.confirm" : "请仔细确认订单信息，订单中价格均以'元'为单位。",
	
	"ordertemplate.template.grid.software.image": "模板镜像",
	"ordertemplate.template.grid.software.zone": "数据中心",
	"ordertemplate.template.grid.plan.name": "付费套餐",
	"ordertemplate.template.grid.plan.fee": "套餐费用",
	"ordertemplate.template.grid.network": "网络配置",
	"ordertemplate.template.grid.network.none": "无",
	"ordertemplate.template.grid.volume": "扩展磁盘",
	"ordertemplate.template.grid.volume.none": "无",
	"ordertemplate.template.grid.hardware.type": "硬件类型",
	"ordertemplate.template.grid.hardware.dcpu": "默认CPU",
	"ordertemplate.template.grid.hardware.mcpu": "最大CPU",
	"ordertemplate.template.grid.hardware.dmem": "默认内存",
	"ordertemplate.template.grid.hardware.mmem": "最大内存",
	"ordertemplate.template.grid.fee.unit": "¥",
	"ordertemplate.template.grid.tips.notes.empty": "该快速订单模板无可用备注信息。",
	"ordertemplate.template.grid.tips.name.empty": "未命名的快速订单模板",
	"ordertemplate.template.grid.button.buy": "购买",
	"ordertemplate.template.grid.button.custom": "自定义VM配置",
	
	"ordertemplate.template.naming.vm.tips": "（只允许输入英文字母, 数字或下划线, 数字不能作为开头）",
	
	"ordertemplate.template.naming.volume.tips": "（只允许输入英文字母, 数字或下划线, 数字不能作为开头）",
	
	"ordertemplate.template.dialog.confirm.title": "订单",
	"ordertemplate.template.dialog.confirm.software": "镜像",
	"ordertemplate.template.dialog.confirm.vmname": "主机名",
	"ordertemplate.template.dialog.confirm.datacenter": "数据中心",
	"ordertemplate.template.dialog.confirm.dcpu": "默认CPU",
	"ordertemplate.template.dialog.confirm.mcpu": "最大CPU",
	"ordertemplate.template.dialog.confirm.dmem": "默认内存",
	"ordertemplate.template.dialog.confirm.mmem": "最大内存",
	"ordertemplate.template.dialog.confirm.plan": "付费套餐",
	"ordertemplate.template.dialog.confirm.price": "金额",
	"ordertemplate.template.dialog.confirm.cpuprice": "CPU价格",
	"ordertemplate.template.dialog.confirm.memprice": "内存价格",
	"ordertemplate.template.dialog.confirm.netprice": "网络流量价格",
	"ordertemplate.template.dialog.confirm.hasnetwork": "是否购买公网IP",
	"ordertemplate.template.dialog.confirm.networktype": "网络类型",
	"ordertemplate.template.dialog.confirm.hasvolume": "是否购买扩展磁盘",
	"ordertemplate.template.dialog.confirm.volumename": "扩展磁盘名",
	"ordertemplate.template.dialog.confirm.volumesize": "磁盘大小",
	"ordertemplate.template.dialog.confirm.fee.vm": "主机价格",
	"ordertemplate.template.dialog.confirm.fee.network": "IP价格",
	"ordertemplate.template.dialog.confirm.fee.volume": "磁盘价格",
	"ordertemplate.template.dialog.confirm.fee.ordercount": "订单数量",
	"ordertemplate.template.dialog.confirm.fee.total": "金额总计",
	"ordertemplate.template.dialog.confirm.subtitle.software": "基础配置",
	"ordertemplate.template.dialog.confirm.subtitle.hardware": "硬件配置",
	"ordertemplate.template.dialog.confirm.subtitle.plan": "付费套餐",
	"ordertemplate.template.dialog.confirm.subtitle.network": "网络配置",
	"ordertemplate.template.dialog.confirm.subtitle.volume": "扩展磁盘配置",
	"ordertemplate.template.dialog.confirm.subtitle.total": "订单合计",
	
	"ordertemplate.label.fee.network": "（IP价格： %s 元/30天）",
	"ordertemplate.label.fee.volume": "（磁盘价格： %s 元/GB/天）",

	"ordertemplate.label.tips.vmname.invalid": "该VM名不合法，请使用字母，数字或下划线组合。且数字不能作为开头，总字符数不超过8个。",
	"ordertemplate.label.tips.vmname.unavailable": "该VM名不可用，请重新输入。",
	"ordertemplate.label.tips.network.vacancy": "若需要使用网络设置，请填写网络的相关信息。",
	"ordertemplate.label.tips.volume.vacancy": "若需要使用扩展磁盘，请填写扩展磁盘的相关信息。",
	"ordertemplate.label.tips.volume.name.invalid": "该扩展磁盘名不合法，请使用字母，数字或下划线组合。且数字不能作为开头，总字符数不超过10个。",
	"ordertemplate.label.tips.datacenter.unavailable": "数据中心不可用，请刷新页面或联系系统管理员。",
	
	"ordertemplate.label.boolean.true": "是",
	"ordertemplate.label.boolean.false": "否",
	"ordertemplate.label.network.public": "发布网站",
	"ordertemplate.label.network.private": "不发布网站",
	
	"ordertemplate.button.order.submit": "提交订单",
	
	"ordertemplate.page.message.buy.prefix": "您的订单：",
	"ordertemplate.page.message.buy.prefix.inst": "云主机：",
	"ordertemplate.page.message.buy.NO_ENOUGH_STOCK": "对不起，系统无足够库存，请稍候再试",
	"ordertemplate.page.message.buy.VM_EXCEEDED": "对不起，系统VM数已达许可证上限，请联系管理员",
	"ordertemplate.page.message.buy.NOIDORDER": "对不起，无法下单，请稍后再试",
	"ordertemplate.page.message.buy.ERROR": "对不起，无法下单，请稍后再试",
	"ordertemplate.page.message.buy.EXCEPTION": "对不起，服务器异常，请稍候再试",
	"ordertemplate.page.message.buy.LOWBALANCE": "对不起，您的余额已不足，请先充值",
	"ordertemplate.page.message.buy.inst.NO_ENOUGH_STOCK": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"ordertemplate.page.message.buy.inst.EXCEPTION": "对不起，服务器可能在维护，您的云主机购买操作未完成",
	"ordertemplate.page.message.buy.inst.NAMENOTAVAILABLE": "对不起，您的云主机名字已被占用，您的云主机购买操作未完成",
	"ordertemplate.page.message.buy.inst.OSTYPEERROR": "对不起，系统信息错误，请立即联系管理员",
	"ordertemplate.page.message.buy.inst.ORDERED": "云主机申请成功，请等待管理员审核",
	"ordertemplate.page.message.buy.inst.CREATED": "云主机购买提交成功，请等待Email确认，邮件确认后您可以到 “%s” 中操作您的云主机",
	"ordertemplate.page.message.buy.inst.CREATED.keyword": "我的云产品",
	"ordertemplate.page.message.buy.inst.LOWBALANCE": "对不起，由于您的余额不足，您的云主机购买操作未完成",
	"ordertemplate.page.message.buy.inst.ONEVMONLY": "对不起，您已不能申请更多的云主机实例",
	"ordertemplate.page.message.buy.inst.DONE": "云主机购买提交成功，成功 %s 个，失败 %s 个",
	"ordertemplate.page.message.buy.inst.UNKNOWN_ERROR": "未知错误，请立即联系管理员",
	"ordertemplate.page.message.buy.ip.NO_ENOUGH_STOCK": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"ordertemplate.page.message.buy.ip.EXCEPTION": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"ordertemplate.page.message.buy.ip.NOIP": "IP地址：对不起，您所选择的数据中心已无可用IP，您的IP购买操作未完成，请与管理员联系",
	"ordertemplate.page.message.buy.ip.WEB": "IP地址：提交成功，如您需要发布网站，请提供您的备案信息",
	"ordertemplate.page.message.buy.ip.LOWBALANCE": "IP地址：对不起，由于您的余额已不足，您的IP购买操作未完成",
	"ordertemplate.page.message.buy.ip.PERSONAL": "IP地址：购买成功，您可以到 “%s” 中操作您的IP",
	"ordertemplate.page.message.buy.ip.PERSONAL.keyword": "我的云产品",
	"ordertemplate.page.message.buy.ip.BATCH": "IP地址：提交成功 %s 个，失败 %s 个",
	"ordertemplate.page.message.buy.ip.UNKNOWN_ERROR": "IP地址：未知错误，请立即联系管理员",
	"ordertemplate.page.message.buy.volume.NO_ENOUGH_STOCK": "对不起，系统暂无足够库存，请联系管理员或稍候再试",
	"ordertemplate.page.message.buy.volume.EXCEPTION": "磁盘：对不起，服务器可能在维护，您的磁盘购买操作未完成",
	"ordertemplate.page.message.buy.volume.LOWBALANCE": "磁盘：对不起，由于您的余额已不足，您的磁盘购买操作未完成",
	"ordertemplate.page.message.buy.volume.DONE": "磁盘：购买成功，您可以到 “%s” 中操作您的磁盘",
	"ordertemplate.page.message.buy.volume.DONE.keyword": "我的云产品",
	"ordertemplate.page.message.buy.volume.BATCH": "磁盘：购买成功 %s 个，失败 %s 个",
	"ordertemplate.page.message.buy.volume.UNKNOWN_ERROR": "磁盘：未知错误，请立即联系管理员",
	
	"ordertemplate.page.message.save.success": "保存完成。",
	"ordertemplate.page.message.save.failure": "保存失败，请重试。"


	// please customize your locale string in file "extension/zh_CN.ex"
	//<!--#include virtual="extension/zh_CN.ex"-->
}


var Resource={
}




