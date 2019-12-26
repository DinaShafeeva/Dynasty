package ru.itis.dynasty.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.dynasty.Protocol.Request;
import ru.itis.dynasty.ai.BotLogic;
import ru.itis.dynasty.bl.GameLogic;
import ru.itis.dynasty.dao.CardDAO;
import ru.itis.dynasty.models.Card;
import ru.itis.dynasty.models.User;
import ru.itis.dynasty.servers.Server;

import java.util.ArrayList;
import java.util.Random;

public class Room extends Thread{
    private User user;
    GameLogic gameLogic = new GameLogic();
    ArrayList<Card> userMoveList = new ArrayList<>();
    ArrayList<Card> botMoveList = new ArrayList<>();
    private boolean skipMove;
    private String result;

    public String getResult() {
        return result;
    }

    public void setSkipMove(boolean skipMove) {
        this.skipMove = skipMove;
    }

    public Room(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        Server.ClientHandler clientHandler = null;
        ArrayList<Card> botList = new ArrayList<>();
        ArrayList<Card> userList = new ArrayList<>();
        CardDAO cardDAO = new CardDAO();
        BotLogic botLogic = new BotLogic(botList);
        boolean clientStatus = true;
        int userLife = 3;
        int botLife = 3;


        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (botList.get(i) == null) botList.add(cardDAO.findByID(random.nextInt(15)));
            if (userList.get(i) == null) userList.add(cardDAO.findByID(random.nextInt(15)));
        }
        skipMove = false;

        while ((userLife > 0) && (botLife > 0)) {
            gameLogic.currentSituation(userMoveList, botMoveList);
            if (clientStatus) {
                do {
                    if ((userMoveList.size() > botMoveList.size())) {
                         botMoveList.add(botLogic.protect());
                        gameLogic.currentSituation(userMoveList, botMoveList);
                    }
                } while ((botMoveList.size() == 2) && (userMoveList.size() == 2));
                if (gameLogic.currentSituation(userMoveList, botMoveList) > 0) {
                    botLife--;
                } else userLife--;
                clientStatus = false;
                botMoveList = null;
                userMoveList = null;

            } else {
                do {
                    if ((userMoveList.size() == botMoveList.size()) || skipMove) {
                        if (gameLogic.currentSituation(botMoveList, userMoveList) > 0) {
                            skipMove = true;
                        } else botMoveList.add(botLogic.attack());
                    }
                } while ((botMoveList.size() == 5) || (userMoveList.size() == 5));
                // TODO делать ожидание юзера
                gameLogic.currentSituation(userMoveList, botMoveList);
                clientStatus = true;
                botMoveList = null;
                userMoveList = null;
            }

            if (userLife == 0) {
                //связать юзер и юзерСтат и изменить статистику
                result = "You lose";
                //TODO заполнение статистики
            } else result =  "You won";
        }
    }


    public ArrayList<Card> addCard(Card card){
        if (userMoveList.size()<3) userMoveList.add(card);
        return userMoveList;
    }

    public int currentSit() {
         return gameLogic.currentSituation(userMoveList, botMoveList);
    }

}
