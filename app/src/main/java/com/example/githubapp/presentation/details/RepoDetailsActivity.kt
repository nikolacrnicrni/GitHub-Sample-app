package com.example.githubapp.presentation.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapp.databinding.ActivityRepoDetailsBinding
import com.example.githubapp.util.Utils
import com.example.githubapp.util.extensions.loadImage
import com.example.githubapp.util.extensions.setSafeOnClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepoDetailsActivity : AppCompatActivity() {
    private var _binding: ActivityRepoDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RepoDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityRepoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.getRepoDetails(intent.getIntExtra(REPO_DETAILS, 0))

        viewModel.state.observe(this) { state ->
            when (state) {
                is GitHubRepoDetailsState.GitHubRepoDetailsStateSuccess -> {
                    binding.repoName.setResult(state.gitRepoResult?.name.toString())
                    binding.repoStarsResult.setResult(state.gitRepoResult?.stargazersCount.toString())
                    binding.repoForksResult.setResult(state.gitRepoResult?.forksCount.toString())
                    binding.repoIssuesResult.setResult(state.gitRepoResult?.openIssuesCount.toString())
                    binding.repoLanguageResult.setResult(state.gitRepoResult?.language.toString())
                    binding.repoUpdatedResult.setResult(Utils.convertStringToDate(state.gitRepoResult?.updatedAt.toString()))
                    binding.repoCreatedResult.setResult(Utils.convertStringToDate(state.gitRepoResult?.createdAt.toString()))
                    binding.repoOwnerResult.text = state.gitRepoResult?.owner?.login
                    binding.authorThumbImg.loadImage(state.gitRepoResult?.owner?.avatarUrl.toString())
                    binding.openExternal.setSafeOnClickListener {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(state.gitRepoResult?.htmlUrl)
                            )
                        )
                    }
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
        private const val REPO_DETAILS = "repo_details"

        fun getIntent(context: Context, repoId: Int) =
            Intent(context, RepoDetailsActivity::class.java).apply {
                putExtra(REPO_DETAILS, repoId)
            }
    }
}
