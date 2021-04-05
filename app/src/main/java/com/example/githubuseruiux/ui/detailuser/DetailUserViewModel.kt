package com.example.githubuseruiux.ui.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.githubuseruiux.api.RetrofitInstance
import com.example.githubuseruiux.localdb.FavoriteUser
import com.example.githubuseruiux.localdb.FavoriteUserDao
import com.example.githubuseruiux.localdb.UserDatabase
import com.example.githubuseruiux.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private var userDb: UserDatabase? = UserDatabase.getInstance(application)
    private var favoriteUserDao: FavoriteUserDao? = userDb?.favoriteUserDao()

    private var _user = MutableLiveData<User>()
    val user get() = _user

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite get() = _isFavorite

    private var _listFollowers = MutableLiveData<ArrayList<User>>()
    val listFollowers get() = _listFollowers

    private var _listFollowing = MutableLiveData<ArrayList<User>>()
    val listFollowing get() = _listFollowing

    fun setUser(username: String) {
        RetrofitInstance.api.getDetailUser(username).enqueue(
            object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        _user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("Retrofit Failure", "${t.message}")
                }
            }
        )
    }

    fun setListFollowers(username: String) {
        RetrofitInstance.api.getFollowerUser(username).enqueue(
            object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        _listFollowers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Retrofit Failure", "${t.message}")
                }

            }
        )
    }

    fun setListFollowing(username: String) {
        RetrofitInstance.api.getFollowingUser(username).enqueue(
            object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        _listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Retrofit Failure", "${t.message}")
                }

            }
        )
    }

    fun checkIsFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteUserDao?.checkUser(id)
            if(count != null){
                _isFavorite.postValue(count > 0)
            }else{
                _isFavorite.postValue(false)
            }
        }
    }

    fun addToFavorite(id: Int, username: String, image: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val favoriteUser = FavoriteUser(
                id, username, image
            )
            favoriteUserDao?.addFavoriteUser(favoriteUser)
        }
    }

    fun removeToFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUserDao?.removeFavoriteUser(id)
        }
    }
}