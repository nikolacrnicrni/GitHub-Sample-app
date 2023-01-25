package com.example.githubapp.di

import com.example.githubapp.data.local.GitHubDatabase
import com.example.githubapp.data.remote.ApiService
import com.example.githubapp.data.repository.RemoteRepoImpl
import com.example.githubapp.domain.repository.RemoteRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideGitRepoRepository(api: ApiService, db: GitHubDatabase): RemoteRepo {
        return RemoteRepoImpl(api = api, db = db)
    }
}
