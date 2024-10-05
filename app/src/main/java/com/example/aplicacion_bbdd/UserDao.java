package com.example.aplicacion_bbdd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long  insertUser(User user);

    @Query("SELECT * FROM users WHERE userId = :id")
    User getUserById(int id);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("DELETE FROM users")
    void deleteAllUsers();

}