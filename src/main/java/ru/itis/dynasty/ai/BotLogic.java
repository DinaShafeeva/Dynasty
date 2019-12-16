package ru.itis.dynasty.ai;

import ru.itis.dynasty.models.Card;
import ru.itis.dynasty.models.User;

import java.util.ArrayList;

public class BotLogic {
    private ArrayList<Card> myCards;
    private int life = 3;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public BotLogic(ArrayList<Card> myCards) {
        this.myCards = myCards;
    }

    public ArrayList<Card> getMyCards() {
        return myCards;
    }

    public void setMyCards(ArrayList<Card> myCards) {
        this.myCards = myCards;
    }

    public Card attack(){
        int maxAttack = 0;
        Card cardToAttack = myCards.get(0);
        for (Card card: myCards){
            if (card.getPower() > maxAttack) {
                maxAttack = card.getPower();
                cardToAttack = card;
            }
        }
        myCards.remove(cardToAttack);
        return cardToAttack;
    }

    public Card protect(){
        int maxProtect = 0;
        Card cardToProtect = myCards.get(0);
        for (Card card: myCards){
            if (card.getProtection() > maxProtect) {
                maxProtect = card.getProtection();
                cardToProtect = card;
            }
        }
        myCards.remove(cardToProtect);
        return cardToProtect;
    }

}
