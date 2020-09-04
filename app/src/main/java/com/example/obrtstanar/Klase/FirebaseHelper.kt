package com.example.obrtstanar.Klase

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class FirebaseHelper {
    fun sendPasswordResetMail(context: Context, email : String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context,"Link za promjenu zaporke poslan je na Vašu email adresu.",
                        Toast.LENGTH_LONG).show()
                }
            }
    }
}