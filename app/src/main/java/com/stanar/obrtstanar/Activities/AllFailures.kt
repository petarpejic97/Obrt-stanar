package com.stanar.obrtstanar.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stanar.obrtstanar.Fragmenti.Listener.FailureListener
import com.stanar.obrtstanar.Klase.Adapters.FailureAdapter
import com.stanar.obrtstanar.Klase.FirebaseClass.FailureWithId
import com.stanar.obrtstanar.Klase.PreferenceManager
import com.stanar.obrtstanar.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.stanar.obrtstanar.Klase.Adapters.FailureAdapterForNormalUsers
import com.stanar.obrtstanar.Klase.Adapters.FailureAdapterForRepairer

class AllFailures : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var failureAdapter: FailureAdapterForRepairer
    private lateinit var preferenceManager : PreferenceManager
    private lateinit var progressBar : ProgressBar
    private lateinit var logOut : ImageView;

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseStorage: FirebaseStorage
    private var failures : MutableList<FailureWithId> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_failures)

        initializeVariable()

        getFailures()
    }
    private fun  initializeVariable(){
        progressBar = findViewById(R.id.progressBar)
        preferenceManager = PreferenceManager()
        recyclerView = findViewById(R.id.failureRecyclerView)

        recyclerView.layoutManager =  LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this,RecyclerView.VERTICAL))
    }
    fun getFailures(){

        databaseReference = FirebaseDatabase.getInstance().getReference("failures")

        databaseReference
            .addValueEventListener(object : com.google.firebase.database.ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val failure =
                            FailureWithId(
                                ds.key.toString(),
                                ds.child("name").getValue(String::class.java)!!,
                                ds.child("lastName").getValue(String::class.java)!!,
                                ds.child("address").getValue(String::class.java)!!,
                                ds.child("phoneNumber").getValue(String::class.java)!!,
                                ds.child("repairTime").getValue(String::class.java)!!,
                                ds.child("typeOfFailure").getValue(String::class.java)!!,
                                ds.child("failureDescription").getValue(String::class.java)!!,
                                ds.child("failureImageUri").getValue(String::class.java)!!,
                                ds.child("repairState").getValue(String::class.java)!!,
                                ds.child("user").getValue(String::class.java)!!,
                                ds.child("finished").getValue(String::class.java)!!
                            )
                        failures.add(failure)
                    }
                    val failureListener = object :
                        FailureListener {
                        override fun onShowDetails(typeoffailure: String,imgUri :String) {
                            goOnActivityShowPicture(typeoffailure,imgUri)
                        }

                        override fun updateWithId(id: String,state : String) {
                            updateInFirebase(id,state)
                            failureAdapter.refreshData()

                        }

                        override fun deleteImage(uri: String,id : String) {
                            removeDataFromDatabase(id)
                            if(uri != ""){
                                removeImageFromStorage(uri)
                            }

                            failureAdapter.refreshData()
                        }

                        override fun callRepairer(typeoffailure: String) {
                        }

                    }
                    failureAdapter = FailureAdapterForRepairer(failures,failureListener)

                    recyclerView.adapter = failureAdapter

                    closeProgresBar()
                }
            })
    }
    private fun closeProgresBar(){
        progressBar.visibility = View.GONE
    }
    private fun goOnActivityShowPicture(type : String, uri : String){
        val intent = Intent(this, ShowPicture::class.java)
        intent.putExtra("typeoffailure", type)
        if(uri != ""){
            intent.putExtra("uriImg", uri)
        }
        else{
            intent.putExtra("uriImg", "NoImg")
        }

        startActivity(intent)
        //finish()
    }
    private fun updateInFirebase(id: String, state : String){
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference()
        databaseReference.child("failures").child(id).child("repairState").setValue(state)
    }
    private fun removeDataFromDatabase(id: String){
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference
        databaseReference.child("failures").child(id).removeValue()
    }
    private fun removeImageFromStorage(uri: String){
        firebaseStorage = FirebaseStorage.getInstance()
        val imageRef = firebaseStorage.getReferenceFromUrl(uri)
        imageRef.delete().addOnSuccessListener {
            Toast.makeText(this,"Obrisano", Toast.LENGTH_SHORT).show()
        }
    }

    fun logOut(view: View) {
        preferenceManager.saveLoggedEmail("Niste prijavljeni.")
        preferenceManager.saveLoginStatus("false")

        val intent = Intent(this,LoginUser::class.java)
        startActivity(intent)
        finish()
    }
}