
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
	
}