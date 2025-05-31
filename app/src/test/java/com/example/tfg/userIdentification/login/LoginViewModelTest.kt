package com.example.tfg.userIdentification.login

import androidx.lifecycle.SavedStateHandle
import com.example.tfg.model.security.TokenRepository
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.userIdentification.login.LoginViewModel
import com.graphQL.LoginQuery
import com.graphQL.type.UserLoginErrors
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: LoginViewModel
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var userRepository: UserRepository
    private lateinit var tokenRepository: TokenRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        stringResourcesProvider = mockk()
        userRepository = mockk()
        tokenRepository = mockk()
        savedStateHandle = SavedStateHandle()

        coEvery { userRepository.tokenCheck() } returns true
        coEvery { tokenRepository.getAccessToken() } returns "Any"
        coEvery { tokenRepository.getRefreshToken() } returns "Any"

        viewModel = LoginViewModel(
            stringResourcesProvider,
            savedStateHandle,
            userRepository,
            tokenRepository
        )

        testDispatcher.scheduler.advanceUntilIdle()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `submit test`() {
        val email = "email@email.com"
        val password = "Test1234$"

        viewModel.emailChanged(email)
        viewModel.passwordChanged(password)

        coEvery { userRepository.login(any(),any()) } returns LoginQuery.Login("","",listOf())

        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `submit test with error`() {
        val email = "email@email.com"
        val password = "Test1234$"

        viewModel.emailChanged(email)
        viewModel.passwordChanged(password)

        every { stringResourcesProvider.getString(any()) } returns "error"
        coEvery { userRepository.login(any(),any()) } returns LoginQuery.Login("","",listOf(UserLoginErrors.USER_NOT_FOUND))

        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.value.passwordError == "error")
        assert(viewModel.formState.value.emailError == "error")
    }

    @Test
    fun `submit test with email error `() {
        var email = "email"
        var password = "Test1234$"

        viewModel.emailChanged(email)
        viewModel.passwordChanged(password)

        every { stringResourcesProvider.getString(any()) } returns "error"
        coEvery { userRepository.login(any(),any()) } returns LoginQuery.Login("","",listOf(UserLoginErrors.USER_NOT_FOUND))

        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.value.emailError == "error")

        email = ""
        password = "Test1234$"

        viewModel.emailChanged(email)
        viewModel.passwordChanged(password)

        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.value.emailError == "error")
    }

    @Test
    fun `submit test with pass error `() {
        var email = "email@email.com"
        var password = ""

        viewModel.emailChanged(email)
        viewModel.passwordChanged(password)

        every { stringResourcesProvider.getString(any()) } returns "error"
        coEvery { userRepository.login(any(),any()) } returns LoginQuery.Login("","",listOf(UserLoginErrors.USER_NOT_FOUND))

        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.value.passwordError == "error")
    }

    @Test
    fun `visible password test`() {
        assert(viewModel.formState.value.isVisiblePassword == false)
        viewModel.visiblePassword(true)
        assert(viewModel.formState.value.isVisiblePassword == true)
    }

    @Test
    fun `email and password reset changed test`() {
        assert(viewModel.formState.value.emailPassReset == "")
        viewModel.emailPassResetChanged("any")
        assert(viewModel.formState.value.emailPassReset == "any")
    }

    @Test
    fun `send reset email`() {
        var email = "email@email.com"

        coEvery { userRepository.resetPassword(any()) } returns true
        viewModel.emailPassResetChanged(email)
        viewModel.sendResetEmail()

        testDispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.formState.value.showToast == true)
    }

    @Test
    fun `send reset email wrong email`() {
        var email = ""

        every { stringResourcesProvider.getString(any()) } returns "error"
        coEvery { userRepository.resetPassword(any()) } returns true
        viewModel.emailPassResetChanged(email)
        viewModel.sendResetEmail()

        testDispatcher.scheduler.advanceUntilIdle()
        assert(viewModel.formState.value.showToast == false)
        assert(viewModel.formState.value.emailPassResetError != null)

        email = "email"
        viewModel.emailPassResetChanged(email)
        viewModel.sendResetEmail()

        assert(viewModel.formState.value.showToast == false)
        assert(viewModel.formState.value.emailPassResetError != null)
    }

}