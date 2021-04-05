package com.example.githubuseruiux.ui.detailuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuseruiux.adapter.GithubUserAdapter
import com.example.githubuseruiux.databinding.FragmentFollowingBinding
import com.example.githubuseruiux.model.User

class FollowingFragment : Fragment() {

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var githubUserAdapter: GithubUserAdapter
    private val githubUsers = ArrayList<User>()
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(ARG_USERNAME).toString()
        viewModel = ViewModelProvider(
            this,
        ).get(DetailUserViewModel::class.java)

        showLoading(true)
        viewModel.setListFollowing(username)

        githubUserAdapter = GithubUserAdapter()
        binding.rvGithubUserFollowing.apply {
            adapter = githubUserAdapter
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
        }

        githubUserAdapter.onClick = { position ->
            Intent(activity, DetailUserActivity::class.java).apply {
                putExtra(DetailUserActivity.USER_EXTRA, githubUsers[position])
                startActivity(this)
            }
        }

        viewModel.listFollowing.observe(viewLifecycleOwner) {
            showLoading(false)
            githubUsers.addAll(it)
            githubUserAdapter.setGithubUsers(it)
            if (it.size == 0) {
                showFollowingStatus()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) {
            binding.loadingFollowing.visibility = View.VISIBLE
            binding.rvGithubUserFollowing.visibility = View.GONE
            binding.tvFollowingStatus.visibility = View.GONE

        } else {
            binding.loadingFollowing.visibility = View.GONE
            binding.tvFollowingStatus.visibility = View.GONE
            binding.rvGithubUserFollowing.visibility = View.VISIBLE
        }
    }

    private fun showFollowingStatus() {
        binding.loadingFollowing.visibility = View.GONE
        binding.rvGithubUserFollowing.visibility = View.GONE
        binding.tvFollowingStatus.visibility = View.VISIBLE
    }
}