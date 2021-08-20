package com.mite.djigibao.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mite.djigibao.database.entities.User
import com.mite.djigibao.domain.firebase.FirestoreUserUseCase
import com.mite.djigibao.model.Account
import com.mite.djigibao.model.Role
import com.mite.djigibao.repository.users.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firestoreUserUseCase: FirestoreUserUseCase,
    private val userRepository: IUserRepository
): ViewModel() {
    private val _isPickingRole by lazy { MutableLiveData(false) }
    val isPickingRole: LiveData<Boolean> get() = _isPickingRole

    private val _alreadyLoggedIn by lazy { MutableLiveData(false) }
    val alreadyLoggedIn: LiveData<Boolean> get() = _alreadyLoggedIn

    private val _pickedRole by lazy { MutableLiveData(Role.NONE) }
    val pickedRole: LiveData<Role> get() = _pickedRole

    private val usernameTyped by lazy { MutableLiveData("") }

    private val passwordTyped by lazy { MutableLiveData("") }

    private val _warning by lazy { MutableLiveData(false) }
    val warning: LiveData<Boolean> get() = _warning

    private val _loadingToMainScreen by lazy { MutableLiveData<Boolean?>(null) }
    val loadingToMainScreen: LiveData<Boolean?> get() = _loadingToMainScreen

    fun findIfLoggedInAlready() = viewModelScope.launch(Dispatchers.IO) {
        val loggedInState = userRepository.checkIfLoggedIn()
        _alreadyLoggedIn.postValue(loggedInState)
    }
    fun syncUsers() = viewModelScope.launch( Dispatchers.IO) {
        firestoreUserUseCase.syncUsers()
    }

    fun register() = viewModelScope.launch(Dispatchers.IO) {
        val username = usernameTyped.value
        val password = passwordTyped.value
        val usernames = userRepository.getAllUsers()?.map { it.username }
        if (usernames?.contains(username) == false) {
            val role = _pickedRole.value
            if (username?.isNotEmpty() == true && password?.isNotEmpty() == true && role != null && role != Role.NONE) {
                val user = User(
                    username = username,
                    role = role
                )
                val account = Account(
                    username = username,
                    password = password,
                    role = role
                )
                userRepository.saveMyAccountData(account)
                userRepository.insertUser(account, user)
                addToFirebase(user)
            } else
                warn()
        }
    }

    private fun addToFirebase(user: User) = viewModelScope.launch(Dispatchers.IO) {
        _loadingToMainScreen.postValue(true)
        val result = firestoreUserUseCase.addUser(user)
        if(!result)
            _loadingToMainScreen.postValue(false)
    }

    fun pickRole(role: Role) = viewModelScope.launch {
        _pickedRole.postValue(role)
    }

    private fun warn() = viewModelScope.launch {
        _warning.postValue(true)
        delay(2500)
        _warning.postValue(false)
    }

    fun isPicking(flag: Boolean) = viewModelScope.launch {
        _isPickingRole.postValue(flag)
    }

    fun logIn() {
        _loadingToMainScreen.postValue(true)
    }

    fun usernameUpdate(it: String) {
        usernameTyped.value = it
    }

    fun passwordUpdate(it: String) {
        passwordTyped.value = it
    }

    fun leftScreen() = viewModelScope.launch(Dispatchers.IO)  {
        _loadingToMainScreen.postValue(false)
        _alreadyLoggedIn.postValue(false)
    }

}