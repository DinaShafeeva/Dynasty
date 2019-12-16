package ru.itis.dynasty.models;

public class Card {
    private int id;
    private String name;
    private int power;
    private int protection;
    private int idSpecialAbility;
    private int inGameCount;

    public Card() {
    }

    public Card(String name, int power, int protection, int idSpecialAbility) {
        this.name = name;
        this.power = power;
        this.protection = protection;
        this.idSpecialAbility = idSpecialAbility;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getProtection() {
        return protection;
    }

    public void setProtection(int protection) {
        this.protection = protection;
    }

    public int getIdSpecialAbility() {
        return idSpecialAbility;
    }

    public void setIdSpecialAbility(int idSpecialAbility) {
        this.idSpecialAbility = idSpecialAbility;
    }

    public int getInGameCount() {
        return inGameCount;
    }

    public void setInGameCount(int inGameCount) {
        this.inGameCount = inGameCount;
    }

}
