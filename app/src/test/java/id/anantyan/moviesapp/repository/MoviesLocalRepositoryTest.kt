package id.anantyan.moviesapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import id.anantyan.moviesapp.data.local.MoviesDao
import id.anantyan.moviesapp.data.local.model.MoviesLocal
import id.anantyan.utils.sharedPreferences.DataStoreManager
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class MoviesLocalRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var dao: MoviesDao
    private lateinit var store: DataStoreManager
    private lateinit var repo: MoviesLocalRepository


    @Before
    fun setUp() {
        dao = mock()
        store = mock()
        repo = MoviesLocalRepository(dao, store)
    }

    @Test
    fun selectMovies() = runBlocking {
        val correct = mockk<LiveData<List<MoviesLocal>>>()
        Mockito.`when`(dao.selectMovies(any())).thenReturn(correct)
        val response = repo.selectMovies()
        assertEquals(response, correct)
    }
}