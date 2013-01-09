// JavaScript Document
// Author: Bill, 2011
var Locale={
	/*-- Global --*/
	"global.processing": "Processing...",

	/* ----Module Navigation---- */
	"navigation.menu.admin.index" : "Admin Index",
	"navigation.menu.admin.stock" : "Stock",
	"navigation.menu.admin.stock.vm" : "VM",
	"navigation.menu.admin.stock.volume" : "Volume",
	"navigation.menu.admin.apptemplate" : "Template",
	"navigation.menu.admin.apptemplate.global" : "Global",
	"navigation.menu.admin.project" : "Project",
	"navigation.menu.admin.project.golbal" : "Global",
	"navigation.menu.admin.parameters" : "Parameters",
	"navigation.menu.admin.parameters.global" : "Global",
	"navigation.menu.admin.parameters.license" : "License",
	
	/* ----Module Entry---- */
	"entry.label.title": "Administrator Console",
	"entry.label.welcome": "Welcome Administrator Console!",

	/* ----Module App Template---- */
	"template.title.manage": "应用模板管理",
	"template.title.verify": "应用模板审核",
	"template.confirm.verify": "要通过该请求吗？",
	"template.confirm.refuse": "要否决改请求吗？",
	"template.confirm.remove": "要删除模板 '%s' 吗?",
	"template.dialog.title.new": "创建新模板",
	"template.dialog.title.update": "修改模板",
	"template.dialog.title.tips": "提示信息",
	"template.dialog.button.ok": "确定",
	"template.dialog.button.cancel": "取消",
	"template.dialog.button.close": "关闭",
	"template.message.illegal.name.empty": "模板名不能为空",
	"template.message.loading": "载入数据中...",
	"template.message.no_data": "未加载数据",
	"template.message.empty": "不存在数据",
	"template.message.verify.success": "审核成功, 该请求已进入服务器任务队列",
	"template.message.verify.failure.stock": "失败了: %s, 库存不足以满足请求，请检查VM库存",
	"template.message.verify.failure.exception": "失败了: %s, 服务出现异常，请检查服务器状态",
	"template.message.refuse.success": "否决成功",
	"template.message.refuse.failure": "失败了: %s",
	"template.message.add.success": "添加成功",
	"template.message.add.failure": "失败了: %s",
	"template.message.update.success": "添加成功",
	"template.message.update.failure": "失败了: %s",
	"template.message.remove.success": "删除成功",
	"template.message.remove.failure": "失败了: %s",
	"template.apply.status.unapproved": "待审核",
	"template.apply.status.proved": "已审核",
	"template.apply.status.rejected": "已否决",
	"template.verify.tab.unapproved": "待审核",
	"template.verify.tab.proved": "已审核",
	"template.verify.tab.rejected": "已否决",
	"template.option.label.zone": "所在域:",
	"template.option.choose.zone": "--选择域--",
	"template.manage.template.label.temp.zone": "所在域:",
	"template.manage.template.label.temp.id": "模板ID:",
	"template.manage.template.label.temp.name": "模板名:",
	"template.manage.template.label.temp.desc": "模板描述:",
	"template.manage.template.label.temp.notes": "模板注释:",
	"template.manage.template.label.temp.inst": "模板应用:",
	"template.manage.template.label.soft.name": "软件名:",
	"template.manage.template.label.soft.id": "软件ID:",
	"template.manage.template.label.cpu": "CPU:",
	"template.manage.template.label.cpu.max": "最大CPU:",
	"template.manage.template.label.mem": "内存:",
	"template.manage.template.label.mem.max": "最大内存:",
	"template.manage.template.column.examTempName": "模板名",
	"template.manage.template.column.examTempUser": "用户",
	"template.manage.template.column.examReqName": "请求应用名",
	"template.manage.template.column.examTempRes": "资源配置",
	"template.manage.template.column.examStatus": "状态",
	"template.manage.template.column.examSubTime": "提交日期",
	"template.manage.template.column.examAdminActTime": "处理日期",
	"template.manage.button.remove": "删除",
	"template.manage.button.update": "修改",
	"template.manage.button.new": "创建新模板",
	"template.verify.button.buttonApproved": "通过",
	"template.verify.button.buttonRejected": "否决",
	"template.button.refreshBtn": "刷新",
	"template.button.manageBtn": "应用模板管理",
	"template.button.examineBtn": "应用模板审核",

	/* ----Module Project---- */
	"project.dialog.button.ok": "确定",
	"project.dialog.button.cancel": "取消",
	"project.dialog.button.close": "关闭",
	"project.dialog.title.tips": "提示信息",
	"project.title.management": "项目管理",
	"project.confirm.accept": "要通过该请求吗？",
	"project.confirm.reject": "要否决改请求吗？",
	"project.confirm.remove": "要删除模板 '%s' 吗?",
	"project.message.loading": "载入数据中...",
	"project.message.empty": "不存在数据",
	"project.message.verify.success": "审核成功, 该请求已进入服务器任务队列",
	"project.message.verify.failure.stock": "失败了: %s, 库存不足以满足请求，请检查VM库存",
	"project.message.verify.failure.exception": "失败了: %s, 服务出现异常，请检查服务器状态",
	"project.message.refuse.success": "否决成功",
	"project.message.refuse.failure": "失败了: %s",
	"project.apply.status.unapproved": "待审核",
	"project.apply.status.proved": "已审核",
	"project.apply.status.rejected": "已否决",
	"project.apply.tab.unapproved": "待审核",
	"project.apply.tab.proved": "已审核",
	"project.apply.tab.rejected": "已否决",
	"project.manage.template.button.detail": "详细",
	"project.manage.template.button.accept": "通过",
	"project.manage.template.button.reject": "否决",
	"project.manage.template.column.project.examTempName": "项目名",
	"project.manage.template.column.project.examTempUser": "用户",
	"project.manage.template.column.project.examReqName": "项目描述",
	"project.manage.template.column.project.examTempRes": "项目配置",
	"project.manage.template.column.project.examDuration": "项目时间",
	"project.manage.template.column.project.examStatus": "状态",
	"project.manage.template.column.project.examSubTime": "提交日期",
	"project.manage.template.column.project.examAdminActTime": "处理日期",
	"project.manage.template.label.no_res": "该VM不存在资源配置信息",
	"project.manage.template.label.soft.id": "软件ID:",
	"project.manage.template.label.soft.name": "软件名:",
	"project.manage.template.label.soft.cpu": "CPU:",
	"project.manage.template.label.soft.mem": "内存:",
	"project.manage.template.label.soft.res": "VM配置 [前缀: %s 数量: %s]",
	"project.manage.template.label.network": "IP配置",
	"project.manage.template.label.network.web": "网站:",
	"project.manage.template.label.network.personal": "非网站:",
	"project.manage.template.label.volume": "扩展磁盘配置",
	"project.manage.template.label.volume.prefix": "磁盘前缀: %s",
	"project.manage.template.label.volume.amount": "%s 个 [前缀: %s]",
	"project.manage.template.label.unit": "个",

	/* ----Module Project---- */
	"license.confirm.update": "您确定要更新软件许可证吗?",
	"license.message.empty": "请填写完整的许可证信息",
	"license.message.framework.failure": "页面框架加载失败, 请重试或联系管理员",
	"license.message.submit.success": "更新成功",
	"license.message.submit.failure": "更新失败，请确认输入的许可证是否有效并重新提交",
	"license.message.submit.failure.undefined": "未知返回: %s",
	"license.status.invalid_private_key": "已过期或失效",
	"license.status.private_key_missing": "尚未激活",
	"license.status.ignore": "Public Key 异常",
	"license.module.groupmanagement": "用户组模块",
	"license.module.backup": "备份模块",
	"license.module.projectmanagement": "项目管理模块",
	"license.module.apptemplatemanagement": "应用模板模块",
	"license.module.ipmanagement": "IP模块",
	"license.module.volumemanagement": "扩展磁盘模块",
	"license.module.vmmanagement": "VM模块",
	"license.module.vlanmanagement": "VLAN模块",
	"license.module.inventorymanagement": "Inventory",
	"license.module.applicationpackagemanagement": "Application Package",
	"license.button.update": "更新许可证",
	"license.label.product.productid": "产品ID",
	"license.label.product.username": "授权用户",
	"license.label.product.duration": "授权时间",
	"license.label.product.components": "组件列表",
	"license.label.product.status": "产品键码",
	"license.label.product.newPrivateKey": "Private Key",
	"license.label.product.newLicense": "License",
	"license.label.tips": "填写新的Private Key以及License, 并在填写完毕后点击 [<u>更新许可证</u>] 按钮以更新您的产品许可证",

	/* ----Module Stock---- */
	"stock.confirm.volume.increase": "请输入要增加的Disk库存?",
	"stock.confirm.volume.decrease": "要减少1个该Disk的库存吗?",
	"stock.confirm.image.decrease": "要减少1个该VM的库存吗?",
	"stock.message.illegal.number": "请输入合法数字",
	"stock.message.input.stock": "请输入要增加的VM库存",
	"stock.table.column.image.id" : "Image ID",
	"stock.table.column.image.name" : "Image Name",
	"stock.table.column.image.amount" : "Image Amount",
	"stock.table.column.image.zone" : "Image Zone",
	"stock.table.column.image.operation" : "Operation",
	"stock.table.column.volume.size" : "磁盘大小",
	"stock.table.column.volume.amount" : "剩余数量",
	"stock.table.column.volume.operation" : "操作",
	"stock.table.cell.image.operation.increase" : "Increase",
	"stock.table.cell.image.operation.decrease" : "Decrease",
	"stock.button.volume.check" : "查询",
	"stock.button.volume.increase" : "增加",
	"stock.button.volume.decrease" : "减少",
	"stock.label.volume.sellstatus" : "销售状况",
	"stock.label.volume.startdate" : "开始日期:",
	"stock.label.volume.stopdate" : "终止日期:",
	"stock.label.volume.totalVolumeSize" : "扩展磁盘大小",
	"stock.label.volume.VMSize" : "虚拟机磁盘大小",
	"stock.label.volume.usedDiskSize" : "磁盘总空间",
	"stock.label.volume.totalFreeDiskSize" : "剩余磁盘空间"
	
	
}


var Resource={
}



