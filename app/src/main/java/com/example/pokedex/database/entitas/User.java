package com.example.pokedex.database.entitas;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "users")
public class User {
    @PrimaryKey()
    @NonNull
    public String id;

    @ColumnInfo(name = "username")
    public String username;

    @ColumnInfo(name = "email")
    public String email;

//    @ColumnInfo(name = "password")
//    public String password;
}
