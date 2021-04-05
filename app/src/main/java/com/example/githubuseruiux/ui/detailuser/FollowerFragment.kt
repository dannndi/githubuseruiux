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
import com.example.githubuseruiux.databinding.FragmentFollowerBinding
import com.example.githubuseruiux.model.User

class FollowerFragment : Fragment() {

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowerFragment {
            val fragment = FollowerFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var githubUserAdapter: GithubUserAdapter
    private val githubUsers = ArrayList<User>()
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = arguments?.getString(ARG_USERNAME).toString()

        viewModel = ViewModelProvider(
            this,
        ).get(DetailUserViewModel::class.java)

        showLoading(true)
        viewModel.setListFollowers(username)

        githubUserAdapter = GithubUserAdapter()
        binding.rvGithubUserFollowers.apply {
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

        viewModel.listFollowers.observe(viewLifecycleOwner) {
            showLoading(false)
            githubUsers.addAll(it)
            githubUserAdapter.setGithubUsers(it)
            if (it.size == 0) {
                showFollowerStatus()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) {
            binding.loadingFollowers.visibility = View.VISIBLE
            binding.rvGithubUserFollowers.visibility = View.GONE
            binding.tvFollowerStatus.visibility = View.GONE

        } else {
            binding.loadingFollowers.visibility = View.GONE
            binding.tvFollowerStatus.visibility = View.GONE
            binding.rvGithubUserFollowers.visibility = View.VISIBLE
        }
    }

    private fun showFollowerStatus(){
        binding.loadingFollowers.visibility = View.GONE
        binding.rvGithubUserFollowers.visibility = View.GONE
        binding.tvFollowerStatus.visibility = View.VISIBLE
    }
}