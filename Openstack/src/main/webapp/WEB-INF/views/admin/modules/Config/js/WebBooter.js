// JavaScript Document
// Author: Bill, 2011~2012
var Booter={
	Pointer: 0,
	Scripts: [],
	init: function() {
		jQuery.extend({
			getCSS: function( url, callback ) {
				return jQuery.ajax({
					url: url,
					type: "POST",
					data:undefined, 
					success: function(data) {
						$(document).find("head").append("<style>"+data+"</style>");
						callback();
					}, 
					dataType: "text"
				});
			}
		});
		return this;
	},
	prepare: function(scriptsToLoad, callback) {
		this.Scripts=scriptsToLoad || this.Scripts;
		this.callback=callback || this.callback;
		
		if(this.Pointer<this.Scripts.length) {
			var scriptDocument=this.Scripts[this.Pointer++];
			
			if(/.*\.css$/i.test(scriptDocument)) {
				jQuery.getCSS(scriptDocument, this.chaining);
			}else{
				jQuery.getScript(scriptDocument, this.chaining);
			}
			
		}else if(this.callback){
			this.callback();
		}
		
		return this;
	},
	chaining: function() {
		Booter.prepare(); // important: it's a callback by jq, so 'this' not equals to Booter it self
		return this;
	},
	callback: null
};






