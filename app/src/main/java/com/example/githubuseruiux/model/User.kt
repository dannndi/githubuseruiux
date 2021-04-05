package com.example.githubuseruiux.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User(
    val id: Int,
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val image: String,
    val name:String ? = null,
    val followers: String? = null,
    val following: String? = null
): Serializable
