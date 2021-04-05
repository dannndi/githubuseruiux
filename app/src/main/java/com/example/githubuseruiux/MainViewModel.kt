package com.example.githubuseruiux

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuseruiux.api.RetrofitInstance
import com.example.githubuseruiux.model.SearchUserResponse
import com.example.githubuseruiux.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val listQueryUsers = MutableLiveData<ArrayList<User>>()

    fun setListQueryUsers(username: String) {
        RetrofitInstance.api.getUserByQuery(username).enqueue(
            object : Callback<SearchUserResponse> {
                override fun onResponse(
                    call: Call<SearchUserResponse>,
                    response: Response<SearchUserResponse>
                ) {
                    if (response.isSuccessful) {
                        listQueryUsers.postValue(response.body()?.users)
                    }
                }

                override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                    Log.d("Retrofit Failure", "${t.message}")
                }
            }
        )
    }

    fun getListQueryUsers(): LiveData<ArrayList<User>> = listQueryUsers
}