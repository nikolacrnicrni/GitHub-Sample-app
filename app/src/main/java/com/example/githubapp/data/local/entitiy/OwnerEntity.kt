package com.example.githubapp.data.local.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubapp.domain.model.Owner

@Entity(tableName = "owners")
data class OwnerEntity(
    @PrimaryKey val ownerId: Int,
    val login: String,
    @ColumnInfo(name = "ownerNodeId") val nodeId: String,
    val avatarUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gravatarId: String,
    @ColumnInfo(name = "ownerHtmlUrl") val htmlUrl: String,
    val reposUrl: String,
    val type: String,
    val url: String
) {
    fun toOwner(): Owner {
        return Owner(
            id = ownerId,
            login = login,
            nodeId = nodeId,
            avatarUrl = avatarUrl,
            followersUrl = followersUrl,
            followingUrl = followingUrl,
            gravatarId = gravatarId,
            htmlUrl = htmlUrl,
            reposUrl = reposUrl,
            type = type,
            url = url
        )
    }
}
