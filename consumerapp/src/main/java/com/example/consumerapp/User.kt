package com.example.consumerapp

import java.io.Serializable


data class User(
    val id: Int,
    val username: String,
    val image: String,
    val name:String ? = null,
    val followers: String? = null,
    val following: String? = null
): Serializable
