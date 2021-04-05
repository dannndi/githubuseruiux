package com.example.githubuseruiux.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuseruiux.R
import com.example.githubuseruiux.databinding.GithubUserItemBinding
import com.example.githubuseruiux.model.User

class GithubUserAdapter :
    RecyclerView.Adapter<GithubUserAdapter.ViewHolder>() {

    private val githubUsers = ArrayList<User>()
    lateinit var onClick: (Int) -> Unit

    fun setGithubUsers(users: ArrayList<User>) {
        githubUsers.clear()
        githubUsers.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GithubUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(githubUsers[position])
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = githubUsers.size

    class ViewHolder(private val binding: GithubUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(user.image)
                    .placeholder(R.drawable.ic_github)
                    .error(R.drawable.ic_github)
                    .into(ivGithubImage)
                tvGithubUsername.text = user.username
            }
        }
    }
}