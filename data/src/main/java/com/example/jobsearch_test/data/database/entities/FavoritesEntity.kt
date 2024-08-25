package com.example.jobsearch_test.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FavoritesEntity.TABLE_NAME)
data class FavoritesEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String
) {
    companion object {
        const val TABLE_NAME = "favorites"
        const val ID = "id"
    }
}