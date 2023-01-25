package com.example.githubapp.di

import com.example.githubapp.domain.repository.RemoteRepo
import com.example.githubapp.domain.use_case.repositories.GetGitRepoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetGitHubRepoListUseCase(repository: RemoteRepo): GetGitRepoUseCase {
        return GetGitRepoUseCase(repository)
    }
}
