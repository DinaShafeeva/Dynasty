package ru.itis.dynasty.models;

public class UserStat {

    private int idUser;
    private int winCount;
    private int defeatCount;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getDefeatCount() {
        return defeatCount;
    }

    public void setDefeatCount(int defeat_count) {
        this.defeatCount = defeatCount;
    }
}
