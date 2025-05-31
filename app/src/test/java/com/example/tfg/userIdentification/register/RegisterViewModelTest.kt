package com.example.tfg.userIdentification.register

import android.content.ContentResolver
import android.net.Uri
import androidx.core.net.toUri
import com.example.tfg.model.security.TokenRepository
import com.example.tfg.model.user.UserRegistrationState
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.userIdentification.register.RegisterViewModel
import com.graphQL.CheckUserEmailAndPassMutation
import com.graphQL.CreateUserMutation
import com.graphQL.type.UserRegisterErrors
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import java.io.ByteArrayInputStream
import kotlin.test.Test

class RegisterViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RegisterViewModel
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var mainRepository: UserRepository
    private lateinit var tokenRepository: TokenRepository
    private lateinit var contentResolver: ContentResolver
    private lateinit var userRegistrationState: UserRegistrationState

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        stringResourcesProvider = mockk()
        mainRepository = mockk()
        tokenRepository = mockk()
        contentResolver = mockk()
        userRegistrationState = UserRegistrationState()

        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns Uri.EMPTY

        val fakeUri = mockk<Uri>(relaxed = true)

        mockkStatic("androidx.core.net.UriKt")
        every { any<String>().toUri() } returns fakeUri

        viewModel = RegisterViewModel(
            stringResourcesProvider,
            mainRepository,
            tokenRepository,
            contentResolver,
            userRegistrationState
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `change email test`() {
        viewModel.formState = viewModel.formState.copy(email = "any")
        assert(viewModel.formState.email == "any")

        viewModel.emailChanged("email")
        assert(viewModel.formState.email == "email")
    }

    @Test
    fun `change password test`() {
        viewModel.formState = viewModel.formState.copy(password = "any")
        assert(viewModel.formState.password == "any")

        viewModel.passwordChanged("pass")
        assert(viewModel.formState.password == "pass")
    }

    @Test
    fun `change visible password test`() {
        assert(viewModel.formState.isVisiblePassword == false)

        viewModel.visiblePassword(true)
        assert(viewModel.formState.isVisiblePassword == true)
    }

    @Test
    fun `change repeat password test`() {
        viewModel.formState = viewModel.formState.copy(passwordRepeat = "any")
        assert(viewModel.formState.passwordRepeat == "any")

        viewModel.passwordRepeatChanged("pass")
        assert(viewModel.formState.passwordRepeat == "pass")
    }

    @Test
    fun `change visible repeat password test`() {
        assert(viewModel.formState.isVisiblePasswordRepeat == false)

        viewModel.visiblePasswordRepeat(true)
        assert(viewModel.formState.isVisiblePasswordRepeat == true)
    }

    @Test
    fun `change permission test`() {
        assert(viewModel.formState.checkGalleryPermission == false)

        viewModel.changePermission()
        assert(viewModel.formState.checkGalleryPermission == true)
    }

    @Test
    fun `change preconditions test`() {
        viewModel.formState = viewModel.formState.copy(checkPreConditions = true)
        assert(viewModel.formState.checkPreConditions == true)

        viewModel.changePreconditions()
        assert(viewModel.formState.checkPreConditions == false)
    }

    @Test
    fun `change dialog test`() {
        viewModel.formState = viewModel.formState.copy(showDialog = true)
        assert(viewModel.formState.showDialog == true)

        viewModel.changeDialog()
        assert(viewModel.formState.showDialog == false)
    }

    @Test
    fun `change image uri test`() {
        viewModel.setImageUri(mockk<Uri>(relaxed = true))
    }

    @Test
    fun `check email and pass test`() {
        val email = "email@email.com"
        val password = "Test1234$"
        val passwordRepeat = "Test1234$"


        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)
        coEvery { mainRepository.checkUserEmailAndPass(any(),any(),any()) } returns CheckUserEmailAndPassMutation.CheckUserEmailAndPass(listOf())

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `check email and pass test wrong email`() {
        var email = "email"
        val password = "Test1234$"
        val passwordRepeat = "Test1234$"

        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)
        coEvery { mainRepository.checkUserEmailAndPass(any(),any(),any()) } returns CheckUserEmailAndPassMutation.CheckUserEmailAndPass(listOf())

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.emailError == "error")

        email = ""
        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.emailError == "error")

    }

    @Test
    fun `check email and pass test wrong password`() {
        var email = "email@email.com"
        var password = "pass"
        var passwordRepeat = "pass"

        every { stringResourcesProvider.getString(any()) } returns "error"
        every { stringResourcesProvider.getStringWithParameters(any(),any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)
        coEvery { mainRepository.checkUserEmailAndPass(any(),any(),any()) } returns CheckUserEmailAndPassMutation.CheckUserEmailAndPass(listOf())

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.passwordError == "error")
        viewModel.formState = viewModel.formState.copy(passwordError = "")

        password = ""
        passwordRepeat = ""
        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.passwordError == "error")

        viewModel.formState = viewModel.formState.copy(passwordError = "")

        password = "notAValidPass"
        passwordRepeat = "notAValidPass"
        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.passwordError == "error")

    }

    @Test
    fun `check email and pass test wrong password repeat`() {
        var email = "email@email.com"
        var password = "Test1234$"
        var passwordRepeat = "notSame"

        every { stringResourcesProvider.getString(any()) } returns "error"
        every { stringResourcesProvider.getStringWithParameters(any(),any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)
        coEvery { mainRepository.checkUserEmailAndPass(any(),any(),any()) } returns CheckUserEmailAndPassMutation.CheckUserEmailAndPass(listOf())

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.passwordRepeatError == "error")
    }

    @Test
    fun `check email and pass test repeated email`() {
        var email = "email@email.com"
        var password = "Test1234$"
        var passwordRepeat = "Test1234$"

        every { stringResourcesProvider.getString(any()) } returns "error"
        every { stringResourcesProvider.getStringWithParameters(any(),any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)
        coEvery { mainRepository.checkUserEmailAndPass(any(),any(),any()) } returns CheckUserEmailAndPassMutation.CheckUserEmailAndPass(listOf(
            UserRegisterErrors.ACCOUNT_EXISTS))

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.emailError == "error")
    }

    @Test
    fun `check email and pass test null return`() {
        var email = "email@email.com"
        var password = "Test1234$"
        var passwordRepeat = "Test1234$"

        every { stringResourcesProvider.getString(any()) } returns "error"
        every { stringResourcesProvider.getStringWithParameters(any(),any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat)
        coEvery { mainRepository.checkUserEmailAndPass(any(),any(),any()) } returns null

        viewModel.checkEmailAndPass()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.emailError == "error")
    }

    @Test
    fun `check submit test`() {
        var email = "email@email.com"
        var password = "Test1234$"
        var passwordRepeat = "Test1234$"
        var userAlias = "userAlias"
        val fakeData = "image bytes".toByteArray()
        val fakeInputStream = ByteArrayInputStream(fakeData)

        every { contentResolver.openInputStream(any()) } returns fakeInputStream
        every { stringResourcesProvider.getString(any()) } returns "error"
        every { stringResourcesProvider.getStringWithParameters(any(),any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat, userAlias = userAlias)
        coEvery { mainRepository.createUser(any(),any(),any(),any(),any(),any()) } returns CreateUserMutation.CreateUser("","",listOf())

        viewModel.checkEmailAndPass()
        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `check submit test wit errors`() {
        var email = "email@email.com"
        var password = "Test1234$"
        var passwordRepeat = "Test1234$"
        var userAlias = "userAlias"
        val fakeData = "image bytes".toByteArray()
        val fakeInputStream = ByteArrayInputStream(fakeData)

        every { contentResolver.openInputStream(any()) } returns fakeInputStream
        every { stringResourcesProvider.getString(any()) } returns "error"
        every { stringResourcesProvider.getStringWithParameters(any(),any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat, userAlias = userAlias)
        coEvery { mainRepository.createUser(any(),any(),any(),any(),any(),any()) } returns CreateUserMutation.CreateUser("","",listOf(UserRegisterErrors.ACCOUNT_EXISTS))

        viewModel.checkEmailAndPass()
        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @Test
    fun `check submit test wit user alias error`() {
        var email = "email@email.com"
        var password = "Test1234$"
        var passwordRepeat = "Test1234$"
        var userAlias = ""
        val fakeData = "image bytes".toByteArray()
        val fakeInputStream = ByteArrayInputStream(fakeData)

        every { contentResolver.openInputStream(any()) } returns fakeInputStream
        every { stringResourcesProvider.getString(any()) } returns "error"
        every { stringResourcesProvider.getStringWithParameters(any(),any()) } returns "error"

        viewModel.formState = viewModel.formState.copy(email = email, password = password, passwordRepeat = passwordRepeat, userAlias = userAlias)
        coEvery { mainRepository.createUser(any(),any(),any(),any(),any(),any()) } returns CreateUserMutation.CreateUser("","",listOf(UserRegisterErrors.ACCOUNT_EXISTS))

        viewModel.checkEmailAndPass()
        viewModel.submit()
        testDispatcher.scheduler.advanceUntilIdle()

        assert(viewModel.formState.userAliasError == "error")
    }
}