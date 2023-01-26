package com.example.githubapp.presentation.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapp.BuildConfig
import com.example.githubapp.databinding.ActivitySplashBinding
import com.example.githubapp.presentation.home.HomeActivity
import com.example.githubapp.presentation.login.LoginActivity
import com.example.githubapp.presentation.login.LoginViewModel
import com.example.githubapp.presentation.login.State
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val prefs = getSharedPreferences(
            BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE
        )

        val token = prefs.getString("access_token", "")
        token?.let { auth_token ->
            viewModel.checkIfTokenValid("Bearer $auth_token")
        }

        viewModel.stateSplashLiveData.observe(this) {
            when (it) {
                State.SUCCESS -> {
                    startActivity(HomeActivity.getIntent(this))
                }
                State.ERROR -> {
                    startActivity(LoginActivity.getIntent(this))
                }
                else -> {
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, SplashScreenActivity::class.java)
    }
}
