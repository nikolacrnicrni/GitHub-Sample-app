package com.example.githubapp.data.remote.dto

import com.example.githubapp.domain.model.Owner
import com.example.githubapp.util.Constants.EMPTY
import com.google.gson.annotations.SerializedName

data class OwnerDto(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String? = EMPTY,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String? = EMPTY,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("organizations_url")
    val organizationsUrl: String? = EMPTY,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String? = EMPTY,
    @SerializedName("repos_url")
    val reposUrl: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean = false,
    @SerializedName("starred_url")
    val starredUrl: String? = EMPTY,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String? = EMPTY,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
) {

    fun toOwner(): Owner {
        return Owner(
            id = id,
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
