package ru.itis.dynasty.dao;

import ru.itis.dynasty.models.User;

public interface UserCrudDAO {
    User create(User user);
    User read(String name);
    User update(User user);
    void delete();
}
