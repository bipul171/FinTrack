package com.bipul.fintrack.data.local.session

import android.content.Context

class SessionManager(
    context: Context
) {

    private val preferences = context.getSharedPreferences(
        "fintrack_session",
        Context.MODE_PRIVATE
    )

    fun setRememberMe(enabled: Boolean) {
        preferences.edit()
            .putBoolean("remember_me", enabled)
            .apply()
    }

    fun isRememberMeEnabled(): Boolean {
        return preferences.getBoolean("remember_me", false)
    }

    fun clearSession() {
        preferences.edit()
            .clear()
            .apply()
    }
}