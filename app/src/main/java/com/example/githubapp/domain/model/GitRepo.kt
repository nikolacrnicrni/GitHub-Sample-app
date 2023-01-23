package com.example.githubapp.domain.model

import com.example.githubapp.data.remote.dto.OwnerDto

data class GitRepo(
    val forksCount: Int,
    val name: String,
    val openIssuesCount: Int,
    val owner: OwnerDto,
    val watchersCount: Int,
    val htmlUrl: String
)
