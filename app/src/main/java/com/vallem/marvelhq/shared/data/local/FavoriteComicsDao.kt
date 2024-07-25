package com.vallem.marvelhq.shared.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vallem.marvelhq.shared.data.local.entity.FavoriteComicEntity

@Dao
interface FavoriteComicsDao {
    @Upsert
    suspend fun save(comic: FavoriteComicEntity)

    @Query("select * from favoritecomicentity")
    fun retrieveAll(): List<FavoriteComicEntity>

    @Query("select * from favoritecomicentity where id = :id")
    fun find(id: Int): FavoriteComicEntity?

    @Delete
    suspend fun remove(comic: FavoriteComicEntity)

    @Query("delete from favoritecomicentity")
    suspend fun removeAll()
}