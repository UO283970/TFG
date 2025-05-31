package com.example.tfg.profile.configuration

import com.example.tfg.model.security.TokenRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.profile.components.configuration.ConfigurationScreenViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class ConfigurationScreenViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: ConfigurationScreenViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var tokenRepository: TokenRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        userRepository = mockk()
        tokenRepository = mockk()
        viewModel = ConfigurationScreenViewModel(
            userRepository,
            tokenRepository
        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `logout test`(){
        coEvery { userRepository.logout() } returns "any"
        coEvery { tokenRepository.clearTokens() } returns

        viewModel.logout()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.confState.toLogin)
    }

    @Test
    fun `toggle dialog`(){
        assert(!viewModel.confState.deleteDialog)
        viewModel.toggleDeleteDialog()
        assert(viewModel.confState.deleteDialog)
    }

    @Test
    fun `delete test`(){
        coEvery { userRepository.deleteUser() } returns true
        coEvery { tokenRepository.clearTokens() } returns

        viewModel.deleteAccount()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.confState.toLogin)
    }
}