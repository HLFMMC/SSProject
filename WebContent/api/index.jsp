<%@ page contentType="text/html; charset=UTF-8"%>

<title>API</title>


<meta name="viewport" content="width=device-width, initial-scale=1">


<script src="http://cdn.bootcss.com/jquery/1.12.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.css"
	rel="stylesheet">


<style type="text/css">
body {
	font-family: Verdana, Consolas, Cambria, Courier;
}

textarea {
	font-family: Consolas, Verdana, Cambria, Courier;
}

pre {
	font-family: Consolas, Verdana, Cambria, Courier;
	border-width: 0px;
}
ul>li {
	font-size: 12px;
	color: blue;
}
</style>

<script>
	var requestDate;

	function clearMessage() {

		$("#responseMessage").html("");

	}

	function showAction(obj) {

		$("#actionInput").val(JSON.stringify(obj, null, 4));

	}

	function submitAction() {

		var actionMsg = $("#actionInput").val();
		requestDate = new Date();
		var jsonData = JSON.parse(actionMsg);

		$.ajax({

			url : "../webAction",
			type : 'POST',
			dataType : 'text',
			data : jsonData,
			success : function(data) {
				var jData = JSON.parse(data);
				var str = JSON.stringify(JSON.parse(data), null, 4);
				appendMessage(jData.ServerCode, str);

			}
		});

	}

	//导入api菜单
	function loadApiMenu(){
		$.ajax({   
		    url: 'api_admin_menu.html',
		    type: 'GET',
		    dataType: 'text',
		    async: false,
		    success: function (data){ 	               	
		    	$("#apiMenu").html(data);
		 	 }
	     });
	}

	$(document).ready(function() {
		loadApiMenu();
	});

	function init() {

	}

	function appendMessage(title, msg) {
		var date0 = new Date();
		var costSec = (date0.getTime() - requestDate.getTime()) / 1000;
		var outStr = "";
		outStr += "<div  class='panel panel-default area'>";
		outStr += "<div  class='panel-heading'>";
		outStr += "<span class='text-left'>" + title + "  (" + costSec + " sec)</span>";
		outStr += "</div>";
		outStr += "<div class='panel-body '>";
		outStr += "<pre>" + msg + "</pre>";
		outStr += "</div>";
		outStr += "</div>";

		$('#responseMessage').prepend(outStr);
		$('.area:eq(0)').hide();
		$('.area:eq(0)').slideDown();
	}
	
	
	
	/* managerLogin */
	function userLogin() {
		var jsonData = {
			"serverCode" : "userLogin",
			"userName" : "Lucas",
			"password" : "123qwe"
		}
		showAction(jsonData);
	}
	// 登录者信息
	function userInfo() {
		var jsonData = {
			"serverCode" : "userInfo"
		}
		showAction(jsonData);
	}
	// 用户注册
	function userRegister() {
		var jsonData = {
			"serverCode" : "userRegister",
			"userName" : "",
			"password" : ""
		}
		showAction(jsonData);
	}
	// 登出
	function userLogout() {
		var jsonData = {
			"serverCode" : "userLogout"
		}
		showAction(jsonData);
	}
	// 修改个人信息
	function userUpdate() {
		var jsonData = {
			"serverCode" : "userUpdate",
			"phone" : "",
			"pic" : "",
			"nikeName":"",
			"email" :"",
			"sex" : ""
		}
		showAction(jsonData);
	}
	// 修改个人信息
	function videoList() {
		var jsonData = {
			"serverCode" : "videoList",
			"videoTypeId" : ""
		}
		showAction(jsonData);
	}
	// 我的视频列表
	function myVideoList() {
		var jsonData = {
			"serverCode" : "myVideoList"
		}
		showAction(jsonData);
	}
	
	// 保存视频
	function saveVideoUpload() {
		var jsonData = {
			"serverCode" : "saveVideoUpload",
			"videoName" : "",
			"videoDesc" :"",
			"pic" :"",
			"address" : "",
			"videoTypeId" : ""
		}
		showAction(jsonData);
	}
	
	/*上传视频*/
	function uploadVideo() {
		var jsonData = {
			"serverCode" : "uploadVideo",
			"base64" : ""
		}
		showAction(jsonData);
	}
	// 上传封面
	function uploadVideoPic() {
		var jsonData = {
			"serverCode" : "uploadVideoPic",
			"base64" : ""
		}
		showAction(jsonData);
	}
	
	// 上传头像
	function uploadUserPic() {
		var jsonData = {
			"serverCode" : "uploadUserPic",
			"base64" : ""
		}
		showAction(jsonData);
	}
	
	//我曾观看
	function myViewVideoList() {
		var jsonData = {
			"serverCode" : "myViewVideoList"
		}
		showAction(jsonData);
	}
	// 新增或更新观看记录
	function myViewVideoAdd() {
		var jsonData = {
			"serverCode" : "myViewVideoAdd",
			"videoId" : ""
		}
		showAction(jsonData);
	}
	
	// 我的收藏
	function myCollectVideoList() {
		var jsonData = {
			"serverCode" : "myCollectVideoList"
		}
		showAction(jsonData);
	}
	// 我的收藏 - 新增
	function myCollectVideoAdd() {
		var jsonData = {
			"serverCode" : "myCollectVideoAdd",
			"videoId" :""
		}
		showAction(jsonData);
	}
	// 我的收藏
	function myCollectVideoDelete() {
		var jsonData = {
			"serverCode" : "myCollectVideoDelete",
			"videoId" :""
		}
		showAction(jsonData);
	}
	// 发布悬赏
	function rewardAdd() {
		var jsonData = {
			"serverCode" : "rewardAdd",
			"content" : "", 
			"money" : ""
		}
		showAction(jsonData);
	}
	// 悬赏列表
	function rewardList() {
		var jsonData = {
			"serverCode" : "rewardList"
		}
		showAction(jsonData);
	}
	// 悬赏列表
	function MyRewardList() {
		var jsonData = {
			"serverCode" : "myRewardList"
		}
		showAction(jsonData);
	}
