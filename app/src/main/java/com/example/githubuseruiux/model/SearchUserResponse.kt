package com.example.githubuseruiux.model

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("items") val users: ArrayList<User>
)
