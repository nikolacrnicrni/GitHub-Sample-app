package com.example.githubapp.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubapp.domain.model.GitRepo

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val full_name: String,
    val html_url: String,
    val updatedAt: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssuesCount: Int,
    val watchersCount: Int,
    val htmlUrl: String
) {
    fun toGitRepo(): GitRepo {
        return GitRepo(
            id = id,
            forksCount = forksCount,
            name = name,
            openIssuesCount = openIssuesCount,
            watchersCount = watchersCount,
            htmlUrl = htmlUrl,
            updatedAt = updatedAt,
            fullName = full_name,
            stargazersCount = stargazersCount,
            forks_count = forksCount
        )
    }
}
