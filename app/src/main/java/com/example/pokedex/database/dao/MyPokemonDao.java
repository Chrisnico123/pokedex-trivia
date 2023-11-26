package com.example.pokedex.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pokedex.database.entitas.MyPokemon;

import java.util.List;

@Dao
public interface MyPokemonDao {
    @Query("SELECT * FROM mypokemon WHERE userId = :userId")
    List<MyPokemon> getMyPokemons(String userId);

    @Insert
    void insertMyPokemon(MyPokemon myPokemon);

    @Delete
    void deleteMyPokemon(MyPokemon myPokemon);
    // Metode lain sesuai kebutuhan
}
