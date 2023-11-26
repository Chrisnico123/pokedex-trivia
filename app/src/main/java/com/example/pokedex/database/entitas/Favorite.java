package com.example.pokedex.database.entitas;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "userId")
    public String userId;

}

