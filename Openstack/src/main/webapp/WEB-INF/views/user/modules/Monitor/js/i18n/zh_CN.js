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
	"monitor.banner" : "监视器管理",
	"monitor.dialog.close" : "关闭",
	"monitor.dialog.create" : "创建",
	"monitor.dialog.modify" : "修改",
	"monitor.dialog.processing" : "处理中, 请稍候...",
	"monitor.dialog.title.tips" : "提示信息",
	"monitor.dialog.title.create" : "创建监视器",
	"monitor.dialog.title.update" : "修改监视器",
	"monitor.confirm.create" : "要创建监视器吗？",
	"monitor.confirm.remove" : "要删除监视器 [%s] 吗？",
	"monitor.confirm.update" : "要 %s 监视器 [%s] 吗？",
	"monitor.confirm.modify" : "要修改监视器的设置吗？",
	"monitor.message.type.in.monitor.name" : "请填写恰当的监视器名称，名称必须由英文字母、数字或下划线组成，且数字不能作为开头。名称必须不多于20个字符。",
	"monitor.message.type.in.monitor.ip" : "请选择有效的IP。",
	"monitor.message.loading" : "数据加载中...",
	"monitor.message.create.done" : "监视器创建成功",
	"monitor.message.remove.done" : "监视器删除成功",
	"monitor.message.update.done" : "监视器修改成功",
	"monitor.message.modify.done" : "监视器设置修改成功",
	"monitor.message.error" : "出现异常, 请联系管理员",
	"monitor.message.undefined" : "未定义的返回 [%s]",
	"monitor.message.no_data": "无该模块数据",
	"monitor.template.tips" : "1. 监视器名只能由英文字母，数字或下划线构成。且监视IP必须已与VM关联。<br/>2. 创建监视器后，监视器需要一段时间进行数据收集。<br/>3. 分离IP时，监视器历史记录也将同时被移除。",
	"monitor.template.monitor.name": "监视器名",
	"monitor.template.monitor.ip": "监视IP",
	"monitor.template.monitor.frequency": "监视频率",
	"monitor.template.monitor.status": "状态",
	"monitor.template.monitor.operation": "操作",
	"monitor.template.monitor.refresh": "刷新",
	"monitor.template.monitor.create": "新建",
	"monitor.template.monitor.remove": "删除",
	"monitor.template.monitor.modify": "修改",
	"monitor.template.monitor.pause": "暂停",
	"monitor.template.monitor.resume": "恢复",
	"monitor.template.tabs.history": "可用性历史",
	"monitor.template.tabs.summary": "可用性概览",
	"monitor.template.tabs.logging": "异常记录",
	"monitor.template.tabs.empty": "选择要查看的项目",
	"monitor.template.label.summary.avail": "有效",
	"monitor.template.label.summary.down": "失效",
	"monitor.template.label.down": "失效时间",
	"monitor.template.label.back": "恢复时间",
	"monitor.template.label.interval": "持续时间",
	"monitor.template.label.interval.second": "秒",
	"monitor.template.label.interval.minute": "分钟",
	"monitor.template.label.interval.hour": "小时",
	"monitor.template.label.availability": "可用性",
	"monitor.template.label.status.monitoring": "监视中",
	"monitor.template.label.status.pause": "暂停",
	"monitor.template.label.noavailableip": "无可用IP",
	"monitor.template.label.foo": "**"




	// please customize your locale string in file "extension/zh_CN.ex"
	//<!--#include virtual="extension/zh_CN.ex"-->
}


var Resource={
}




