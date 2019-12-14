package ru.itis.dynasty.dao;

import ru.itis.dynasty.util.DataBase;
import ru.itis.dynasty.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements UserCrudDAO {

    private Connection connection;

    @Override
    public User create(User user) {
        connection = DataBase.getInstance().getConnection();
        String sqlQuery = "INSERT INTO dynasty.user ( name, password)" +
                "VALUES (?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            if (!isExist(user.getName())) {
                ps.executeUpdate();
                return read(user.getName());
            } else return null;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public User read(String name) {
        connection = DataBase.getInstance().getConnection();
        String sqlQuery = "SELECT * FROM dynasty.user WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, name);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(resultSet.getString("name"),
                            resultSet.getString("password"));
                    user.setId(resultSet.getInt("id"));
                    return user;
                } else return null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    public User read(int id) {
        connection = DataBase.getInstance().getConnection();
        String sqlQuery = "SELECT * FROM dynasty.user WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(resultSet.getString("name"),
                            resultSet.getString("password"));
                    user.setId(resultSet.getInt("id"));
                    return user;
                } else return null;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(e);
            }
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public User update(User user) {
        connection = DataBase.getInstance().getConnection();
        String sqlQuery = "UPDATE dynasty.user SET name = ?, password = ?" +
                "WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)){
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getId());
            ps.executeUpdate();
            return read(user.getId());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void delete() {
    }

    public boolean isExist(String name) {
        connection = DataBase.getInstance().getConnection();
        String sqlQuery = "SELECT * FROM dynasty.user WHERE dynasty.user.name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)){
            ps.setString(1, name);
            if (ps.executeQuery().next()) {
                return true;
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public User isExist(String name, String password) {
        connection = DataBase.getInstance().getConnection();
        String sqlQuery = "SELECT * FROM dynasty.user WHERE name = ? AND password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlQuery)) {
            ps.setString(1, name);
            ps.setString(2, password);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(resultSet.getString("name"),
                            resultSet.getString("password"));
                    user.setId(resultSet.getInt("id"));
                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }
}
