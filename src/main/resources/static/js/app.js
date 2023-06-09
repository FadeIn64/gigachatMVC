let stompClient = null;
let chatData = [];
let chatMessages;
let messageInput;
let chatID;
let clientID;
let userID;

addEventListener("DOMContentLoaded", (event) => { connect()});

function connect() {
    messageInput = document.getElementById("message");
    chatMessages = $("#chat-messages");
    chatID =  document.getElementById("chatId").value;
    userID =  document.getElementById("userId").value;
    clientID =  document.getElementById("clientId").value;
    let socket = new SockJS('http://localhost:8080/chat');
    if (stompClient) stompClient.disconnect();
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => connectCallback(frame));

}

function connectCallback(frame){
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/chat/' + chatID, onServerMessage);
    if(userID == clientID)
        stompClient.subscribe('/topic/end/' + chatID, ()=>location.reload());
}

const onServerMessage = (payload) => {
    console.log(payload);
    let payloadData = JSON.parse(payload.body);
    chatData.push(payloadData);
    newMessage(payloadData);
}

const newMessage = (msg)=>{
    console.log(msg);
    chatMessages.append(messageBox(msg.body, userID));
    // $("chat-messages").append(messageBox(msg.body.text))

}

const sendValue = () => {
    if (messageInput.value == "") return;
    if (stompClient) {
        let chatMessage = {
            'chatId' : chatID,
            senderId: userID,
            text: messageInput.value
        };
        messageInput.value = "";
        stompClient.send("/app/chat/" + chatID, {}, JSON.stringify(chatMessage));
    }
}

const logout = () => {
    location.replace("http://localhost:8080/logout");
}