<!DOCTYPE html>
<html>
<head lang="en">
<title>Spring Boot Demo - FreeMarker</title>
<link href="css/index.css" rel="stylesheet" />
</head>
<body>
	<center>
		<h1 id="title">${title}</h1>
		<h1 id="name">${name}</h1>
		<h1 id="sessionId">${sessionId}</h1>
	</center>
	只输入数字和逗号的验证：
	<input type="text" onkeyup="this.value=this.value.replace(/[^\d,]/g,，'').replace(/,{2,}/g,',');" onblur="this.value=this.value.replace(/^,|,$/,'')" />
	<br />只输入数据、字母、逗号：
	<input type="text" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9,]/,，'').replace(/,{2,}/,',');" onblur="this.value=this.value.replace(/^,|,$/,'')" />
	<br />只输入汉字、数据、字母、逗号：
	<input type="text" onkeyup="this.value=this.value.replace(/[^ A-Za-z0-9,，\u4e00-\u9fa5]/g,'').replace(/,{2,}/g,',');" onblur="this.value=this.value.replace(/^,|,$/,'')" />

	<hr />
	websocket演示
	<br> 发送消息:
	<input type="text" id="sendMsg" />
	<input type="button" onclick="sendMsg()">

<script type="text/javascript" src="/webjars/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="/js/websocket/sockjs-1.1.1.min.js"></script>
<script type="text/javascript" src="/js/websocket/stomp-2.3.4.min.js"></script>
<script type="text/javascript" src="/js/websocket/wbsocket.js"></script>
<script type="text/javascript">
var ws1 ; 
$(function(){
	ws1 = getWebsockByUrl("message/test1");
	//消息监控
	ws1.onmessage = function (event) {  
		console.log('onmessage messageAirInit 收返回消息为 : ' + event.data);
	}; 
	
});

function sendMsg(){
	var value = $("#sendMsg").val();
	ws1.send(value);
	console.log(value);
}

</script>
</body>
</html>