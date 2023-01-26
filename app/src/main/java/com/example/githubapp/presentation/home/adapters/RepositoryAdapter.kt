package com.example.githubapp.presentation.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapp.R
import com.example.githubapp.databinding.SearchResultItemBinding
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.presentation.home.RepoClickListener
import com.example.githubapp.util.extensions.loadImage
import com.example.githubapp.util.extensions.setSafeOnClickListener

class RepositoryAdapter(val onclick: RepoClickListener) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    var repositories: MutableList<GitRepo> = mutableListOf()

    inner class RepositoryViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)
        return RepositoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = repositories[position]

        val binding = SearchResultItemBinding.bind(holder.itemView)
        binding.repositoryName.text = repository.name
        binding.repositoryAuthor.text = repository.owner.login
        binding.repositoryThumbnail.loadImage(repository.owner.avatarUrl)
        binding.repositoryWatchers.text = repository.watchersCount.toString()
        binding.repositoryForks.text = repository.forksCount.toString()
        binding.repositoryIssues.text = repository.openIssuesCount.toString()

        holder.itemView.setSafeOnClickListener {
            onclick.repoClicked(repository)
        }
    }

    override fun getItemCount() = repositories.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<GitRepo>) {
        repositories.clear()
        repositories.addAll(list)
        notifyDataSetChanged()
    }
}
