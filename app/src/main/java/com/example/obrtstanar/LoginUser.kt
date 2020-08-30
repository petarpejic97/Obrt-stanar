package com.example.obrtstanar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.Klase.ProgressDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginUser : AppCompatActivity() {
    lateinit var tvRegistration: TextView;
    lateinit var btnLogin : Button
    lateinit var edEmail : EditText
    lateinit var  edPassword : EditText
    lateinit var preferenceManager : PreferenceManager

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        initializeVariable()
        ListenerOnRegistration()
        ListenerLogin()
    }
    private fun initializeVariable() {
        // Initialize Firebase Auth
        auth = Firebase.auth
        tvRegistration = findViewById(R.id.tvRegistration)
        btnLogin = findViewById(R.id.btnLogin)
        edEmail = findViewById(R.id.edEmail)
        edPassword = findViewById(R.id.edPassword)
        preferenceManager = PreferenceManager()
    }
    private fun ListenerLogin() {
        btnLogin.setOnClickListener {
            evaluateEd()
        }
    }

    private fun evaluateEd() {
        if(edEmail.text.toString().isEmpty() && edPassword.text.toString().isEmpty()){
            emptyEditTextHandler(edEmail)
            emptyEditTextHandler(edPassword)
        }
        else if(edEmail.text.toString().isEmpty()){
            emptyEditTextHandler(edEmail)
        }
        else if(edPassword.text.toString().isEmpty()){
            emptyEditTextHandler(edPassword)
        }
        else{
            doLogin()
        }
    }
    fun emptyEditTextHandler(edittext : EditText){
        edittext.error = "Polje ne smije biti prazno."
    }
    private fun doLogin() {
        val progress = ProgressDialog(
            this,
            "Prijava",
            "Molimo pričekate..."
        )
        auth.signInWithEmailAndPassword(edEmail.text.toString(), edPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if(auth.getCurrentUser()?.isEmailVerified()!!) {

                        preferenceManager.saveLoggedEmail(edEmail.text.toString())

                        preferenceManager.setLoginStatus("true")

                        goOnActivity(MainMenu::class.java)
                    }
                    else{
                        progress.progresDismis()
                        Toast.makeText(baseContext, "Podvrdite svoju email adresu.",
                            Toast.LENGTH_SHORT).show()
                    }

                } else {
                    progress.progresDismis()
                    Toast.makeText(baseContext, "Prijava neuspješna.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun ListenerOnRegistration() {
        tvRegistration.setOnClickListener {
            goOnActivity(Registration::class.java);
        }
    }

    private fun goOnActivity(classs: Class<*>) {
        val intent = Intent(this, classs)
        startActivity(intent)
        finish()
    }
}