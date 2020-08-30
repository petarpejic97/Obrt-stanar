package com.example.obrtstanar

//import com.google.firebase.database.FirebaseDatabase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.obrtstanar.Klase.ProgressDialog
import com.example.obrtstanar.Klase.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class Registration : AppCompatActivity() {

    lateinit var imgBack : ImageView;
    lateinit var edname : EditText;
    lateinit var edlastname : EditText;
    lateinit var edphonenumber : EditText;
    lateinit var edemail : EditText;
    lateinit var edpassword : EditText;
    lateinit var edconfPass : EditText;
    lateinit var btnregistration : Button;

    var emptyflag : Boolean = true;
    var passrequerments : Boolean = true;
    var passequality : Boolean = true;

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initializateVariable()
        backListener()
        btnRegListener()
    }
    private fun initializateVariable() {
        // Initialize Firebase Auth
        auth = Firebase.auth
        imgBack = findViewById(R.id.imgBack)
        edname = findViewById(R.id.edName)
        edlastname = findViewById(R.id.edLastname)
        edphonenumber = findViewById(R.id.edPhoneNumber)
        edemail = findViewById(R.id.edEmail)
        edpassword = findViewById(R.id.edPassword)
        edconfPass = findViewById(R.id.edConfPass)
        btnregistration = findViewById(R.id.btnRegistration)
    }
    private fun backListener() {
        imgBack.setOnClickListener {
            goOnLoginActivity()
        }
    }
    private fun goOnLoginActivity() {
        val intent = Intent(this, LoginUser::class.java)
        startActivity(intent)
        finish()
    }
    private fun btnRegListener() {
        btnregistration.setOnClickListener {
            evaluateEditTexts()
        }
    }
    private fun evaluateEditTexts() {
        emptyflag = true
        checkEmptyED()
        if(emptyflag){
            passrequerments = checkPasswordRequirments()
        }
        if(passrequerments && emptyflag){
            passequality = checkPasswordEquality()
        }
        if(emptyflag && passrequerments && passequality){
            registration()
        }
    }
    private fun checkEmptyED() {
        if(edname.text.toString().isEmpty()) {
            emptyEditTextHandler(edname)
            emptyflag = false
        }
        if(edlastname.text.toString().isEmpty()){
            emptyEditTextHandler(edlastname)
            emptyflag = false
        }
        if(edphonenumber.text.toString().isEmpty()){
            emptyEditTextHandler(edphonenumber)
            emptyflag = false
        }
        if(edemail.text.toString().isEmpty()){
            emptyEditTextHandler(edemail)
            emptyflag = false
        }
        if(edpassword.text.toString().isEmpty()){
            emptyEditTextHandler(edpassword)
            emptyflag = false
        }
        if(edconfPass.text.toString().isEmpty()){
            emptyEditTextHandler(edconfPass)
            emptyflag = false
        }
    }
    private fun checkPasswordEquality() : Boolean {
        if(edpassword.text.toString() != edconfPass.text.toString()){
            edpassword.error = "Zaporke nisu iste."
            edconfPass.error = "Zaporke nisu iste."
            return false
        }
        else return true
    }
    private fun checkPasswordRequirments() : Boolean {
        if(edpassword.text.toString().length < 8){
            edpassword.error = "Zaporke mora imati minimalno 8 znakova."
            return false;
        }
        else if(!ifContainNumber(edpassword.text.toString())){
            edpassword.error = "Zaporke mora sadržavati barem jedan broj."
            return false
        }
        else
            return true;
    }
    fun ifContainNumber(password : String) : Boolean{

        for (c in password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true
            }
        }
        return false

    }
    fun emptyEditTextHandler(edittext : EditText){
        edittext.error = "Polje ne smije biti prazno."
    }

    private fun registration() {
        val progress = ProgressDialog(
            this,
            "Registracija",
            "Molimo pričekajte..."
        )

        auth.createUserWithEmailAndPassword(edemail.text.toString(), edpassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener(OnCompleteListener<Void?> { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this,"Molimo Vas potvrdite svoj email!",Toast.LENGTH_LONG).show()
                                saveInDatabase()
                                goOnLoginActivity()
                            }
                        })

                } else {
                    progress.progresDismis()
                    Toast.makeText(baseContext, "Authentication failed.",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun saveInDatabase(){
        val user = User(
            edname.text.toString(),
            edlastname.text.toString(),
            edphonenumber.text.toString(),
            edemail.text.toString()
        )

        val database = FirebaseDatabase.getInstance()
        val key = database.getReference("users").push().key
        val myRef = key?.let { database.getReference().child("users").child(it).setValue(user) }
    }
}