package com.example.pokedex.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokedex.database.entitas.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    User login(String username);

    @Insert
    void register(User user);
    // Metode lain sesuai kebutuhan
}
