package com.example.githubuseruiux

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuseruiux.adapter.GithubUserAdapter
import com.example.githubuseruiux.databinding.ActivityMainBinding
import com.example.githubuseruiux.model.User
import com.example.githubuseruiux.ui.SettingsActivity
import com.example.githubuseruiux.ui.detailuser.DetailUserActivity
import com.example.githubuseruiux.ui.favoriteuser.FavoriteUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var githubUsers: ArrayList<User>
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_GithubUserUIUX)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Search Github User"

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        githubUserAdapter = GithubUserAdapter()
        binding.rvGithubUser.apply {
            adapter = githubUserAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        githubUserAdapter.onClick = { position ->
            Intent(this, DetailUserActivity::class.java).apply {
                putExtra(DetailUserActivity.USER_EXTRA, githubUsers[position])
                startActivity(this)
            }
        }

        viewModel.getListQueryUsers().observe(this) {
            showLoading(false)
            githubUsers = it
            githubUserAdapter.setGithubUsers(it)
            if (it.size == 0) {
                showErrorMessage(resources.getString(R.string.status_search_not_found))
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                viewModel.setListQueryUsers(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btn_favorite -> {
                Intent(this, FavoriteUserActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.btn_setting -> {
                Intent(this, SettingsActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return true
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) {
            binding.tvGithubUser.visibility = View.GONE
            binding.rvGithubUser.visibility = View.GONE
            binding.tvStatusSearch.visibility = View.GONE
            binding.loadingSearch.visibility = View.VISIBLE
        } else {
            binding.tvStatusSearch.visibility = View.GONE
            binding.loadingSearch.visibility = View.GONE
            binding.tvGithubUser.visibility = View.VISIBLE
            binding.rvGithubUser.visibility = View.VISIBLE
        }
    }

    private fun showErrorMessage(message: String) {
        binding.tvGithubUser.visibility = View.GONE
        binding.rvGithubUser.visibility = View.GONE
        binding.tvStatusSearch.visibility = View.VISIBLE
        binding.tvStatusSearch.text = message
    }
}