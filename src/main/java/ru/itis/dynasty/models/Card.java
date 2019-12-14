package ru.itis.dynasty.models;

public class Card {
    private int id;
    private String name;
    private int power;
    private int protection;
    private int idSpecialAbility;
    private int inGameCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
