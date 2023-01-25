package com.example.githubapp.presentation.home

import com.example.githubapp.domain.model.GitRepo

interface RepoClickListener {
    fun repoClicked(item: GitRepo)
}
