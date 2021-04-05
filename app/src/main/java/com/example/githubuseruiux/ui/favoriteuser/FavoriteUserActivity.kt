package com.example.githubuseruiux.ui.favoriteuser

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuseruiux.adapter.GithubUserAdapter
import com.example.githubuseruiux.databinding.ActivityFavoriteUserBinding
import com.example.githubuseruiux.model.User
import com.example.githubuseruiux.ui.detailuser.DetailUserActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var viewModel: FavoriteUserViewModel
    private lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var githubUsers: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"

        viewModel = ViewModelProvider(
            this
        ).get(FavoriteUserViewModel::class.java)

        binding.rvFavoriteUser.apply {
            githubUserAdapter = GithubUserAdapter()
            setHasFixedSize(true)
            adapter = githubUserAdapter
            layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
        }

        showLoading(true)

        viewModel.getFavoriteUser()?.observe(this){
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
            Intent(this, DetailUserActivity::class.java).apply {
                putExtra(DetailUserActivity.USER_EXTRA, githubUsers[position])
                startActivity(this)
            }
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