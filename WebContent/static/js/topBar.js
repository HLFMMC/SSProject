
function setTopBar() {


	$.ajax({

		url : "topBar.html",
		type : 'Get',
		dataType : 'text',
		async: false,
		success : function(data) {
			$("#topBar").html(data)
		}
	});
	userInfoSet();
}
function userInfoSet() {
	var jsonData = {
		"serverCode": "userInfo"
	};
	$.ajax({
		url : "webAction",
		type : 'POST',
		dataType : 'text',
		data:jsonData,
		success : function(data) {
			var jData = JSON.parse(data);
			var serverMsg = jData.serverMsg;
			if (serverMsg=="success"){
				$("#login").css("display","none");
				$("#register").css("display","none");
				$("#userInfo").css("display","").children().html(jData.data[0].userName);
				
			} else {
				$("#login").css("display","");
				$("#register").css("display","");
				$("#userInfo").css("display","none");
			}
		}
	});
}