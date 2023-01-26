package com.example.githubapp.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.domain.repository.RemoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    private val remoteRepo: RemoteRepo
) : ViewModel() {

    private val _state = MutableLiveData<GitHubRepoDetailsState>()
    val state: MutableLiveData<GitHubRepoDetailsState> = _state
    private val _stateFav = MutableLiveData<FavouriteState>()
    val stateFav: MutableLiveData<FavouriteState> = _stateFav
    private val disposable = CompositeDisposable()

    fun getRepoDetails(repoId: Int) {
        disposable.add(
            remoteRepo.getSpecificRepoDetail(repoId).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repos ->
                    _state.postValue(GitHubRepoDetailsState.GitHubRepoDetailsStateSuccess(repos.firstOrNull()))
                }, {
                    _state.postValue(GitHubRepoDetailsState.GitHubRepoDetailsStateError)
                })
        )
    }

    fun addToFavourites(repoId: Int) {
        disposable.add(
            remoteRepo.setToFavourite(repoId).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _stateFav.postValue(FavouriteState.SAVED)
                }, {
                    _stateFav.postValue(FavouriteState.NOT)
                })
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}

enum class FavouriteState {
    SAVED, NOT
}

sealed class GitHubRepoDetailsState {
    data class GitHubRepoDetailsStateSuccess(val gitRepoResult: GitRepo?) : GitHubRepoDetailsState()
    object GitHubRepoDetailsStateError : GitHubRepoDetailsState()
}
