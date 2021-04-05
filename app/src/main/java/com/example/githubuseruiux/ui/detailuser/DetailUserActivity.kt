package com.example.githubuseruiux.ui.detailuser

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuseruiux.R
import com.example.githubuseruiux.databinding.ActivityDetailUserBinding
import com.example.githubuseruiux.model.User
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val USER_EXTRA = "user_extra"
    }

    private var isFavorite = false
    private lateinit var user: User
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.detail_page)

        user = intent.getSerializableExtra(USER_EXTRA) as User
        viewModel = ViewModelProvider(
            this,
        ).get(DetailUserViewModel::class.java)

        viewModel.checkIsFavorite(user.id)
        viewModel.setUser(user.username)

        viewModel.user.observe(this) { user ->
            binding.apply {
                Glide.with(this@DetailUserActivity)
                    .load(user.image)
                    .placeholder(R.drawable.ic_github)
                    .into(ivGithubImage)
                tvGithubName.text = user.name
                tvGithubUsername.text = user.username
                tvGithubFollower.text = user.followers
                tvGithubFollowing.text = user.following
                showContent()
            }
        }

        viewModel.isFavorite.observe(this) { state ->
            binding.btnFavorite.isChecked = state
            isFavorite = state
        }

        binding.btnFavorite.setOnClickListener {
            if (isFavorite) {
                viewModel.removeToFavorite(user.id)
            } else {
                viewModel.addToFavorite(user.id, user.username, user.image)
            }
        }

        val pagerAdapter = PagerAdapter(this)
        pagerAdapter.username = user.username
        binding.detailViewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.detailTabs, binding.detailViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.followers)
                1 -> tab.text = resources.getString(R.string.following)
            }
        }.attach()
    }

    private fun showContent() {
        binding.apply {
            loadingDetail.visibility = View.GONE
            ivGithubImage.visibility = View.VISIBLE
            tvGithubName.visibility = View.VISIBLE
            tvGithubUsername.visibility = View.VISIBLE
            tvGithubFollower.visibility = View.VISIBLE
            tvFollower.visibility = View.VISIBLE
            tvGithubFollowing.visibility = View.VISIBLE
            tvFollowing.visibility = View.VISIBLE
            detailTabs.visibility = View.VISIBLE
            detailViewPager.visibility = View.VISIBLE
            btnFavorite.visibility = View.VISIBLE
        }
    }
}