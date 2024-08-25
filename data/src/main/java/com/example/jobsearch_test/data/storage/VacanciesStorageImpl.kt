package com.example.jobsearch_test.data.storage

import android.util.Log
import com.example.jobsearch_test.data.database.dao.FavoritesDao
import com.example.jobsearch_test.data.database.entities.FavoritesEntity
import com.example.jobsearch_test.data_api.storage.VacanciesStorage
import javax.inject.Inject

class VacanciesStorageImpl @Inject constructor(
    private val favoritesDao: FavoritesDao
): VacanciesStorage {

    override suspend fun selectFavorites(favoriteId: String) {
        if (favoritesDao.getAllFavorites().contains(favoriteId)){
            favoritesDao.delete(FavoritesEntity(id = favoriteId))
        } else {
            favoritesDao.insert(FavoritesEntity(id = favoriteId))
        }
    }

    override suspend fun getFavorites(): List<String> {
        return favoritesDao.getAllFavorites()
    }

}