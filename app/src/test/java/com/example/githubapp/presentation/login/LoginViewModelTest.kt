package com.example.githubapp.presentation.login

import android.annotation.SuppressLint
import com.example.githubapp.domain.model.AccessToken
import com.example.githubapp.domain.repository.RemoteRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class LoginViewModelTest {

    @Mock
    lateinit var repository: RemoteRepo

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = mock(RemoteRepo::class.java)
        `when`(
            repository.getAccessToken(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString()
            )
        ).thenReturn(Observable.just(AccessToken("123")))
    }

    @SuppressLint("CheckResult")
    @Test
    fun test_getAccessToken() {
        repository.getAccessToken("code", "clientId", "clientSecret", "redirectUri", "grantType")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                assertEquals("123", it.access_token)
            }, {
                Assert.fail()
            })
    }

    @Test
    fun testGetAccessToken_correctParamsPassed() {
        val code = "123"
        val clientId = "clientId"
        val clientSecret = "clientSecret"
        val redirectUri = "redirectUri"
        val grantType = "grantType"
        repository.getAccessToken(code, clientId, clientSecret, redirectUri, grantType)
        verify(repository).getAccessToken(code, clientId, clientSecret, redirectUri, grantType)
    }

    @Test
    fun testCheckIfTokenValid_error() {
        val repository = mock(RemoteRepo::class.java)
        `when`(repository.checkTokenValidity("invalid_token"))
            .thenReturn(Observable.error(Throwable()))

        val viewModel = LoginViewModel(repository)
        viewModel.checkIfTokenValid("invalid_token")

        // Verify that the repository's checkTokenValidity method is called with the correct argument
        verify(repository).checkTokenValidity("invalid_token")
    }
}
