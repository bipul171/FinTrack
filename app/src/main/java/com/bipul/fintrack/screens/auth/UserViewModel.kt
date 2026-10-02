package com.bipul.fintrack.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bipul.fintrack.data.local.entity.UserEntity
import com.bipul.fintrack.data.local.session.SessionManager
import com.bipul.fintrack.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val sessionManager: SessionManager
) : ViewModel() {
    fun registerUser(
        fullName: String,
        username: String,
        email: String,
        passwordHash: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {

            if (repository.usernameExists(username)) {
                onResult(false, "Username already exists")
                return@launch
            }

            if (repository.emailExists(email)) {
                onResult(false, "Email already exists")
                return@launch
            }

            val user = UserEntity(
                fullName = fullName,
                username = username,
                email = email,
                passwordHash = passwordHash
            )

            repository.insertUser(user)

            onResult(true, "Account created successfully")
        }
    }

    fun loginUser(
        emailOrUsername: String,
        passwordHash: String,
        rememberMe: Boolean,
        onResult: (UserEntity?) -> Unit
    ) {
        viewModelScope.launch {

            val user = if (emailOrUsername.contains("@")) {
                repository.loginByEmail(
                    email = emailOrUsername,
                    passwordHash = passwordHash
                )
            } else {
                repository.loginByUsername(
                    username = emailOrUsername,
                    passwordHash = passwordHash
                )
            }

            if (user != null) {
                sessionManager.setRememberMe(rememberMe)
            }

            onResult(user)
        }
    }
}