package ru.itis.dynasty.models;

import java.util.ArrayList;

public class User extends Entity {
    private int id;
    private String password;
    private ArrayList<Card> cards;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public User() {
    }

    public User(String name, String password) {
        super(name);
        this.password = password;
    }

//   public User(String name, ArrayList<Card> cards) {
//        super(name);
//        this.cards = cards;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
