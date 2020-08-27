package com.example.obrtstanar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNewActivity();
    }

    private fun startNewActivity() {
        val handler = Handler()
        handler.postDelayed(Runnable { openLogin() }, 2000)
    }

    private fun openLogin() {
        val intent = Intent(this, LoginUser::class.java)
        startActivity(intent)
        finish()
    }

}