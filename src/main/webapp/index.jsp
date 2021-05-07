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
    <input type="button" id="submit" onclick="runDrools()" disabled="disabled" value="Run Drools">
    <hr>
    <textarea id="msg" rows="99" style="width: 100%">
</textarea>
</div>
<script>

    var sessionId;
    let ws = new WebSocket("ws://localhost:8080/jboss-ws-demo-1.0-SNAPSHOT/ws");
    ws.onmessage = function (message) {
        let receivedData = JSON.parse(message.data);
        if (receivedData.kind === 'WsConnectedMessage') {
            sessionId = receivedData.id
            document.getElementById("submit").removeAttribute("disabled")
        } else {
            let target = document.getElementById("msg")
            target.value += "[" + receivedData.level + "] " + receivedData.message + String.fromCharCode(13, 10)
        }
    };

    function runDrools() {
        document.getElementById("msg").value = ""
        fetch('api/drools/run', {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                sessionId: sessionId
            })
        })
    }


</script>
</body>
</html>