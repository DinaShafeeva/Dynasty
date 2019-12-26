package ru.itis.dynasty.bl;

import ru.itis.dynasty.dto.MessageDTO;
import ru.itis.dynasty.Protocol.Response;
import ru.itis.dynasty.models.User;
import ru.itis.dynasty.servers.Server;

public class ChatBL {
    private User user;
    private String textMessage;

    public ChatBL(User user, String textMessage) {
        this.user = user;
        this.textMessage = textMessage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public void sendMessage(String textMessage, String name){
        for (Server.ClientHandler client : Server.getClients()) {
            client.getWriter().println(textMessage);
        }
        Response response = Response.build(new MessageDTO.Builder().setTextMessage(textMessage).build());
    }
}
