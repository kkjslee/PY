/* 
    All copyleft under GNU GPLv3 below.
    Copyright (C) 2011  Puyun, Bill.Cao <gorebill@163.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 /*<!--#include virtual="../config.shtml" -->*/

var BootLoader={_HandBreak_:{debug:false},log:function(msg){window.console&&window.console.log("[BootLoader](Log): "+msg)},error:function(msg){window.console&&window.console.error("[BootLoader](Err): "+msg)},prepareScriptSerial:function(scripts,callback){if(scripts&&scripts.length>0){var s=scripts.shift();BootLoader.log("Loading script in serial mode - "+s);$.getScript(s).done(function(script,textStatus){BootLoader.prepareScriptSerial(scripts,callback)})}else{callback&&callback()}},prepareScriptParallel:function(scripts,callback){var jqXHRs=[];for(var i=0;scripts&&i<scripts.length;i++){jqXHRs.push($.getScript(scripts[i]))}$.when.apply($,jqXHRs).done(function(){callback&&callback()})},init:function(){BootLoader.check();if($.fn.dataTable){BootLoader.log("[+] DataTables is available. Ver-"+$.fn.dataTableExt.sVersion);$.fn.dataTableExt.sErrMode="throw";BootLoader.log("Try to load dataTable addons");$.fn.dataTableExt.oApi.fnGetColumnData=this.addons.dataTable.multipleFilter}if(window.Highcharts){BootLoader.log("[+] Highcharts is available.");Highcharts.setOptions({global:{useUTC:false}})}},conf:{dataTable:function(dataTable){var aoColumns=null;if(dataTable){aoColumns=[];var ths=$(dataTable).find("thead tr").first().find("th");for(var i=0;i<ths.length;i++){$(ths[i]).attr("visible")=="false"?aoColumns.push({"bVisible":false}):aoColumns.push({"bVisible":true})}}return{"fnInitComplete":function(oSettings,json){$("#dataTable").find("#pager").removeAttr("id").appendTo($("#dataTable tfoot").find("td:first-child").empty())},"aLengthMenu":(window.JSON&&JSON.parse("<!--#echo var='DataTableLength'-->"))||null,"iDisplayLength":(window.JSON&&JSON.parse("<!--#echo var='DataTableLength'-->")[0])||null,"aoColumns":aoColumns||null,"bAutoWidth":false,"sPaginationType":"full_numbers","oLanguage":(Locale&&Locale.dataTable)||null,"sDom":"<'#dataTable't<'#pager'lpi>>"}}},cache:{},check:function(){this._HandBreak_.debug&&$(document.head).find("link,script").each(function(){var src=$(this).attr("href")||$(this).attr("src")||"";src&&$.ajax({url:src,type:"head",cache:false,success:function(){BootLoader.log("Check ("+src+") passed.")},error:function(){BootLoader.error("Check ("+src+") failed!")}})})},addons:{dataTable:{multipleFilter:{fnGetColumnData:function(oSettings,iColumn,bUnique,bFiltered,bIgnoreEmpty){if(typeof iColumn=="undefined")return new Array();if(typeof bUnique=="undefined")bUnique=true;if(typeof bFiltered=="undefined")bFiltered=true;if(typeof bIgnoreEmpty=="undefined")bIgnoreEmpty=true;var aiRows;if(bFiltered==true)aiRows=oSettings.aiDisplay;else aiRows=oSettings.aiDisplayMaster;var asResultData=new Array();for(var i=0,c=aiRows.length;i<c;i++){iRow=aiRows[i];var aData=this.fnGetData(iRow);var sValue=aData[iColumn];if(bIgnoreEmpty==true&&sValue.length==0)continue;else if(bUnique==true&&jQuery.inArray(sValue,asResultData)>-1)continue;else asResultData.push(sValue)}return asResultData}}}}};

