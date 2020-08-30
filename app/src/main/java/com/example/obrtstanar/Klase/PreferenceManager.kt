package com.example.obrtstanar.Klase

import android.content.Context
import com.example.obrtstanar.ObrtStanar

class PreferenceManager {
    companion object {
        const val PREFS_FILE = "MyPreferences"
    }
    fun saveLoggedEmail(email: String) {
        saveVariable("loggedEmail", email)
    }
    fun saveLoggedName(name: String) {
        saveVariable("loggedName", name)
    }
    fun saveLoggedLastname(lastName: String) {
        saveVariable("loggedLastname", lastName)
    }
    fun setLoginStatus(status: String) {
        saveVariable("loginStatus", status)
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
    fun saveVariable(varName : String, varValue : String){
        val sharedPreferences = ObrtStanar.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(varName, varValue)
        editor.apply()
    }
}