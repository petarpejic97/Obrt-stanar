package com.example.obrtstanar.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.obrtstanar.Klase.Controllers.AlertController
import com.example.obrtstanar.Klase.FirebaseHelper
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.Klase.ProgressDialog
import com.example.obrtstanar.LoginViewModel
import com.example.obrtstanar.R
import com.example.obrtstanar.databinding.ActivityLoginUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_user.*

class LoginUser : AppCompatActivity() {
    private lateinit var viewModel : LoginViewModel
    private lateinit var binding : ActivityLoginUserBinding

    private var alertController : AlertController = AlertController()

    lateinit var preferenceManager : PreferenceManager
    lateinit var progress : ProgressDialog
    lateinit var firebaseHelper : FirebaseHelper

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        initializeVariable()
        setUpUI()
        ListenerOnRegistration()
        ListenerLogin()
        ListenerForgotPass()
    }
    private fun initializeVariable() {
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login_user)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        preferenceManager = PreferenceManager()
        progress = ProgressDialog(this, "Prijava", "Molimo pričekate...")
        firebaseHelper = FirebaseHelper()

    }

    private fun setUpUI(){
        binding.apply {
            login = viewModel
            viewModel.email.value=""
            viewModel.password.value=""
        }
    }
    private fun ListenerLogin() {
        binding.btnLogin.setOnClickListener {
            evaluateEd()
        }
    }

    private fun evaluateEd() {
        if(edEmail.text.toString().isEmpty() && edPassword.text.toString().isEmpty()){
            alertController.twoEditTextAlert(edPassword,edEmail,"Polje ne smije biti prazno.")
        }
        else if(edEmail.text.toString().isEmpty()){
            alertController.oneEditTextAlert(edEmail,"Polje ne smije biti prazno.")
        }
        else if(edPassword.text.toString().isEmpty()){
            alertController.oneEditTextAlert(edPassword,"Polje ne smije biti prazno.")
        }
        else{
            doLogin()
        }
    }
    private fun doLogin() {
        progress.showDialog()

        auth.signInWithEmailAndPassword(binding.login?.email?.value!!, binding.login?.password?.value!!)
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
        binding.tvRegistration.setOnClickListener {
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
        binding.tvForgotPassword.setOnClickListener {
            if(edEmail.text.isEmpty()){
                Toast.makeText(this,"Molimo upišite svoj email",Toast.LENGTH_LONG).show()
            }
            else{
                firebaseHelper.sendPasswordResetMail(this,edEmail.text.toString())
            }

        }
    }
}