package com.example.obrtstanar.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.obrtstanar.Klase.FirebaseHelper
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.Klase.ProgressDialog
import com.example.obrtstanar.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_user.*

class LoginUser : AppCompatActivity() {
    lateinit var tvRegistration: TextView;
    lateinit var  tvForgotPassword : TextView
    lateinit var btnLogin : Button
    lateinit var edEmail : EditText
    lateinit var  edPassword : EditText
    lateinit var preferenceManager : PreferenceManager
    lateinit var progress : ProgressDialog
    lateinit var firebaseHelper : FirebaseHelper

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        initializeVariable()
        ListenerOnRegistration()
        ListenerLogin()
        ListenerForgotPass()
    }
    private fun initializeVariable() {
        // Initialize Firebase Auth
        auth = Firebase.auth
        tvRegistration = findViewById(R.id.tvRegistration)
        btnLogin = findViewById(R.id.btnLogin)
        edEmail = findViewById(R.id.edEmail)
        edPassword = findViewById(R.id.edPassword)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)
        preferenceManager = PreferenceManager()
        progress = ProgressDialog(this, "Prijava", "Molimo pričekate...")
        firebaseHelper = FirebaseHelper()
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
        progress.showDialog()

        auth.signInWithEmailAndPassword(edEmail.text.toString(), edPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    if(auth.getCurrentUser()?.isEmailVerified()!!) {

                        preferenceManager.saveLoggedEmail(edEmail.text.toString())

                        preferenceManager.setLoginStatus("true")

                        saveUserInfoInPreferences()

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

    private fun saveUserInfoInPreferences(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")

        myRef.run {
            orderByChild("email").equalTo(preferenceManager.getLoggedEmail().toString())
                .addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (ds in dataSnapshot.children) {
                            saveInPreferenceManager(ds.key.toString(),ds.child("name").getValue(String::class.java)!!,ds.child("lastName").getValue(String::class.java)!!
                                    ,ds.child("phoneNumber").getValue(String::class.java)!!,ds.child("address").getValue(String::class.java)!!,ds.child("email").getValue(String::class.java)!!)
                        }
                    }
                })
        }
    }
    private fun saveInPreferenceManager(key:String, name:String, lastName : String, phoneNumber : String, address : String, email : String){
        preferenceManager.saveLoggedPrimaryKey(key)
        preferenceManager.saveLoggedName(name)
        preferenceManager.saveLoggedLastname(lastName)
        preferenceManager.savePhoneNumber(phoneNumber)
        preferenceManager.saveAddress(address)
        preferenceManager.saveLoggedEmail(email)
    }
    private fun ListenerForgotPass(){
        tvForgotPassword.setOnClickListener {
            if(edEmail.text.isEmpty()){
                Toast.makeText(this,"Molimo upišite svoj email",Toast.LENGTH_LONG).show()
            }
            else{
                firebaseHelper.sendPasswordResetMail(this,edEmail.text.toString())
            }

        }
    }
}