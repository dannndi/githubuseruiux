package com.example.githubuseruiux.api

import com.example.githubuseruiux.model.SearchUserResponse
import com.example.githubuseruiux.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubUserDAO {
    @GET("search/users")
    @Headers("Authorization: token 77aced602b561e6649fa8e968f58c7e16d85ceb1")
    fun getUserByQuery(@Query("q") username: String): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 77aced602b561e6649fa8e968f58c7e16d85ceb1")
    fun getDetailUser(@Path("username") username: String): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 77aced602b561e6649fa8e968f58c7e16d85ceb1")
    fun getFollowerUser(@Path("username") username: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 77aced602b561e6649fa8e968f58c7e16d85ceb1")
    fun getFollowingUser(@Path("username") username: String): Call<ArrayList<User>>
}