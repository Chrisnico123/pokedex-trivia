package com.example.pokedex.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokedex.database.entitas.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId")
    List<Favorite> getFavorites(String userId);

    @Insert
    void insertFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);

}
