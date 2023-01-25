package com.example.githubapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.domain.use_case.repositories.GetGitRepoUseCase
import com.example.githubapp.util.Constants
import com.example.githubapp.util.Constants.EMPTY
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val gitRepoUseCase: GetGitRepoUseCase
) : ViewModel() {
    private val disposable = CompositeDisposable()

    private val _state = MutableLiveData<GitHubRepoState>()
    val state: MutableLiveData<GitHubRepoState> = _state

    init {
        searchRepository()
    }

    private fun searchRepository(query: String = EMPTY,) {
        _state.postValue(GitHubRepoState.GitHubRepoStateIsLoading)
        disposable.add(
            gitRepoUseCase(
                q = query.ifEmpty { "pulse" },
                order = "desc",
                perPage = Constants.PAGE_SIZE,
                page = Constants.PAGE,
                sort = EMPTY
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
