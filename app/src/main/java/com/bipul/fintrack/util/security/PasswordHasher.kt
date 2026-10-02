package com.bipul.fintrack.util.security

import java.security.MessageDigest

object PasswordHasher {

    fun hash(password: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
}