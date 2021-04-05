package com.example.githubuseruiux.ui.favoriteuser

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.githubuseruiux.localdb.FavoriteUserDao
import com.example.githubuseruiux.localdb.UserDatabase

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {
    private var userDb: UserDatabase? = UserDatabase.getInstance(application)
    private var favoriteUserDao: FavoriteUserDao? = userDb?.favoriteUserDao()

    fun getFavoriteUser() = favoriteUserDao?.getFavoriteUsers()

}