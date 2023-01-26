package com.example.githubapp.domain.model

import com.google.gson.annotations.SerializedName

class AccessToken(
    @SerializedName("access_token")
    var access_token: String
)
