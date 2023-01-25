package com.example.githubapp.domain.model

import com.example.githubapp.data.local.entitiy.OwnerEntity

data class Owner(
    val id: Int,
    val login: String,
    val nodeId: String,
    val avatarUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gravatarId: String,
    val htmlUrl: String,
    val reposUrl: String,
    val type: String,
    val url: String
) {
    fun toOwner(): OwnerEntity {
        return OwnerEntity(
            ownerId = id,
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
