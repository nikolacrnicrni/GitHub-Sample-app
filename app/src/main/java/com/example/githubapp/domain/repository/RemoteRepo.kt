package com.example.githubapp.domain.repository

import com.example.githubapp.data.remote.dto.RepoDetailDto
import com.example.githubapp.domain.model.AccessToken
import com.example.githubapp.domain.model.GitRepo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface RemoteRepo {
    fun getRepos(
        q: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Observable<List<GitRepo>>

    fun getRepoDetail(owner: String, repo: String): Single<RepoDetailDto>

    fun getSpecificRepoDetail(repoId: Int): Single<List<GitRepo>>

    fun setToFavourite(repoId: Int): Observable<Int>

    fun getFavourite(): Observable<List<GitRepo>>

    fun getAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String
    ): Observable<AccessToken>

    fun checkTokenValidity(accessToken: String): Observable<Unit>
}
