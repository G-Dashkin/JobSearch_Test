package com.example.jobsearch_test.data.database.factory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobsearch_test.data.database.dao.FavoritesDao
import com.example.jobsearch_test.data.database.entities.FavoritesEntity

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "jobsearch.db"

@Database(
    entities = [FavoritesEntity::class],
    exportSchema = false,
    version = DATABASE_VERSION)
abstract class AppDatabase: RoomDatabase()  {

    abstract fun favoritesDao(): FavoritesDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun createDatabase(context: Context): AppDatabase {
            return instance?: synchronized(LOCK) {
                Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = DATABASE_NAME
                ).build()
            }
        }
    }
}