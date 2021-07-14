package com.stanar.obrtstanar.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.stanar.obrtstanar.Klase.PreferenceManager
import com.stanar.obrtstanar.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    lateinit var preferenceManager : PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setDelay()
        getRepairers()
    }
    fun getRepairers(){
        preferenceManager = PreferenceManager()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("repairer")

        myRef.run {
            orderByChild("email").equalTo(preferenceManager.getLoggedEmail().toString())
                .addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (ds in dataSnapshot.children) {
                            saveRepairersInPreferenceManager(
                                ds.child("plumber").getValue(String::class.java)!!
                                ,ds.child("electricitian").getValue(String::class.java)!!
                                ,ds.child("builder").getValue(String::class.java)!!
                                ,ds.child("another").getValue(String::class.java)!!
                            )
                        }
                    }
                })
        }
    }
    fun saveRepairersInPreferenceManager(plumber:String, electricitian:String, builder : String, another : String){
        preferenceManager.saveVariable("plumber", plumber)
        preferenceManager.saveVariable("electricitian", electricitian)
        preferenceManager.saveVariable("builder", builder)
        preferenceManager.saveVariable("another", another)
    }
    fun setDelay(){
        val handler = Handler()
        handler.postDelayed(Runnable { checkIsUserLogged() }, 2000)
    }
    fun checkIsUserLogged(){
        val preferenceManager = PreferenceManager()
        if(preferenceManager.getLoginStatus() == "true"){
            if(preferenceManager.getLoggedEmail() == "majstori.stanar@gmail.com"){
                startNewActivity(AllFailures::class.java)
            }
            else{
                startNewActivity(MainMenu::class.java)
            }
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