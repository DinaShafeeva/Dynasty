package ru.itis.dynasty.dao;

import ru.itis.dynasty.models.Card;
import ru.itis.dynasty.util.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardDAO {

    private Connection connection;

    public void create(Card card) {
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("INSERT INTO dynasty.card (name, power, protection)" +
                    "VALUES (?, ?, ?)");
            statement.setString(1, card.getName());
            statement.setInt(2, card.getPower());
            statement.setInt(3, card.getProtection());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    public Card findByID(int id){
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("SELECT * FROM dynasty.card WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               Card card = new Card();
                card.setName(resultSet.getString("name"));
                card.setPower(resultSet.getInt("power"));
                card.setProtection(resultSet.getInt("protection"));
                card.setInGameCount(resultSet.getInt("in_game_count"));
                card.setIdSpecialAbility(resultSet.getInt("id_special_ability"));
                return card;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
    public Card findByName(String name){
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("SELECT * FROM dynasty.card WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               Card card = new Card();
                card.setId(resultSet.getInt("id"));
                card.setPower(resultSet.getInt("power"));
                card.setProtection(resultSet.getInt("protection"));
                card.setInGameCount(resultSet.getInt("in_game_count"));
                card.setIdSpecialAbility(resultSet.getInt("id_special_ability"));
                return card;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
