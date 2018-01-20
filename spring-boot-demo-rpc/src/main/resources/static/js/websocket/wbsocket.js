var ws = null;
var url = null ;
var stompClient = null;
var subscription = null;
var isDebug = false;


/**
 * stomp协议初始化
 */
function stompClientInit(){
	stompClient = Stomp.over(ws);
	var connectCallback = function() {
		console.log("Info: connection opened.");
		//订阅
		stompClient.subscribe("/topic/messDemo", function(data) {
			if (data) {
				var body = JSON.parse(data.body);
				console.log("Public: " + body.value);
			}
		});
	};
	var errorCallback = function(error) {
		if (error.headers) {
			console.log("Error: " + error.headers.message);
		}
	};
	if (isDebug) {
		stompClient.debug = function(str) {
			console.log("Debug: " + str);
		};
	}
	stompClient.connect({}, connectCallback, errorCallback);
}

/**
 * stomp协议 发送服务端消息
 */
function sendServeEemo() {
	if (ws != null) {
		var message = document.getElementById('message').value;
		console.log('Pinconsole.logg with: ' + message);
		if (stompClient != null) {
			stompClient.send("/app/ping", {}, JSON.stringify({
				'key' : 'MYKEY',
				'value' : message
			}));
		}
	} else {
		alert('connection not established, please connect.');
	}
}

/**
 * 获得对应websocket协议URL
 * @param url
 * @returns
 */
function getWebsockByUrl(url){
	if(null == url || url.trim() == ""){
		console.log("声明websocket的url不能为空！");
		return false;
	}else{
		url = url.trim();
	}
	
	if(url.indexOf != 0){
		url = "/"+url;
	}
	var _host = window.location.host
	var _ws ;
	if ('WebSocket' in window) {
		_ws = new WebSocket("ws://"+_host+"/ws"+url);
	} else if ('MozWebSocket' in window) {
		_ws = new MozWebSocket("ws://"+_host+"/ws"+url);
	} else {
		_ws = new SockJS("http://"+_host+"/wss/"+url);
	}
	return _ws;
}


$(function(){
	//websocket 消息推送
	message1Init();
});

function message1Init() {
	var ws1 = getWebsockByUrl("message/test1");
	//消息监控
	ws1.onmessage = function (event) {  
		console.log('onmessage messageAirInit 收返回消息为 : ' + event.data);
	};  
}

