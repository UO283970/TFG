package com.example.tfg.profile.edirProfile

import android.content.ContentResolver
import android.net.Uri
import androidx.core.net.toUri
import com.example.tfg.model.user.MainUserState
import com.example.tfg.model.user.User
import com.example.tfg.repository.UserRepository
import com.example.tfg.ui.common.StringResourcesProvider
import com.example.tfg.ui.profile.components.editScreen.EditProfileViewModel
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
import org.junit.Test
import java.io.ByteArrayInputStream

class EditProfileViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: EditProfileViewModel
    private lateinit var stringResourcesProvider: StringResourcesProvider
    private lateinit var userRepository: UserRepository
    private lateinit var contentResolver: ContentResolver
    private lateinit var mainUserState: MainUserState



    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        userRepository = mockk()
        mainUserState = MainUserState()
        stringResourcesProvider = mockk()
        contentResolver = mockk()

        mainUserState.setMainUser(User("userAlias", userId = "userId", userName = "userName"))
        mockkStatic(Uri::class)
        every { Uri.parse(any()) } returns Uri.EMPTY

        val fakeUri = mockk<Uri>(relaxed = true)

        mockkStatic("androidx.core.net.UriKt")
        every { any<String>().toUri() } returns fakeUri
        viewModel = EditProfileViewModel(
            stringResourcesProvider,
            mainUserState,
            userRepository,
            contentResolver
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun reset() {
        Dispatchers.resetMain()
    }

    @Test
    fun `change user name test`(){
        assert(viewModel.profileEditState.userName == "userName")
        viewModel.changeUserName("newUserName")
        assert(viewModel.profileEditState.userName == "newUserName")
    }

    @Test
    fun `change user description test`(){
        assert(viewModel.profileEditState.userDescription == "")
        viewModel.changeUserDescription("newDescription")
        assert(viewModel.profileEditState.userDescription == "newDescription")
    }

    @Test
    fun `change switch test`() {
        assert(!viewModel.profileEditState.switchState)
        viewModel.changeSwitch()
        assert(viewModel.profileEditState.switchState)
    }

    @Test
    fun `change user alias test`() {
        assert(viewModel.profileEditState.userAlias == "userAlias")
        viewModel.changeUserAlias("newAlias")
        assert(viewModel.profileEditState.userAlias == "newAlias")
    }


    @Test
    fun `change user permission test`() {
        assert(!viewModel.profileEditState.checkGalleryPermission)
        viewModel.changePermission()
        assert(viewModel.profileEditState.checkGalleryPermission)
    }

    @Test
    fun `change dialog test`() {
        assert(!viewModel.profileEditState.showDialog)
        viewModel.changeDialog()
        assert(viewModel.profileEditState.showDialog)
    }

    @Test
    fun `set image uri test`(){
        assert(viewModel.profileEditState.userImageUri == "".toUri())
        viewModel.setImageUri("imageUri".toUri())
        assert(viewModel.profileEditState.userImageUri == "imageUri".toUri())
    }

    @Test
    fun `save button click test empty user alias`(){
        viewModel.profileEditState = viewModel.profileEditState.copy(userAlias = "")
        every { stringResourcesProvider.getString(any()) } returns "error"

        viewModel.saveButtonOnClick()
        assert(viewModel.profileEditState.userNameError != null)
    }

    @Test
    fun `save button click test`(){
        val fakeData = "image bytes".toByteArray()
        val fakeInputStream = ByteArrayInputStream(fakeData)

        every { contentResolver.openInputStream(any()) } returns fakeInputStream
        viewModel.profileEditState = viewModel.profileEditState.copy(userAlias = "changedUserAlias", switchState = false)
        coEvery { userRepository.updateUser(any(),any(),any(),any(),any()) } returns "any"

        viewModel.saveButtonOnClick()
        testDispatcher.scheduler.advanceUntilIdle()
    }
}