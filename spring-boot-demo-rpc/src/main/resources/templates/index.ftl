<!DOCTYPE html>
<html>
<head lang="en">
	<title>Spring Boot Demo - FreeMarker</title>
	
	<link href="/css/index.css" rel="stylesheet" />
</head>
<body>
	<center>
		<h1 id="title">${title}</h1>
		<h1 id="name">${name}</h1>
		<h1 id="sessionId">${sessionId}</h1>
	</center>
	只输入数字和逗号的验证：
	<input type="text" onkeyup="this.value=this.value.replace(/[^\d,]/,'').replace(/,{2,}/,',');" onblur="this.value=this.value.replace(/^,|,$/,'')"/>
	只输入数据、字母、逗号：
	<input type="text" onkeyup="this.value=this.value.replace(/[^A-Za-z0-9,]/,'').replace(/,{2,}/,',');" onblur="this.value=this.value.replace(/^,|,$/,'')"/>
	
	
	<!-- <form method="POST" enctype="multipart/form-data" action="/file/upload"> 
       	文件：<input type="file" name="roncooFile" />
       <input type="submit" value="上传" />
   	</form> -->
	<script type="text/javascript" src="/webjars/jquery/2.1.4/jquery.min.js"></script>
</body>
</html>