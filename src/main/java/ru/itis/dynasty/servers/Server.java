package ru.itis.dynasty.servers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.dynasty.Protocol.Commands;
import ru.itis.dynasty.Protocol.Request;
import ru.itis.dynasty.Protocol.Response;
import ru.itis.dynasty.bl.ChatBL;
import ru.itis.dynasty.bl.GameLogic;
import ru.itis.dynasty.bl.RoomBL;
import ru.itis.dynasty.bl.UserBL;
import ru.itis.dynasty.dao.CardDAO;
import ru.itis.dynasty.models.Card;
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

//        private String game(){
//            boolean isFinished = false;
//            GameLogic gameLogic = new GameLogic();
//            CardDAO card = new CardDAO();
//            ArrayList<Card> botList = null;
//            ArrayList<Card> userList = null;
//            int userLife = 3;
//            int botLife = 3;
//            BotLogic bot = new BotLogic(botList);
//            User user = new User(userList);
//            boolean userStatus = true;
//            //статус нападающего
//
//            while ((userLife>0) && (botLife>0)) {
//                ArrayList<Card> attackedCards = null;
//                ArrayList<Card> protectedCards = null;
//
//                Random random = new Random();
//                for (int i=0; i<5;i++){
//                    if (botList.get(i) == null) botList.add(card.findByID( random.nextInt(15)));
//                    if (userList.get(i) == null) userList.add(card.findByID( random.nextInt(15)));
//                }
//
//
//                    //ход атаки
//               //     attackedCards.add(card1);
//                    gameLogic.currentSituation(attackedCards, protectedCards);
//                    //засетить в текст
//
//                    //ход защиты
//                    if (isFinished) break;
//                    //protectedCards.add(card2);
//                    gameLogic.currentSituation(attackedCards, protectedCards);
//                    //засетить в текст
//
//                if (gameLogic.currentSituation(attackedCards, protectedCards) > 0) {
//                    if (userStatus) {
//                        botLife--;
//                    }else userLife--;
//                }
//                userStatus = false;
//            }
//            if (userLife==0){
//                //связать юзер и юзерСтат и изменить статистику
//                return "You lose";
//            } else return "You won";
//        }

        @Override
        public void run() {
            while (true) {
                try {
                    Request request = null;
                    try {
                        request = objectMapper.readValue(reader.readLine(), Request.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Response response;
                    UserBL userBL = new UserBL();
                    String command = request.getHeader();
                    if (command.equals(Commands.LOG_IN.name())){
                        response = Response.build(userBL.login(request.getParameter("login"), request.getParameter("password")));
                    } else if (command.equals(Commands.REGISTER.name())) {
                        if (userBL.register(request.getParameter("login"), request.getParameter("password"), request.getParameter("repeatPassword"))) {
                            response = Response.build("successful");
                        } else response = Response.build("unsuccessful");
                    } else if (command.equals(Commands.SEND_MESSAGE.name())){
                        ChatBL chatBL = new ChatBL(request.getParameter("name"), request.getParameter("textMessage"));
                        response = Response.build()
                    }







                    GameLogic gameLogic = new GameLogic();
                    CardDAO cardDAO = new CardDAO();
                    ArrayList<Card> botList = null;
                    ArrayList<Card> userList = null;
                    int userLife = 3;
                    int botLife = 3;

                    //это от чатика
                    String message = reader.readLine();
                    //сюда писать все
                    //обработка команд с клиента

//пока игра не бетте, получи от клиента карты, запусти метод игры


//                    while ((userLife>0) && (botLife>0)) {
//                        for (int i=0; i<5;i++){
//                            if (botList.get(i) == null) botList.add(cardDAO.findByID( random.nextInt(15)));
//                            if (userList.get(i) == null) userList.add(cardDAO.findByID( random.nextInt(15)));
//                        }
//                        //получение карты нападающего - ниже строчку стереть
//                        Card card = new Card();
//                        gameLogic.game(card);
//                    }
                    //Запрос с кнопки начать, оздаю юзера по имени
                    User user = new User();
                  //  RoomBL room = new RoomBL(user);
                    //это чатик
                    for (ClientHandler client : clients) {
                        client.writer.println(message);
                    }
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }
}
