<!DOCTYPE html>
<html>
<head lang="en">
    <title>Spring Boot Demo - FreeMarker</title>
</head>
<body>
	<#list commonException as exception>
	<h1>error-系统出错，请联系后台管理员${exception}</h1>
	</#list>
</body>
</html>