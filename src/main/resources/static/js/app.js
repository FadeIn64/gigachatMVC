var stompClient = null;
const chatData = [];
var message;

addEventListener("DOMContentLoaded", (event) => { connect()});

function connect() {
    message = document.getElementById("message");
    let socket = new SockJS('http://localhost:8080/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => connectCallback(frame));
}

function connectCallback(frame){
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/topic/messages', onServerMessage);
}

const onServerMessage = (payload) => {
    console.log(payload);
    let payloadData = JSON.parse(payload.body);
    chatData.push(payloadData);
    newMessage(payloadData);
}

const newMessage = (message)=>{
    alert(message);
}

const sendValue = () => {
    if (message.value == "") return;
    if (stompClient) {
        let chatMessage = {
            senderId: 1,
            text: message.value
        };
        message.value = "";
        stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
    }
}