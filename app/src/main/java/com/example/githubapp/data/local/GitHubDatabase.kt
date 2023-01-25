package com.example.githubapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubapp.data.local.dao.GitHubRepositoriesDao
import com.example.githubapp.data.local.entitiy.RepositoryEntity

@Database(
    entities = [RepositoryEntity::class],
    version = 1
)
abstract class GitHubDatabase : RoomDatabase() {

    abstract val gitHubRepositoriesDao: GitHubRepositoriesDao
}
