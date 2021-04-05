package com.example.consumerapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {
    private var _userList = MutableLiveData<ArrayList<User>>()
    val userlist get() = _userList

    fun getFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumn.CONTENT_URI, null, null,
            null, null
        )

        val listConverted = MappingHelper.mapCursorToArrayList(cursor)
        _userList.postValue(listConverted)
    }

}