package com.example.githubapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapp.domain.model.AccessToken
import com.example.githubapp.domain.repository.RemoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: RemoteRepo) : ViewModel() {
    private val disposables = CompositeDisposable()
    private val _accessTokenLiveData = MutableLiveData<AccessToken>()

    val accessTokenLiveData: LiveData<AccessToken>
        get() = _accessTokenLiveData

    private val _stateLiveData = MutableLiveData<State>()
    val stateLiveData: LiveData<State>
        get() = _stateLiveData

    private val _stateSplashLiveData = MutableLiveData<State>()
    val stateSplashLiveData: LiveData<State>
        get() = _stateSplashLiveData

    fun getNewAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        grantType: String
    ) {
        disposables.add(
            repository.getAccessToken(code, clientId, clientSecret, redirectUri, grantType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _accessTokenLiveData.value = it
                    _stateLiveData.value = State.SUCCESS
                }, {
                    _stateLiveData.value = State.ERROR
                })
        )
    }

    fun checkIfTokenValid(accessToken: String) {
        disposables.add(
            repository.checkTokenValidity(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _stateSplashLiveData.value = State.SUCCESS
                }, {
                    _stateSplashLiveData.value = State.ERROR
                })
        )
    }

    public override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

enum class State {
    LOADING,
    SUCCESS,
    ERROR
}
