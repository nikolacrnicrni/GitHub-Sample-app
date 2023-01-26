package com.example.githubapp.data.local.dao

import androidx.room.*
import com.example.githubapp.data.local.entitiy.RepositoryEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface GitHubRepositoriesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositories(repositories: List<RepositoryEntity>): Completable

    @Update
    fun updateFavourite(repository: RepositoryEntity): Single<Int>

    @Query("SELECT * FROM repositories WHERE id = :repositoryId")
    fun getRepositoryById(repositoryId: Int): Flowable<RepositoryEntity>

    @Query("SELECT * FROM repositories ORDER BY stargazersCount DESC")
    fun getRepositoriesSortedByStars(): Flowable<List<RepositoryEntity>>

    @Query("SELECT * FROM repositories ORDER BY forksCount DESC")
    fun getRepositoriesSortedByForks(): Flowable<List<RepositoryEntity>>

    @Query("SELECT * FROM repositories ORDER BY updatedAt DESC")
    fun getRepositoriesSortedByUpdated(): Flowable<List<RepositoryEntity>>

    @Query("SELECT * FROM repositories WHERE id =:repoId")
    fun getRepositoryDetailsByRepoId(repoId: Int): List<RepositoryEntity>

    @Query("SELECT * FROM repositories")
    fun getRepositories(): List<RepositoryEntity>
}
