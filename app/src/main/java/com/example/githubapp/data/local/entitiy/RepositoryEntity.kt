package com.example.githubapp.data.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubapp.domain.model.GitRepo

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var fullName: String,
    var updatedAt: String,
    var stargazersCount: Int,
    var forksCount: Int,
    var openIssuesCount: Int,
    var language: String,
    var createdAt: String,
    var watchersCount: Int,
    var htmlUrl: String,
    @ColumnInfo(name = "repositoryHtmlUrl") var ownerId: Int,
    var nodeId: String,
    @Embedded val owner: OwnerEntity
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
            fullName = fullName,
            stargazersCount = stargazersCount,
            forks_count = forksCount,
            nodeId = nodeId,
            ownerId = ownerId,
            owner = owner.toOwner(),
            language = language,
            createdAt = createdAt
        )
    }
}
