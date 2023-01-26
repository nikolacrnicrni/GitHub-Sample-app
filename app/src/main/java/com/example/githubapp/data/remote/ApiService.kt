package com.example.githubapp.data.remote

import com.example.githubapp.data.remote.dto.RepoDetailDto
import com.example.githubapp.data.remote.dto.RepoDto
import com.example.githubapp.domain.model.AccessToken
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("/login/oauth/access_token")
    fun getNewAccessToken(
        @Field("code") code: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("redirect_uri") redirectUri: String?,
        @Field("grant_type") grantType: String?
    ): Observable<AccessToken>

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
