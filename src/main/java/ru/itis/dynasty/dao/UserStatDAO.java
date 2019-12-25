package ru.itis.dynasty.dao;

import ru.itis.dynasty.models.UserStat;
import ru.itis.dynasty.util.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStatDAO {
    private Connection connection;

    public void create(UserStat us) {
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("INSERT INTO dynasty.user_stat (id_user, win_count,defeat_count)" +
                    "VALUES (?, ?, ?)");
            statement.setInt(1, us.getIdUser());
            statement.setInt(2, us.getWinCount());
            statement.setInt(3, us.getDefeatCount());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    public UserStat findByID(int id){
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("SELECT * FROM dynasty.user_stat WHERE id_user = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               UserStat us = new UserStat();
                us.setWinCount(resultSet.getInt("win_count"));
                us.setDefeatCount(resultSet.getInt("defeat_count"));
                return us;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
    public UserStat findByName(String name){
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("SELECT * FROM dynasty.user_stat WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserStat us = new UserStat();
                us.setWinCount(resultSet.getInt("win_count"));
                us.setDefeatCount(resultSet.getInt("defeat_count"));
                return us;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
