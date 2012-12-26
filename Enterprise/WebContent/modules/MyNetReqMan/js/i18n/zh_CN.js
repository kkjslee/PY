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
	
	/* ----Module My IP Request Management---- */
	"myreq.banner" : "我的IP请求",
	"myreq.dialog.close" : "关闭",
	"myreq.dialog.processing" : "处理中, 请稍候...",
	"myreq.dialog.title.tips" : "提示信息",
	"myreq.message.loading" : "数据加载中...",
	"myreq.message.error" : "出现异常, 请联系管理员",
	"myreq.message.undefined" : "未定义的返回 [%s]",
	"myreq.message.no_data": "无该模块数据",
	"myreq.template.tips" : "1. 下表中数据是您的IP请求状况，若发现存在异常情况，请联系管理员。<br/>2. 已被否决的IP请求将不再列出。",
	"myreq.template.myreq.name": "所在域",
	"myreq.template.myreq.ip": "IP",
	"myreq.template.myreq.status": "状态",
	"myreq.template.myreq.operation": "操作",
	"myreq.template.myreq.refresh": "刷新",
	"myreq.template.myreq.remove": "删除",
	
	"myreq.template.label.status.approved": "已审核",
	"myreq.template.label.status.pending": "待审核",
	"myreq.template.label.status.rejected": "已否决",
	
	"myreq.template.label.foo": "**"




	// please customize your locale string in file "extension/zh_CN.ex"
	//<!--#include virtual="extension/zh_CN.ex"-->
}


var Resource={
}




