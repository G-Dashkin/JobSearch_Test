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
        Log.d("MyLog", "Favorite Id in Storage: ${favoritesDao.getAllFavorites()}")
        favoritesDao.insert(FavoritesEntity(id = favoriteId, isFavorites = true))
    }

    override suspend fun getFavorites(): List<String> {
        favoritesDao.getAllFavorites()
        Log.d("MyLog", "All Favorites in Storage: ${favoritesDao.getAllFavorites()}")
        return listOf()
    }

}