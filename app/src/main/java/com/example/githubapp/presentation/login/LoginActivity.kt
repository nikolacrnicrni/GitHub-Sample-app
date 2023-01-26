package com.example.githubapp.presentation.login

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapp.BuildConfig
import com.example.githubapp.databinding.ActivityLoginBinding
import com.example.githubapp.presentation.home.HomeActivity
import com.example.githubapp.util.extensions.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val clientId = "bc641c51635b0687017f"
    private val redirectUri = "demo://callback"
    private val clientSecret = "536fb2e98e17cd78829243d097fa6b9d0b2d31b9"
    private val grantType = "authorization_code"

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = getSharedPreferences(
            BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE
        )

        binding.buttonLogin.setSafeOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/login/oauth/authorize?client_id=$clientId&scope=repo&redirect_uri=$redirectUri")
                )
            )
        }

        viewModel.stateLiveData.observe(this) {
            when (it) {
                State.LOADING -> {
                    // Show a loading indicator
                }
                State.SUCCESS -> {
                    // Hide the loading indicator and save the access token
                    val accessToken = viewModel.accessTokenLiveData.value
                    if (accessToken != null) {
                        prefs.edit().putString("access_token", accessToken.access_token).apply()
                        startActivity(HomeActivity.getIntent(this))
                        finish()
                    }
                }
                State.ERROR -> {
                    // Hide the loading indicator and show an error message
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val uri = intent.data
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            val code = uri.getQueryParameter("code")
            if (code != null) {
                viewModel.getNewAccessToken(code, clientId, clientSecret, redirectUri, grantType)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }
}
