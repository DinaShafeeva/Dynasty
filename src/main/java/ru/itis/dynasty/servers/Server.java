package ru.itis.dynasty.servers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.dynasty.dao.CardDAO;
import ru.itis.dynasty.dto.CardDTO;
import ru.itis.dynasty.dto.MessageDTO;
import ru.itis.dynasty.dto.UserStatDTO;
import ru.itis.dynasty.Protocol.Commands;
import ru.itis.dynasty.Protocol.CommandsFromServer;
import ru.itis.dynasty.Protocol.Request;
import ru.itis.dynasty.Protocol.Response;
import ru.itis.dynasty.bl.ChatBL;
import ru.itis.dynasty.bl.UserBL;
import ru.itis.dynasty.dao.UserStatDAO;
import ru.itis.dynasty.room.Room;
import ru.itis.dynasty.models.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private static List<ClientHandler> clients;

    public static List<ClientHandler> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        new Server().start(7320);
        System.out.println("Сервер запущен!");
    }

    public Server(){
        this.clients = new ArrayList<>();
    }

    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            try{
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public class ClientHandler extends Thread{
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;
        private ObjectMapper objectMapper;

        public PrintWriter getWriter() {
            return writer;
        }

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            this.objectMapper = new ObjectMapper();
            clients.add(this);
            try{
                this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.writer = new PrintWriter(this.clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("New Client");
        }

        @Override
        public void run() {
            Room room = null;
            while (true) {
                try {
                    Request request = null;
                    try {
                        request = objectMapper.readValue(reader.readLine(), Request.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Response response = null;
                    UserBL userBL = new UserBL();
                    User user = null;

                    String command = request.getHeader();

                    if (command.equals(Commands.LOG_IN.name())){
                        userBL.login(request.getParameter("login"), request.getParameter("password"));
                        System.out.println(request.getParameter("login") + " " + request.getParameter("password"));
                        user = new User(request.getParameter("login"), request.getParameter("password"));
                        response = Response.build(new MessageDTO.Builder().setTextMessage(user.getName()).build());//user.getName()).build());
                        response.setHeaderName(CommandsFromServer.LOG_IN.name());
                    }
                    else if (command.equals(Commands.REGISTER.name())) {
                        user = new User();
                        if (userBL.register(request.getParameter("login"), request.getParameter("password"), request.getParameter("repeatPassword"))) {
                            response = Response.build(new MessageDTO.Builder().setTextMessage("successful").build());
                        } else response = Response.build(new MessageDTO.Builder().setTextMessage("unsuccessful").build());
                        response.setHeaderName(CommandsFromServer.REGISTER.name());
                    }
                    else if (command.equals(Commands.SEND_MESSAGE.name())){
                        ChatBL chatBL = new ChatBL(request.getParameter("name"), request.getParameter("textMessage"));
                        chatBL.sendMessage(request.getParameter("name"), request.getParameter("textMessage"));
                        response = Response.build(new MessageDTO.Builder().setTextMessage("textMessage").build());
                        response.setHeaderName(CommandsFromServer.GET_MESSAGE.name());
                    }
                    else if (command.equals(Commands.START_GAME.name())){
                        room = new Room(request.getParameter("user"));
                        room.start();
                        //комната роботой
                        response = Response.build(new MessageDTO.Builder().setTextMessage("Game started").build());
                        response.setHeaderName(CommandsFromServer.GET_MESSAGE.name());
                    }
                    else if (command.equals(Commands.SEND_CARD.name())){
                        CardDAO cardDAO = new CardDAO();
                        room.addCard(request.getParameter("card"));
                        response = Response.build(new CardDTO.Builder()
                                .setName(request.getParameter("name"))
                                .setPower(cardDAO.findByName(request.getParameter("name")).getPower())
                        .setProtection(cardDAO.findByName(request.getParameter("name")).getProtection())
                        .build());
                        response.setHeaderName(CommandsFromServer.BOTS_CARD.name());
                        writer.println(objectMapper.writeValueAsString(response));
                        response = Response.build(new MessageDTO.Builder().setTextMessage(String.valueOf(room.currentSit())).build());
                        response.setHeaderName(CommandsFromServer.CUR_SITUATION.name());
                    }
                    else if (command.equals(Commands.ABORT_GAME.name())){
                        room.interrupt();
                        response = Response.build(new MessageDTO.Builder().setTextMessage("Game over").build());
                        response.setHeaderName(CommandsFromServer.GET_MESSAGE.name());
                        response.setHeaderName(CommandsFromServer.GET_MESSAGE.name());
                    }
                    else if (command.equals(Commands.PROFILE.name())){
                        UserStatDAO userStatDAO = new UserStatDAO();
                        response = Response.build(
                                new UserStatDTO.Builder().setName(user.getName())
                                        .setVictories(userStatDAO.findByName(user.getName())
                                                .getWinCount()).setDefeats(userStatDAO.findByName(user.getName()).getDefeatCount())
                                        .build());
                        response.setHeaderName(CommandsFromServer.PROFILE.name());
                    }
                    writer.println(objectMapper.writeValueAsString(response));
                    System.out.println(objectMapper.writeValueAsString("Server: " + response));




                    //это от чатика
                    String message = reader.readLine();
                      //это чатик
//                    for (ClientHandler client : clients) {
//                        client.writer.println(message);
//                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
