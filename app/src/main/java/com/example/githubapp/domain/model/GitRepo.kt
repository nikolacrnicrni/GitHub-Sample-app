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
    val stargazersCount: Int,
    val nodeId: String,
    val ownerId: Int,
    val owner: Owner,
    val language: String,
    val createdAt: String
) {
    companion object {
        fun GitRepo.toRepositoryEntity(): RepositoryEntity {
            return RepositoryEntity(
                id = id,
                name = name,
                fullName = fullName,
                htmlUrl = htmlUrl,
                updatedAt = updatedAt,
                stargazersCount = stargazersCount,
                watchersCount = watchersCount,
                forksCount = forksCount,
                openIssuesCount = openIssuesCount,
                nodeId = nodeId,
                ownerId = ownerId,
                owner = owner.toOwner(),
                createdAt = createdAt,
                language = language
            )
        }
    }
}
