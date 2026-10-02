package com.bipul.fintrack.data.repository

import com.bipul.fintrack.data.local.dao.UserDao
import com.bipul.fintrack.data.local.entity.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun insertUser(user: UserEntity): Long {
        return userDao.insertUser(user)
    }

    suspend fun loginByUsername(
        username: String,
        passwordHash: String
    ): UserEntity? {
        return userDao.loginByUsername(
            username = username,
            passwordHash = passwordHash
        )
    }

    suspend fun loginByEmail(
        email: String,
        passwordHash: String
    ): UserEntity? {
        return userDao.loginByEmail(
            email = email,
            passwordHash = passwordHash
        )
    }

    suspend fun usernameExists(
        username: String
    ): Boolean {
        return userDao.usernameExists(username)
    }

    suspend fun emailExists(
        email: String
    ): Boolean {
        return userDao.emailExists(email)
    }
}