package com.example.pokedex.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pokedex.database.dao.FavoriteDao
import com.example.pokedex.database.dao.MyPokemonDao
import com.example.pokedex.database.dao.UserDao
import com.example.pokedex.database.entitas.Favorite
import com.example.pokedex.database.entitas.MyPokemon
import com.example.pokedex.database.entitas.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, MyPokemon::class, Favorite::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun myPokemonDao(): MyPokemonDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my_app_database"
                )
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                CoroutineScope(Dispatchers.IO).launch {
                    // Database operations should be performed here
                    database.execSQL("CREATE TABLE IF NOT EXISTS `users_new` (`id` TEXT PRIMARY KEY NOT NULL, `username` TEXT, `email` TEXT, `password` TEXT)")
                    database.execSQL("INSERT INTO `users_new` (`id`, `username`, `email`) SELECT `id`, `username`, `email` FROM `users`")
                    database.execSQL("DROP TABLE `users`")
                    database.execSQL("ALTER TABLE `users_new` RENAME TO `users`")
                }
            }
        }
    }
}
