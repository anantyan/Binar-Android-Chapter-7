package id.anantyan.moviesapp.repository

import id.anantyan.moviesapp.data.local.UsersDao
import id.anantyan.moviesapp.data.local.model.UsersLocal
import id.anantyan.utils.sharedPreferences.DataStoreManager
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.mock

class UsersLocalRepositoryTest {

    private lateinit var dao: UsersDao
    private lateinit var store: DataStoreManager
    private lateinit var repo: UsersLocalRepository

    @Before
    fun setUp() {
        dao = mock()
        store = mock()
        repo = UsersLocalRepository(dao, store)
    }

    @Test
    fun getAccount() = runBlocking {
        val correct = mockk<UsersLocal>()
        Mockito.`when`(dao.showAccount(any())).thenReturn(correct)
        val response = repo.getAccount()
        assertEquals(response, correct)
    }

    @Test
    fun login() = runBlocking {
        val correct = mockk<UsersLocal>()
        Mockito.`when`(dao.login("abc@email.com", "123")).thenReturn(correct)
        val response = repo.login("abc@email.com", "123")
        assertEquals(response, correct)
    }

    @Test
    fun checkAccountByEmail() = runBlocking {
        val correct = mockk<UsersLocal>()
        Mockito.`when`(dao.checkAccount(username = null, "abc@email.com")).thenReturn(correct)
        val response = repo.checkAccountByEmail("abc@email.com")
        assertEquals(response, correct)
    }
}