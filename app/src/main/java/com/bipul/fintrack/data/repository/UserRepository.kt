package com.bipul.fintrack.data.repository

import com.bipul.fintrack.data.local.dao.UserDao
import com.bipul.fintrack.data.local.entity.UserEntity
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun insertUser(user: UserEntity): Long =
        userDao.insertUser(user)

    suspend fun loginByUsername(
        username: String,
        passwordHash: String
    ): UserEntity? =
        userDao.loginByUsername(username, passwordHash)

    suspend fun loginByEmail(
        email: String,
        passwordHash: String
    ): UserEntity? =
        userDao.loginByEmail(email, passwordHash)

    suspend fun usernameExists(username: String): Boolean =
        userDao.usernameExists(username)

    suspend fun emailExists(email: String): Boolean =
        userDao.emailExists(email)

    suspend fun findUserByUsernameAndEmail(
        username: String,
        email: String
    ): UserEntity? =
        userDao.findUserByUsernameAndEmail(username, email)

    suspend fun updatePassword(
        userId: Long,
        passwordHash: String
    ) {
        userDao.updatePassword(userId, passwordHash)
    }
}