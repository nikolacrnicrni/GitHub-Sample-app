package com.example.githubapp.domain.model

import com.example.githubapp.data.remote.dto.OwnerDto

data class GitRepoDetail(
    val createdAt: String,
    val forksCount: Int,
    val forksUrl: String,
    val fullName: String,
    val hasIssues: Boolean,
    val hasPages: Boolean,
    val hasProjects: Boolean,
    val id: Int,
    val language: String,
    val license: Any?,
    val name: String,
    val openIssues: Int,
    val owner: OwnerDto,
    val pushedAt: String,
    val updatedAt: String,
    val url: String,
    val watchers: Int,
    val watchersCount: Int,
    val openIssuesCount: Int,
    val description: String?,
    val htmlUrl: String?
)
