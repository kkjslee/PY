var Server="";
//this should be called first in jsp file
function setServer(server){
	Server = server;
}
$(function(){
	$.ajax({
        type: "POST",
        dataType: "html",
        cache: false,
        url: Server + "/getPagerNetworkList",  
        data: {
            pageIndex: pageIndex,
            pageSize: pageSize
        },
        success: function(data) {
             $(".dataTable").html(data);
        },
        error: function(jqXHR, textStatus, errorThrown) {
            $("<span class='loadingError'>error</span>").appendTo($(".dataTable").empty());
        }
    });
});
