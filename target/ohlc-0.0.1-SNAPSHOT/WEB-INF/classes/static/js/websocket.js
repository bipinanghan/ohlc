var ws = new WebSocket("ws://" + location.host + "/ohlc/update");


ws.onopen = function() {
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "WebSocket Opened";

    var holder = document.getElementById("stockOhlc");
    holder.innerHTML = newDiv.innerHTML;
}

ws.onmessage = function(evt) {
	var data = JSON.parse(evt.data);
	var st = document.getElementById(data.symbol);
	if(typeof(st) != 'undefined' && null != st){
		 var ohlcData = JSON.parse(evt.data);
		 document.getElementById("barNum").innerHTML = '<B>' + ohlcData.barNumber + '</B>';
		 document.getElementById("open").innerHTML = '<B>' + ohlcData.o + '</B>';
		 document.getElementById("high").innerHTML = '<B>' + ohlcData.h + '</B>';
		 document.getElementById("low").innerHTML = '<B>' + ohlcData.l + '</B>';
		 document.getElementById("close").innerHTML = '<B>' + ohlcData.c + '</B>';
	}else {
		var newDiv = document.createElement("div");
		if(data.symbol != null){
			newDiv.setAttribute("id", data.symbol);
		}
		document.getElementById("stockOhlc").append(newDiv);
	}
}

ws.onclose = function() {
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "WebSocket Connection Has Been Closed ";

    var holder = document.getElementById("stockOhlc");
    holder.innerHTML = newDiv.innerHTML;
}

ws.onerror = function() {
    var newDiv = document.createElement("div");
    newDiv.innerHTML = "WebSocket Error. Refreshing the page.";

    var holder = document.getElementById("stockOhlc");
    //holder.appendChild(newDiv);
    holder.innerHTML = newDiv.innerHTML;
}