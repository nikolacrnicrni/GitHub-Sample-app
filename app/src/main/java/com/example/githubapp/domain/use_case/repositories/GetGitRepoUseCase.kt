package com.example.githubapp.domain.use_case.repositories

import com.example.githubapp.domain.model.GitRepo
import com.example.githubapp.domain.repository.RemoteRepo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class GetGitRepoUseCase @Inject constructor(
    private val repository: RemoteRepo
) {
    operator fun invoke(
        q: String,
        sort: String,
        order: String,
        perPage: Int,
        page: Int
    ): Observable<List<GitRepo>> {
        return repository.getRepos(
            q = q,
            sort = sort,
            order = order,
            perPage = perPage,
            page = page
        )
    }
}
