package com.stanar.obrtstanar.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.stanar.obrtstanar.Klase.Controllers.AlertController
import com.stanar.obrtstanar.Klase.FirebaseHelper
import com.stanar.obrtstanar.Klase.PreferenceManager
import com.stanar.obrtstanar.Klase.ProgressDialog
import com.stanar.obrtstanar.LoginViewModel
import com.stanar.obrtstanar.R
import com.stanar.obrtstanar.databinding.ActivityLoginUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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
            binding.login?.email?.value = viewModel.email.value
            binding.login?.password?.value = viewModel.password.value
        }
    }
    private fun ListenerLogin() {
        binding.btnLogin.setOnClickListener {
            evaluateEd()
        }
    }

    private fun evaluateEd() {
        if(binding.login?.email?.value?.isEmpty()==null && binding.login?.password?.value?.isEmpty()==null){
            alertController.twoEditTextAlert(edPassword,edEmail,"Polje ne smije biti prazno.")
        }
        else if(binding.login?.email?.value?.isEmpty()==null){
            alertController.oneEditTextAlert(edEmail,"Polje ne smije biti prazno.")
        }
        else if(binding.login?.password?.value?.isEmpty()==null){
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

                        progress.progresDismis()

                        preferenceManager.saveLoggedEmail(edEmail.text.toString())

                        preferenceManager.saveLoginStatus("true")

                        saveUserInfoInPreferences()

                        if(edEmail.text.toString() == "majstori.stanar@gmail.com"){
                            goOnActivity(AllFailures::class.java)
                        }
                        else{
                            goOnActivity(MainMenu::class.java)
                        }

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
                                    ,ds.child("phoneNumber").getValue(String::class.java)!!,ds.child("address").getValue(String::class.java)!!
                                    ,ds.child("email").getValue(String::class.java)!!, ds.child("type").getValue(String::class.java)!!)
                        }
                    }
                })
        }
    }
    private fun saveInPreferenceManager(key:String, name:String, lastName : String, phoneNumber : String, address : String, email : String,type : String){
        preferenceManager.saveLoggedPrimaryKey(key)
        preferenceManager.saveLoggedName(name)
        preferenceManager.saveLoggedLastname(lastName)
        preferenceManager.savePhoneNumber(phoneNumber)
        preferenceManager.saveAddress(address)
        preferenceManager.saveLoggedEmail(email)
        preferenceManager.saveUserType(type)
        saveRepairersInPreferenceManager();
    }
    private fun saveRepairersInPreferenceManager(){
        Log.w("AAA","usau u saveRepair")
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("repairer")
        myRef.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                Log.w("AAA",snapshot.child("builder").getValue(String::class.java)!!)
                preferenceManager.saveVariable("plumber",snapshot.child("plumber").getValue(String::class.java)!!)
                preferenceManager.saveVariable("electricitian",snapshot.child("electricitian").getValue(String::class.java)!!)
                preferenceManager.saveVariable("builder",snapshot.child("builder").getValue(String::class.java)!!)
                preferenceManager.saveVariable("another",snapshot.child("another").getValue(String::class.java)!!)

//                for (ds in snapshot.children){
//                    val number = ds.getValue(String::class.java)
//                    if (number != null) {
//                        Log.w("AAAA",number)
//                    }
//                }
            }
        })
    }
    private fun ListenerForgotPass(){
        binding.tvForgotPassword.setOnClickListener {
            if(binding.login?.email?.value?.isEmpty()==null){
                Toast.makeText(this,"Molimo upišite svoj email",Toast.LENGTH_LONG).show()
            }
            else{
                firebaseHelper.sendPasswordResetMail(this,edEmail.text.toString())
            }

        }
    }
}