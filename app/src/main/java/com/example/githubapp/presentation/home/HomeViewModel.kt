package com.example.githubapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.domain.repository.RemoteRepo
import com.example.githubapp.util.Constants
import com.example.githubapp.util.Constants.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val remoteRepo: RemoteRepo
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _state = MutableLiveData<GitHubRepoState>()
    val state: MutableLiveData<GitHubRepoState> = _state

    private val _stateFav = MutableLiveData<FavouriteRepoState>()
    val stateFav: MutableLiveData<FavouriteRepoState> = _stateFav

    private val _sortingState = MutableLiveData<SortingRepos>()
    val sortingState: MutableLiveData<SortingRepos> = _sortingState

    fun searchRepository(query: String = EMPTY, sort: String = EMPTY) {
        _state.postValue(GitHubRepoState.GitHubRepoStateIsLoading)
        disposable.add(
            remoteRepo.getRepos(
                q = query.ifEmpty { "pulse" },
                order = "desc",
                perPage = Constants.PAGE_SIZE,
                page = Constants.PAGE,
                sort = sort
            )
                .observeOn(Schedulers.io())
                .subscribe({ repos ->
                    _state.postValue(GitHubRepoState.GitHubRepoStateSuccess(repos))
                }, {
                    _state.postValue(GitHubRepoState.GitHubRepoStateError)
                })
        )
    }

    fun getFavourites() {
        disposable.add(
            remoteRepo.getFavourite()
                .observeOn(Schedulers.io())
                .subscribe({ repos ->
                    _stateFav.postValue(FavouriteRepoState.FavouriteRepoStateSuccess(repos))
                }, {
                    _stateFav.postValue(FavouriteRepoState.FavouriteRepoStateError)
                })
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}

sealed class GitHubRepoState {
    data class GitHubRepoStateSuccess(val gitRepoResult: List<GitRepo>) : GitHubRepoState()
    object GitHubRepoStateError : GitHubRepoState()
    object GitHubRepoStateIsLoading : GitHubRepoState()
}
sealed class FavouriteRepoState {
    data class FavouriteRepoStateSuccess(val gitRepoResult: List<GitRepo>) : FavouriteRepoState()
    object FavouriteRepoStateError : FavouriteRepoState()
}

enum class SortingRepos {
    STARS, FORKS, UPDATED, RESET
}
