package ru.itis.dynasty.dao;

import ru.itis.dynasty.models.Ability;
import ru.itis.dynasty.models.Card;
import ru.itis.dynasty.util.DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbilityDAO {
    private Connection connection;

    public void create(Ability ability) {
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("INSERT INTO dynasty.ability (name, description)" +
                    "VALUES (?, ?)");
            statement.setString(1, ability.getName());
            statement.setString(2, ability.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    public Ability findByID(int id){
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("SELECT * FROM dynasty.ability WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
             Ability ability = new Ability();
                ability.setName(resultSet.getString("name"));
                ability.setDescription(resultSet.getString("description"));
                return ability;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
    public Ability findByName(String name){
        PreparedStatement statement;
        connection = DataBase.getInstance().getConnection();
        try {
            statement = connection.prepareStatement("SELECT * FROM dynasty.ability WHERE name = ?");
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               Ability ability = new Ability();
                ability.setId(resultSet.getInt("id"));
                ability.setDescription(resultSet.getString("description"));
                return ability;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
