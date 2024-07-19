package com.vallem.marvelhq.shared.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.vallem.marvelhq.shared.data.local.entity.ComicEntity

@Dao
interface FavoriteComicsDao {
    @Upsert
    suspend fun save(comic: ComicEntity)

    @Query("select * from comicentity")
    fun retrieveAll(): PagingSource<Int, ComicEntity>

    @Query("select * from comicentity where id = :id")
    fun find(id: Int): ComicEntity?

    @Delete
    suspend fun remove(comic: ComicEntity)

    @Query("delete from comicentity")
    suspend fun removeAll()
}