package com.vallem.marvelhq.shared.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vallem.marvelhq.shared.data.local.entity.FavoriteComicEntity

@Database(entities = [FavoriteComicEntity::class], version = 3)
abstract class MarvelHQDatabase : RoomDatabase() {
    abstract val favoriteComicsDao: FavoriteComicsDao
}