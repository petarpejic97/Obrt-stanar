package com.example.obrtstanar

import android.content.Context

class PreferenceManager {
    companion object {
        const val PREFS_FILE = "MyPreferences"
    }
    fun saveLoggedEmail(email: String) {
        val sharedPreferences = ObrtStanar.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString("loggedEmail", email)
        editor.apply()
    }
    fun setLoginStatus(status: String) {
        val sharedPreferences = ObrtStanar.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString("loginStatus", status)
        editor.apply()
    }
    fun getLoggedEmail(): String? {
        val sharedPreferences = ObrtStanar.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("loggedEmail", "none")
    }
    fun GetLoginStatus(): String? {
        val sharedPreferences = ObrtStanar.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("loginStatus", "none")
    }
}