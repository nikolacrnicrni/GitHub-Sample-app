package com.example.githubapp.data.remote.dto

import com.example.githubapp.domain.model.RepoSource
import com.google.gson.annotations.SerializedName

data class RepoDto(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<ItemDto>,
    @SerializedName("total_count")
    val totalCount: Int
) {
    fun toGitRepo(): RepoSource {
        return RepoSource(
            items = items.map { it.getItems() }
        )
    }
}
