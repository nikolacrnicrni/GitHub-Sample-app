package com.example.githubapp.data.remote

import com.example.githubapp.data.remote.dto.RepoDetailDto
import com.example.githubapp.data.remote.dto.RepoDto
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Observable<RepoDto>

    @GET("repos/{owner}/{repo}")
    fun getRepositoryByOwner(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Single<RepoDetailDto>
}
