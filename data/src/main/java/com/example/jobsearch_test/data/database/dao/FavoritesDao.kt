package com.example.jobsearch_test.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobsearch_test.data.database.entities.FavoritesEntity

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favoriteVacancy: FavoritesEntity)

    @Delete
    suspend fun delete(favoriteVacancy: FavoritesEntity)

    @Query("SELECT ${FavoritesEntity.ID} FROM ${FavoritesEntity.TABLE_NAME} ")
    suspend fun getAllFavorites(): List<String>
}