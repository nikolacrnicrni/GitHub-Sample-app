package com.example.githubapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.githubapp.databinding.FragmentRepositoriesBinding
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.presentation.home.adapters.RepositoryAdapter
import com.example.githubapp.util.Constants.EMPTY
import com.example.githubapp.util.Constants.FORKS
import com.example.githubapp.util.Constants.STARS
import com.example.githubapp.util.Constants.UPDATED
import com.example.githubapp.util.extensions.showErrorSnackbar
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class RepositoryFragment : Fragment(), RepoClickListener {
    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    private lateinit var disposable: Disposable
    private lateinit var adapter: RepositoryAdapter
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchRepository()
        disposable = binding.searchField.textChanges()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribe { text ->
                viewModel.searchRepository(text.toString())
            }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.progressBar.root.visibility = View.GONE
            when (state) {
                is GitHubRepoState.GitHubRepoStateSuccess -> {
                    adapter.updateList(state.gitRepoResult)
                }
                is GitHubRepoState.GitHubRepoStateIsLoading -> {
                    binding.progressBar.root.visibility = View.VISIBLE
                }
                else -> {
                    view.showErrorSnackbar("Offline mode!")
                }
            }
        }

        viewModel.sortingState.observe(viewLifecycleOwner) { sortingState ->
            when (sortingState) {
                SortingRepos.STARS -> {
                    viewModel.searchRepository(binding.searchField.text.toString(), STARS)
                }
                SortingRepos.FORKS -> {
                    viewModel.searchRepository(binding.searchField.text.toString(), FORKS)
                }
                SortingRepos.UPDATED -> {
                    viewModel.searchRepository(binding.searchField.text.toString(), UPDATED)
                }
                else -> {
                    viewModel.searchRepository(binding.searchField.text.toString(), EMPTY)
                }
            }
        }

        adapter = RepositoryAdapter(this)
        binding.searchResultsList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        _binding = null
    }

    override fun repoClicked(item: GitRepo) {
        TODO("Not yet implemented")
    }
}
