package com.bipul.fintrack.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,

    val fullName: String,

    val username: String,

    val email: String,

    val passwordHash: String,

    val biometricEnabled: Boolean = false
)