let manager;
let stompClient = null;

addEventListener("DOMContentLoaded", (event) => { connect()});

function connect() {
    manager = document.getElementById("manager").value;
    let socket = new SockJS('http://localhost:8080/chat');
    if (stompClient) stompClient.disconnect();
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => connectCallback(frame));
}

function connectCallback(frame){
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/wait/' + manager, ()=>{
        location.replace("http://localhost:8080/");
    });
}