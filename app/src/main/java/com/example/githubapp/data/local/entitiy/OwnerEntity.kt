package com.example.githubapp.data.local.entitiy

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "owners")
data class OwnerEntity(
    @PrimaryKey val id: Int,
    val avatarUrl: String,
    val followersUrl: String,
    val followingUrl: String,
    val gravatarId: String,
    val htmlUrl: String,
    val login: String,
    val nodeId: String,
    val reposUrl: String,
    val type: String,
    val url: String
)
