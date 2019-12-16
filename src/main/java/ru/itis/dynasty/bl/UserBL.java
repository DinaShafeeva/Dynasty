package ru.itis.dynasty.bl;

import ru.itis.dynasty.dao.UserDAO;
import ru.itis.dynasty.dao.UserStatDAO;
import ru.itis.dynasty.models.User;
import ru.itis.dynasty.models.UserStat;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserBL {
    private UserDAO userDAO = new UserDAO();
    private UserStatDAO userStatDAO = new UserStatDAO();

    public boolean checkPassword(String password1, String password2){
        if ((password1.length() > 6)&&(password1.equals(password2))){
            return true;
        } else return false;
    }

    public User update(User user, String repeatPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        User foundUser = userDAO.read(user.getId());
        User newUser = new User();
        newUser.setId(user.getId());

        if (foundUser != null){
            if (user.getName() == null){
                newUser.setName(foundUser.getName());
            } else {
                newUser.setName(user.getName());
            }

            if (user.getPassword() == null){
                newUser.setPassword(foundUser.getPassword());
            } else {
                if (checkPassword(user.getPassword(), repeatPassword)) {
                    newUser.setPassword(getHash(user.getPassword()));
                }
            }

            user = userDAO.update(newUser);
            return user;
        } else{
            return foundUser;
        }
    }

    public boolean register(String name, String password, String repeatPassword) {
        if (checkPassword(password, repeatPassword)){
            if (!userDAO.isExist(name)) {
                User user = null;
                try {
                    user = new User(name, getHash(password));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                User createdUser = userDAO.create(user);
                if (createdUser != null) {
                    UserStat userStat = new UserStat();
                    userStat.setIdUser(createdUser.getId());
                    userStatDAO.create(userStat);
                    //при нажатии на кнопку регистрации отправляет на страницу авторизации
                    return true;
                } else {
                    //если уже зарегистрирован
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public User login(String name, String password) {
        try {
            return userDAO.isExist(name, getHash(password)) ;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("No such algorithm");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("No such encoding");
        }
    }


    public String getHash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(str.getBytes("utf-8"));
        String s2 = new BigInteger(1, m.digest()).toString(16);
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0, count = 32 - s2.length(); i < count; i++) {
            sb.append("0");
        }
        return sb.append(s2).toString();
    }
}

