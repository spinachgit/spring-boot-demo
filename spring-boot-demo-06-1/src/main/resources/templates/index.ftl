<!DOCTYPE html>
<html>
<head lang="en">
<title>Spring Boot Demo - FreeMarker</title>
<link href="/css/index.css" rel="stylesheet" />
<style type="text/css">
*{margin: 1px;}
span {
	width:50px;
}
input,textarea {
	width:900px;
}
textarea{
	height: 100px;
}
</style>
</head>
<body>
	<center>
	<h1 id="title">${title}</h1>
		<span>JS:</span><textarea id="js_content" placeholder="请输入JS对应的内容">
var url = "http://localhost:8080/rest/update";
var data={
  'name':'spinach',
  'sex':'男',
  'age':11
}
$.post(url,data,function(body,status){
	console.log(body);
	alert("数据: \n" + body + "\n状态: " + status);
});
		</textarea><br/>
		
		<hr />
		<button id="button_content">根据内容提交</button>
		<hr />
<span>url:</span><input type="url" id="js_url" placeholder="url" value="http://localhost:8080/web/index"/><br/>
<span>data:</span><textarea id="js_data" placeholder="json字符串">
{	
	"name":"spinach"
}
</textarea><br/>
		<hr />
		<button id="button_get" submit_type="get">发送一个 HTTP GET 请求并获取返回结果</button>
		<button id="button_post" submit_type="post">发送一个 HTTP post 请求并获取返回结果</button>
		<hr />
	</center>
	<script type="text/javascript" src="/webjars/jquery/2.1.4/jquery.min.js"></script>

	<script>
		$(document).ready(function() {
			$("#button_content").click(function() {
				var js_content = $("#js_content").val().trim();
				console.log(js_content);
			    eval(js_content);
			});
			
			$("#button_get,#button_post").click(function() {
				var type=$(this).attr("submit_type");
				var url = $("#js_url").val();
				var dataStr =  $("#js_data").val();
				var data=JSON.parse(dataStr);
				if("get" == type){
					$.get(url, data,function(body, status) {
						console.log(body);
					});
				}else{
					$.post(url, data,function(body, status) {
						console.log(body);
					});
				}
			});
		});
	</script>
</body>
</html>