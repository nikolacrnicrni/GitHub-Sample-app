package com.example.githubapp.data.repository

import com.example.githubapp.data.local.GitHubDatabase
import com.example.githubapp.data.remote.ApiService
import com.example.githubapp.data.remote.dto.RepoDetailDto
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.domain.model.GitRepo.Companion.toRepositoryEntity
import com.example.githubapp.domain.repository.RemoteRepo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class RemoteRepoImpl @Inject constructor(
    private val api: ApiService,
    private val db: GitHubDatabase
) : RemoteRepo {

    private lateinit var subscription: Disposable
    private val gitRepoState = BehaviorSubject.create<List<GitRepo>>()

    override fun getRepos(
        q: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Observable<List<GitRepo>> {
        val oldRepos = db.gitHubRepositoriesDao.getRepositories().map { it.toGitRepo() }
        subscription = api.getRepositories(
            q = q,
            sort = sort,
            order = order,
            perPage = perPage,
            page = page
        ).subscribeOn(
            Schedulers.io()
        ).subscribe(
            { repoData ->
                db.gitHubRepositoriesDao.insertRepositories(repoData.toGitRepo().items.map { it.toRepositoryEntity() })
                    .doOnComplete {
                        gitRepoState.onNext(repoData.toGitRepo().items)
                    }
                    .doOnError {
                        gitRepoState.onNext(oldRepos)
                    }
                    .subscribe()
            },
            {
                gitRepoState.onNext(oldRepos)
            }
        )

        return gitRepoState
    }

    override fun getSpecificRepoDetail(repoId: Int): Single<List<GitRepo>> {
        val oldRepos = db.gitHubRepositoriesDao.getRepositoryDetailsByRepoId(repoId).map { it.toGitRepo() }
        return Single.just(oldRepos)
    }

    fun dispose() {
        if (::subscription.isInitialized && !subscription.isDisposed) {
            subscription.dispose()
        }
    }

    override fun getRepoDetail(owner: String, repo: String): Single<RepoDetailDto> {
        TODO("Not yet implemented")
    }
}
