package com.example.githubapp.data.repository

import com.example.githubapp.data.local.GitHubDatabase
import com.example.githubapp.data.local.dao.GitHubRepositoriesDao
import com.example.githubapp.data.remote.ApiService
import org.mockito.Mockito.*

class RemoteRepoImplTest {

    private val api = mock(ApiService::class.java)
    private val loginApi = mock(ApiService::class.java)
    private val db = mock(GitHubDatabase::class.java)
    private val gitHubRepositoriesDao = mock(GitHubRepositoriesDao::class.java)
    private val remoteRepo = RemoteRepoImpl(api, loginApi, db)

//    @Before
//    fun setUp() {
//        `when`(db.gitHubRepositoriesDao).thenReturn(gitHubRepositoriesDao)
//    }
//
//    @Test
//    fun `getRepos should return a list of repos from the API`() {
//        val gitRepoList = listOf(
//            GitRepo(
//                1,
//                10,
//                "Repo1",
//                5,
//                20,
//                "www.example.com",
//                10,
//                "2022-01-01T12:00:00Z",
//                "Repo1",
//                100,
//                "abc123",
//                1,
//                Owner(
//                    id = 1,
//                    login = "test_user",
//                    nodeId = "test_node_id",
//                    avatarUrl = "http://test.com/avatar",
//                    followersUrl = "http://test.com/followers",
//                    followingUrl = "http://test.com/following",
//                    gravatarId = "test_gravatar_id",
//                    htmlUrl = "http://test.com/test_user",
//                    reposUrl = "http://test.com/test_user/repos",
//                    type = "User",
//                    url = "http://test.com/test_user"
//                ),
//                "Java",
//                "2022-01-01T12:00:00Z"
//            )
//        )
//        val observable = Observable.just(gitRepoList)
//        `when`(
//            api.getRepositories(
//                q = "",
//                sort = "",
//                order = "",
//                perPage = 0,
//                page = 0
//            )
//        ).thenReturn(observable.map {  })
//
//        val testObserver = remoteRepo.getRepos("", "", "", 0, 0).test()
//
//        testObserver.assertValue(gitRepoList)
//    }
//
//    @Test
//    fun `getRepos should return a list of repos from the database if API call fails`() {
//        val gitRepoList = listOf(
//            GitRepo(
//                1,
//                10,
//                "Repo1",
//                5,
//                20,
//                "www.example.com",
//                10,
//                "2022-01-01T12:00:00Z",
//                "Repo1",
//                100,
//                "abc123",
//                1,
//                Owner(
//                    id = 1,
//                    login = "test_user",
//                    nodeId = "test_node_id",
//                    avatarUrl = "http://test.com/avatar",
//                    followersUrl = "http://test.com/followers",
//                    followingUrl = "http://test.com/following",
//                    gravatarId = "test_gravatar_id",
//                    htmlUrl = "http://test.com/test_user",
//                    reposUrl = "http://test.com/test_user/repos",
//                    type = "User",
//                    url = "http://test.com/test_user"
//                ),
//                "Java",
//                "2022-01-01T12:00:00Z"
//            )
//        )
//        val reposResponse = (gitRepoList)
//        val observable = Observable.error(Throwable())
//        `when`(
//            api.getRepositories(
//                q = "",
//                sort = "",
//                order = "",
//                perPage = 0,
//                page = 0
//            )
//        ).thenReturn(observable)
//        `when`(gitHubRepositoriesDao.getRepositories()).thenReturn(gitRepoList.map { it.toRepositoryEntity() })
//
//        val testObserver = remoteRepo.getRepos("", "", "", 0, 0).test()
//
//        testObserver.assertValue(gitRepoList)
//    }
}
