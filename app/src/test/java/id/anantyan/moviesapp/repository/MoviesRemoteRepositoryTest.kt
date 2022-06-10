package id.anantyan.moviesapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.anantyan.moviesapp.data.api.MoviesApi
import id.anantyan.moviesapp.model.Movies
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.Response

class MoviesRemoteRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var api: MoviesApi
    private lateinit var repository: MoviesRemoteRepository

    @Before
    fun setUp() {
        api = mock()
        repository = MoviesRemoteRepository(api)
    }

    @Test
    fun trendingWeek() = runBlocking {
        val responseApi = mockk<Response<Movies>>()
        Mockito.`when`(api.byTrendingWeek()).thenReturn(responseApi)
        val response = repository.trendingWeek()
        assertEquals(responseApi, response)
    }
}