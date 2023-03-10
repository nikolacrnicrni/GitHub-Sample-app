package com.example.githubapp.data.repository

import com.example.githubapp.data.local.GitHubDatabase
import com.example.githubapp.data.remote.ApiService
import com.example.githubapp.data.remote.dto.RepoDetailDto
import com.example.githubapp.di.LoginApi
import com.example.githubapp.di.RepoApi
import com.example.githubapp.domain.model.AccessToken
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
    @RepoApi private val api: ApiService,
    @LoginApi private val loginApi: ApiService,
    private val db: GitHubDatabase
) : RemoteRepo {

    private lateinit var subscription: Disposable
    private val gitRepoState = BehaviorSubject.create<List<GitRepo>>()
    private val gitFavouritesRepoState = BehaviorSubject.create<List<GitRepo>>()
    private val gitRepoStateFavorite = BehaviorSubject.create<Int>()

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

    override fun setToFavourite(repoId: Int): Observable<Int> {
        val repository = db.gitHubRepositoriesDao.getRepositoryById(repoId)
            .subscribeOn(Schedulers.io())
            .subscribe {
                it.favouriteRepo = 1
                db.gitHubRepositoriesDao.updateFavourite(it)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
                gitRepoStateFavorite.onNext(1)
            }
        return gitRepoStateFavorite
    }

    override fun getFavourite(): Observable<List<GitRepo>> {
        val oldRepos = db.gitHubRepositoriesDao.getFavouriteRepos().toObservable().subscribe { favourites ->
            gitFavouritesRepoState.onNext(favourites.map { it.toGitRepo() })
        }

        return gitFavouritesRepoState
    }

    override fun getAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String
    ): Observable<AccessToken> {
        return loginApi.getNewAccessToken(code, clientId, clientSecret, redirectUri, grantType)
    }

    override fun checkTokenValidity(accessToken: String): Observable<Unit> {
        return loginApi.checkTokenValidity(accessToken)
    }

    override fun getRepoDetail(owner: String, repo: String): Single<RepoDetailDto> {
        TODO("Not yet implemented")
    }
}