</script>

<div id="test"></div>
<div class="container-fluid"></div>

<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#" id="messages">API</a>
			<div id="messages"></div>
		</div>

		<div id="apiMenu"></div>

		<ul class="nav navbar-nav navbar-right">
			<li><a href="#"><span class="glyphicon glyphicon-member"></span>admin</a></li>
		</ul>
	</div>
</nav>
<hr>


<div class="container-fluid">
	<div class="col-md-0"></div>
	
	<div class="col-md-3">
		<div class="panel-group">
			<div class="panel panel-default">
			
			
				<div class="panel-heading">manager</div>
				<ul class="list-group">
					<li class="list-group-item"><a href="javascript:userLogin()">用户登录userLogin </a></li>
					<li class="list-group-item"><a href="javascript:userInfo()"> 登录者信息userInfo </a></li>
					<li class="list-group-item"><a href="javascript:userRegister()">用户注册userRegister </a></li>
					<li class="list-group-item"><a href="javascript:userLogout()">用户登出userLogout </a></li>
					<li class="list-group-item"><a href="javascript:userUpdate()">修改个人信息userUpdate </a></li>
					<li class="list-group-item"><a href="javascript:uploadUserPic()">上传头像uploadUserPic </a></li>
					<li class="list-group-item"><a href="javascript:myVideoList()">我的视频myVideoList </a></li>
					
				</ul>
				<div class="panel-heading">Video</div>
				<ul class="list-group">
					<li class="list-group-item"><a href="javascript:videoList()">视频列表videoList </a></li>
					<li class="list-group-item"><a href="javascript:saveVideoUpload()">保存视频saveVideoUpload </a></li>
					<li class="list-group-item"><a href="javascript:uploadVideoPic()">上传视频封面uploadVideoPic </a></li>
					<li class="list-group-item"><a href="javascript:uploadVideo()">上传视频uploadVideo </a></li>
					<li class="list-group-item"><a href="javascript:myViewVideoList()">观看记录列表myViewVideoList </a></li>
					<li class="list-group-item"><a href="javascript:myViewVideoAdd()">新增观看记录myViewVideoAdd </a></li>
					
				</ul>
				<div class="panel-heading">我的收藏</div>
				<ul class="list-group">
					<li class="list-group-item"><a href="javascript:myCollectVideoAdd()">新增收藏myCollectVideoAdd </a></li>
					<li class="list-group-item"><a href="javascript:myCollectVideoList()">我的收藏列表myCollectVideoList </a></li>
					<li class="list-group-item"><a href="javascript:myCollectVideoDelete()">删除我的收藏myCollectVideoDelete </a></li>
				</ul>
				
				<div class="panel-heading">悬赏相关</div>
				<ul class="list-group">
					<li class="list-group-item"><a href="javascript:rewardAdd()">发布悬赏rewardAdd </a></li>
					<li class="list-group-item"><a href="javascript:rewardList()">悬赏列表rewardList </a></li>
					<li class="list-group-item"><a href="javascript:MyRewardList()">我发布的悬赏列表MyRewardList </a></li>
				</ul>


			</div>
		</div>
	</div>
	
	<div class="col-md-9">
		<div class="form-group">
			<label>request</label>
			<textarea class="form-control" rows="8" id="actionInput"></textarea>
		</div>
		<div class="form-group">
			<button class="btn btn-default" onclick="submitAction()">request</button>
			<button class="btn btn-default" onclick="clearMessage()">clear</button>
		</div>
		<label for="exampleInputEmail1">response</label> <br>
		<div id="responseMessage" class="panel-group"></div>
	</div>
</div>
<footer class="container-fluid text-center">
	<p>
		<a href="javascript:refreshVerifyPic();"><img  class="control-label"  id="verifyPic"  src="../verifyCode" /></a><br> Copyright 2016
	</p>
</footer>
</body>
</body>
</html>
