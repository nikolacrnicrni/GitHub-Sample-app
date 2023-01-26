package com.example.githubapp.presentation.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.githubapp.databinding.FragmentFavouritesBinding
import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.presentation.details.RepoDetailsActivity
import com.example.githubapp.presentation.home.FavouriteRepoState
import com.example.githubapp.presentation.home.HomeViewModel
import com.example.githubapp.presentation.home.RepoClickListener
import com.example.githubapp.presentation.home.adapters.RepositoryAdapter
import com.example.githubapp.util.extensions.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class FavoritesFragment : Fragment(), RepoClickListener {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private var disposable: Disposable? = null
    private lateinit var adapter: RepositoryAdapter
    private val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavourites()

        viewModel.stateFav.observe(viewLifecycleOwner) { state ->
            when (state) {
                is FavouriteRepoState.FavouriteRepoStateSuccess -> {
                    adapter.updateList(state.gitRepoResult)
                }
                else -> {
                    view.showErrorSnackbar("Offline mode!")
                }
            }
        }

        adapter = RepositoryAdapter(this)
        binding.favResultsList.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        adapter?.onclick = null
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
        _binding = null
    }

    override fun repoClicked(item: GitRepo) {
        startActivity(RepoDetailsActivity.getIntent(requireContext(), item.id))
    }
}
