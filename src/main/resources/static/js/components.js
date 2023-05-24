const messageBox = (message, user) =>{

    let i = userID == message.senderId

    return (
        "<div class='message "+ (i?'self':'') +"'>\n" +
        "  <div class=\"chat-user-name \"></div>\n" +
        "  <div class=\"chat-user-text \" >"+message.text+"</div>\n" +
        "</div>\n");
}