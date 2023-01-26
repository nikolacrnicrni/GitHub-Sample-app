package com.example.githubapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.domain.use_case.repositories.GetGitRepoUseCase
import com.example.githubapp.util.Constants
import com.example.githubapp.util.Constants.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gitRepoUseCase: GetGitRepoUseCase
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _state = MutableLiveData<GitHubRepoState>()
    val state: MutableLiveData<GitHubRepoState> = _state

    private val _sortingState = MutableLiveData<SortingRepos>()
    val sortingState: MutableLiveData<SortingRepos> = _sortingState

    init {
        searchRepository()
    }

    fun searchRepository(query: String = EMPTY, sort: String = EMPTY) {
        _state.postValue(GitHubRepoState.GitHubRepoStateIsLoading)
        disposable.add(
            gitRepoUseCase(
                q = query.ifEmpty { "pulse" },
                order = "desc",
                perPage = Constants.PAGE_SIZE,
                page = Constants.PAGE,
                sort = sort
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repos ->
                    _state.postValue(GitHubRepoState.GitHubRepoStateSuccess(repos))
                }, {
                    _state.postValue(GitHubRepoState.GitHubRepoStateError)
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

enum class SortingRepos {
    STARS, FORKS, UPDATED, RESET
}
