<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello Drools!" %>
</h1>
<br/>
<div style="width: 1200px">
    <input type="button" id="submit" onclick="runDrools()" value="Run Drools">
    <hr>
    <textarea id="msg" rows="99" style="width: 100%">
</textarea>
</div>
<script>

    let ws, sessionId;


    function runDrools() {
        if (!ws) {
            ws = new WebSocket("ws://localhost:8080/jboss-ws-demo-1.0-SNAPSHOT/ws");
            ws.onmessage = function (message) {
                let receivedData = JSON.parse(message.data);
                if (receivedData.__type === 'WsConnectedMessage') {
                    sessionId = receivedData.id
                } else {
                    let target = document.getElementById("msg")
                    target.value += "[" + receivedData.level + "] " + receivedData.message + String.fromCharCode(13, 10)
                }
            };
        }
        document.getElementById("msg").value = ""
        waitForSocketConnection(ws, function () {
            console.log("Sending start message!!!");
            ws.send(JSON.stringify({
                __type: "DroolsRunMessage",
                sessionId: sessionId
            }))
        });
    }

    // Make the function wait until the connection is made...
    function waitForSocketConnection(socket, callback) {
        setTimeout(
            function () {
                if (socket.readyState === 1 && sessionId) {
                    console.log("Connection is made")
                    if (callback != null) {
                        callback();
                    }
                } else {
                    console.log("wait for connection...")
                    waitForSocketConnection(socket, callback);
                }

            }, 5); // wait 5 milisecond for the connection...
    }


</script>
</body>
</html>