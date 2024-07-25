package com.vallem.marvelhq.shared.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteComicEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("description") val description: String?,
    @ColumnInfo("thumb_url") val thumbUrl: String?,
)