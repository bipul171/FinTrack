package com.bipul.fintrack.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bipul.fintrack.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("""
        SELECT * FROM users
        WHERE username = :username
        AND passwordHash = :passwordHash
        LIMIT 1
    """)
    suspend fun loginByUsername(
        username: String,
        passwordHash: String
    ): UserEntity?

    @Query("""
        SELECT * FROM users
        WHERE email = :email
        AND passwordHash = :passwordHash
        LIMIT 1
    """)
    suspend fun loginByEmail(
        email: String,
        passwordHash: String
    ): UserEntity?

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM users
            WHERE username = :username
        )
    """)
    suspend fun usernameExists(
        username: String
    ): Boolean

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM users
            WHERE email = :email
        )
    """)
    suspend fun emailExists(
        email: String
    ): Boolean
}