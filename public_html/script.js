  var ws;

        function init() {
            ws = new WebSocket("ws://localhost:8080/chat");
            ws.onopen = function (event) {

            }
            ws.onmessage = function (event) {
                var mess = JSON.parse(event.data);
                var textarea;
                if (mess.status == 1){
                     textarea = document.getElementById("users");
                     textarea.value = mess.message + "\n";
                }
                else{
                    textarea = document.getElementById("messages");
                    textarea.value = textarea.value + mess.message + "\n";
                }
            }
            ws.onclose = function (event) {

            }
        };

        function sendMessage() {
            var messageField = document.getElementById("message");
            var userNameField = document.getElementById("username");
            var message = userNameField.value + ":" + messageField.value;
            ws.send(message);
            messageField.value = '';
        }