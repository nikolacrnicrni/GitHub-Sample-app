package com.example.githubapp.di

import android.content.Context
import androidx.room.Room
import com.example.githubapp.BuildConfig
import com.example.githubapp.data.local.GitHubDatabase
import com.example.githubapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @DatabaseName
    fun provideDatabaseName() = Constants.DB_NAME

    @Provides
    @Singleton
    fun provideGitHubDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): GitHubDatabase {
        return Room.databaseBuilder(
            context.applicationContext, GitHubDatabase::class.java, databaseName
        ).fallbackToDestructiveMigration()
            .apply {
                if (BuildConfig.DEBUG) {
                    allowMainThreadQueries()
                }
            }
            .build()
    }
}
