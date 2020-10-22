package com.example.obrtstanar.Fragmenti

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obrtstanar.Activities.ShowPicture
import com.example.obrtstanar.Fragmenti.Listener.FailureListener
import com.example.obrtstanar.Klase.Adapters.FailureAdapter
import com.example.obrtstanar.Klase.FirebaseClass.FailureWithId
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class SeeAllFailures : Fragment(){
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private lateinit var failureAdapter: FailureAdapter
    private lateinit var preferenceManager : PreferenceManager
    private lateinit var progressBar : ProgressBar

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseStorage: FirebaseStorage
    private var failures : MutableList<FailureWithId> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_my_report_failure, container, false)

        initializeVariable()

        getFailures()

        return rootView
    }
    private fun  initializeVariable(){
        progressBar = rootView.findViewById(R.id.progressBar)
        preferenceManager = PreferenceManager()
        recyclerView = rootView.findViewById(R.id.failureRecyclerView)
        recyclerView.layoutManager = activity?.let { LinearLayoutManager(it) }
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(activity,RecyclerView.VERTICAL))
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
                                    ds.child("user").getValue(String::class.java)!!
                                )
                            Log.w("state",ds.child("repairState").getValue(String::class.java)!!)
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
                                Log.w("AAA","ovdje urem")
                                removeDataFromDatabase(id)
                                if(uri != ""){
                                    removeImageFromStorage(uri)
                                }

                                failureAdapter.refreshData()
                            }

                        }
                        failureAdapter = FailureAdapter(failures,failureListener)

                        recyclerView.adapter = failureAdapter

                        closeProgresBar()
                    }
            })
    }
    private fun closeProgresBar(){
        progressBar.visibility = View.GONE
    }
    private fun goOnActivityShowPicture(type : String, uri : String){
        val intent = Intent(activity, ShowPicture::class.java)
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
        Log.w("AAA",id)
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference
        databaseReference.child("failures").child(id).removeValue()
    }
    private fun removeImageFromStorage(uri: String){
        firebaseStorage = FirebaseStorage.getInstance()
        val imageRef = firebaseStorage.getReferenceFromUrl(uri)
        imageRef.delete().addOnSuccessListener {
            Toast.makeText(activity,"Obrisano",Toast.LENGTH_SHORT).show()
        }
    }

}