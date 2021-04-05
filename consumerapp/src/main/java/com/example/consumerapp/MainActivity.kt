package com.example.consumerapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var githubUsers: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        viewModel = ViewModelProvider(
            this
        ).get(FavoriteUserViewModel::class.java)

        binding.rvFavoriteUser.apply {
            githubUserAdapter = GithubUserAdapter()
            setHasFixedSize(true)
            adapter = githubUserAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        showLoading(true)
        viewModel.getFavoriteUser(this)
        viewModel.userlist.observe(this){
            showLoading(false)
            if(it !=null){
                val listUser = it.map { favUser->
                    User(favUser.id, favUser.username, favUser.image)
                }
                githubUsers = ArrayList(listUser)
                githubUserAdapter.setGithubUsers(githubUsers)

                if(it.isEmpty()){
                    showTextStatus()
                }
            }
        }

        githubUserAdapter.onClick = { position ->
            Toast.makeText(this, "${githubUsers[position].username} Clicked", Toast.LENGTH_SHORT).show()
        }

    }

    private fun showLoading(boolean: Boolean){
        if(boolean){
            binding.rvFavoriteUser.visibility = View.GONE
            binding.tvStatus.visibility = View.GONE
            binding.loadingFavorite.visibility = View.VISIBLE
        }else{
            binding.rvFavoriteUser.visibility = View.VISIBLE
            binding.tvStatus.visibility = View.GONE
            binding.loadingFavorite.visibility = View.GONE
        }
    }

    private fun showTextStatus(){
        binding.rvFavoriteUser.visibility = View.GONE
        binding.loadingFavorite.visibility = View.GONE
        binding.tvStatus.visibility = View.VISIBLE
    }
}