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

var BootLoader = {
	_HandBreak_: {
		debug: false
	},
	log: function(msg) {
	},
	error: function(msg) {
	},
	/*
	 * Function: prepareScriptSerial
	 * Purpose:  Prepare scripts in serial mode. Usually be used for scripts with dependency.
	 * Returns:  none
	 * Inputs:   Array[String]:scripts - scripts URI going to load in order.
	 *           Function:callback - callback when scripts have been loaded
	 * Author:   Bill <gorebill@163.com>
	 */
	prepareScriptSerial: function (scripts, callback) {
		if(scripts && scripts.length>0){
			var s=scripts.shift();
			
			BootLoader.log("Loading script in serial mode - "+s);
			
			$.getScript(s).done(function(script, textStatus){
				BootLoader.prepareScriptSerial(scripts, callback);
			});
		}else {
			callback && callback();
		}
	},
	/*
	 * Function: prepareScriptParallel
	 * Purpose:  Prepare scripts in parallel mode.  Usually be used for scripts without dependency or stand alone.
	 * Returns:  none
	 * Inputs:   Array[String]:scripts - scripts URI going to load
	 *           Function:callback - callback when scripts have been loaded
	 * Author:   Bill <gorebill@163.com>
	 */
	prepareScriptParallel: function (scripts, callback) {
		var jqXHRs=[];
		for(var i=0; scripts && i<scripts.length; i++) {
			jqXHRs.push($.getScript(scripts[i]));
		}
		
		$.when.apply($, jqXHRs).done(function(){callback && callback();});
	},
	init: function() {
		BootLoader.check();
		
		// set up data table
		if($.fn.dataTable){
			BootLoader.log("[+] DataTables is available. Ver-"+$.fn.dataTableExt.sVersion);
			
			$.fn.dataTableExt.sErrMode="throw";
			
			BootLoader.log("Try to load dataTable addons");
			$.fn.dataTableExt.oApi.fnGetColumnData=this.addons.dataTable.multipleFilter;
		}
		
		// set up highcharts
		if(window.Highcharts) {
			BootLoader.log("[+] Highcharts is available.");
			Highcharts.setOptions({
		        global: {
		            useUTC: false
		        }
		    });
		}
	},
	conf:{
		/*
		 * Parameter dataTable is optional. It will construct parameters for addon-dataTable while exists.
		 */
		dataTable: function(dataTable) {
			
			var aoColumns=null;
			if(dataTable) {
				aoColumns=[];
				var ths=$(dataTable).find("thead tr").first().find("th");
				for(var i=0; i<ths.length; i++) {
					$(ths[i]).attr("visible")=="false"?aoColumns.push({"bVisible":false}):aoColumns.push({"bVisible":true});
				}
			}
			
			return	{
				"fnInitComplete": function(oSettings, json){
					$("#dataTable").find("#pager").removeAttr("id").appendTo($("#dataTable tfoot").find("td:first-child").empty());
				},
				"aLengthMenu": (window.JSON && JSON.parse("[ 10, 25, 50, 100 ]")) || null, // set entries length option if available
				"iDisplayLength": (window.JSON && JSON.parse("[ 10, 25, 50, 100 ]")[0]) || null, // set items[0] as the default
				"aoColumns": aoColumns || null,
				"bAutoWidth": false,
				"sPaginationType": "full_numbers",
				"oLanguage": (Locale && Locale.dataTable) || null,
				"sDom": "<'#dataTable't<'#pager'lpi>>"
			};
		}
	},
	cache: {/*empty for cache usage*/},
	check: function() {
		this._HandBreak_.debug && $(document.head).find("link,script").each(function(){
			var src=$(this).attr("href") || $(this).attr("src") || "";
			src && $.ajax({url:src,type:"head",cache:false,
				success:function(){BootLoader.log(
					"Check ("+src+") passed."
				);},
				error:function(){BootLoader.error(
					"Check ("+src+") failed!"
				);}
			});
		});
	},
	addons: {
		dataTable: {
			multipleFilter: {
				/*
				 * Function: fnGetColumnData
				 * Purpose:  Return an array of table values from a particular column.
				 * Returns:  array string: 1d data array 
				 * Inputs:   object:oSettings - dataTable settings object. This is always the last argument past to the function
				 *           int:iColumn - the id of the column to extract the data from
				 *           bool:bUnique - optional - if set to false duplicated values are not filtered out
				 *           bool:bFiltered - optional - if set to false all the table data is used (not only the filtered)
				 *           bool:bIgnoreEmpty - optional - if set to false empty values are not filtered from the result array
				 * Author:   Benedikt Forchhammer <b.forchhammer /AT\ mind2.de>
				 */
				fnGetColumnData: function ( oSettings, iColumn, bUnique, bFiltered, bIgnoreEmpty ) {
				    // check that we have a column id
				    if ( typeof iColumn == "undefined" ) return new Array();
				     
				    // by default we only wany unique data
				    if ( typeof bUnique == "undefined" ) bUnique = true;
				     
				    // by default we do want to only look at filtered data
				    if ( typeof bFiltered == "undefined" ) bFiltered = true;
				     
				    // by default we do not wany to include empty values
				    if ( typeof bIgnoreEmpty == "undefined" ) bIgnoreEmpty = true;
				     
				    // list of rows which we're going to loop through
				    var aiRows;
				     
				    // use only filtered rows
				    if (bFiltered == true) aiRows = oSettings.aiDisplay; 
				    // use all rows
				    else aiRows = oSettings.aiDisplayMaster; // all row numbers
				 
				    // set up data array    
				    var asResultData = new Array();
				     
				    for (var i=0,c=aiRows.length; i<c; i++) {
				        iRow = aiRows[i];
				        var aData = this.fnGetData(iRow);
				        var sValue = aData[iColumn];
				         
				        // ignore empty values?
				        if (bIgnoreEmpty == true && sValue.length == 0) continue;
				 
				        // ignore unique values?
				        else if (bUnique == true && jQuery.inArray(sValue, asResultData) > -1) continue;
				         
				        // else push the value onto the result data array
				        else asResultData.push(sValue);
				    }
				     
				    return asResultData;
				}
			}
		}
	}
};


