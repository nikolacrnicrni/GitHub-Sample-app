package com.example.githubapp.domain.model

import com.example.githubapp.data.local.entitiy.RepositoryEntity

data class GitRepo(
    val id: Int,
    val forksCount: Int,
    val name: String,
    val openIssuesCount: Int,
    val watchersCount: Int,
    val htmlUrl: String,
    val forks_count: Int,
    val updatedAt: String,
    val fullName: String,
    val stargazersCount: Int
) {
    companion object {
        fun GitRepo.toRepositoryEntity(): RepositoryEntity {
            return RepositoryEntity(
                id = id,
                name = name,
                full_name = fullName,
                html_url = htmlUrl,
                updatedAt = updatedAt,
                stargazersCount = stargazersCount,
                watchersCount = watchersCount,
                htmlUrl = htmlUrl,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount,
            )
        }
    }
}
