package com.example.obrtstanar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDelay()
    }
    fun setDelay(){
        val handler = Handler()
        handler.postDelayed(Runnable { checkIsUserLogged() }, 2000)
    }
    fun checkIsUserLogged(){
        val preferenceManager = PreferenceManager()
        if(preferenceManager.GetLoginStatus() == "true"){
            startNewActivity(MainMenu::class.java)
        }
        else{
            startNewActivity(LoginUser::class.java)
        }
    }
    private fun startNewActivity(classs: Class<*>) {
        val intent = Intent(this, classs)
        startActivity(intent)
        finish()
    }
}