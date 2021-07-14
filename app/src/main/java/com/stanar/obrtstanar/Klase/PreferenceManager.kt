package com.stanar.obrtstanar.Klase

import android.content.Context

class PreferenceManager {
    companion object {
        const val PREFS_FILE = "MyPreferences"
        val sharedPreferences = ObrtStanar.ApplicationContext.getSharedPreferences(
            PREFS_FILE, Context.MODE_PRIVATE
        )
    }
    fun saveLoggedPrimaryKey(key: String) {
        saveVariable("primaryKey", key)
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
    fun savePhoneNumber(phoneNumber: String) {
        saveVariable("phoneNumber", phoneNumber)
    }
    fun saveAddress(address: String) {
        saveVariable("address", address)
    }
    fun saveLoginStatus(status: String) {
        saveVariable("loginStatus", status)
    }
    fun saveUserType(type: String) {
        saveVariable("userType", type)
    }
    fun getUserType(): String? {
        return sharedPreferences.getString("userType", "none")
    }
    fun getLoggedEmail(): String? {
        return sharedPreferences.getString("loggedEmail", "none")
    }
    fun getLoggedName(): String? {
        return sharedPreferences.getString("loggedName", "none")
    }
    fun getLoggedLastname(): String? {
        return sharedPreferences.getString("loggedLastname", "none")
    }
    fun getLoggedPhoneNumber(): String? {
        return sharedPreferences.getString("phoneNumber", "none")
    }
    fun getLoggedAddress(): String? {
        return sharedPreferences.getString("address", "none")
    }
    fun getLoginStatus(): String? {
        return sharedPreferences.getString("loginStatus", "none")
    }
    fun getVariable(varName: String): String? {
        return sharedPreferences.getString(varName, "none")
    }

    fun saveVariable(varName : String, varValue : String){
        val editor = sharedPreferences.edit()
        editor.putString(varName, varValue)
        editor.apply()
    }
}