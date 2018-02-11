<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<title>Javascript 二维码生成库：QRCode</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
<script type="text/javascript" src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="http://static.runoob.com/assets/qrcode/qrcode.min.js"></script>
</head>
<body>
<center>
	<h1>菜鸟演示</h1>http://www.runoob.com/try/try.php?filename=tryhtml5_QRCode
	<h1>文档：</h1>http://code.ciaoca.com/javascript/qrcode/
	<h1>github：</h1>https://github.com/davidshimjs/qrcodejs


</center>
<input id="text" type="text" value="http://www.runoob.com" style="width:80%" /><br />
<div id="qrcode" style="width:100px; height:100px; margin-top:15px;"></div>

<script type="text/javascript">
var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 400,
	height : 400
});

function makeCode () {		
	var elText = document.getElementById("text");
	
	if (!elText.value) {
		alert("Input a text");
		elText.focus();
		return;
	}
	
	qrcode.makeCode(elText.value);
}

makeCode();

$("#text").
	on("blur", function () {
		makeCode();
	}).
	on("keydown", function (e) {
		if (e.keyCode == 13) {
			makeCode();
		}
	});
</script>
</body>
</html>