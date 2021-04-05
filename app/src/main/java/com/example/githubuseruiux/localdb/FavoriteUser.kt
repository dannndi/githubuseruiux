package com.example.githubuseruiux.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_user")
data class FavoriteUser(
    @PrimaryKey val id: Int,
    val username: String,
    val image: String,
)
