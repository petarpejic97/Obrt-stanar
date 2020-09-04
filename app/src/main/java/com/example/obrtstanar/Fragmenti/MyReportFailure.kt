package com.example.obrtstanar.Fragmenti

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.obrtstanar.Klase.Failure
import com.example.obrtstanar.Klase.FailureAdapter
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.R
import com.google.firebase.database.*

class MyReportFailure : Fragment() {
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private lateinit var failureAdapter: FailureAdapter
    private lateinit var preferenceManager : PreferenceManager

    private lateinit var databaseReference: DatabaseReference
    private var failures : MutableList<Failure> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.fragment_my_report_failure, container, false)

        initializeVariable()

        getMyFailures()

        return rootView
    }
    private fun  initializeVariable(){
        preferenceManager = PreferenceManager()
        recyclerView = rootView.findViewById(R.id.failureRecyclerView)
        recyclerView.layoutManager = activity?.let { LinearLayoutManager(it) }
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(activity,RecyclerView.VERTICAL))
    }

    private fun getMyFailures(){
        databaseReference = FirebaseDatabase.getInstance().getReference("failures")

        databaseReference.run {
            orderByChild("user").equalTo(preferenceManager.getLoggedEmail().toString())
                .addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                    }
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (ds in dataSnapshot.children) {

                            val failure = Failure(
                                ds.child("name").getValue(String::class.java)!!,
                                ds.child("lastName").getValue(String::class.java)!!,
                                ds.child("address").getValue(String::class.java)!!,
                                ds.child("repairTime").getValue(String::class.java)!!,
                                ds.child("typeOfFailure").getValue(String::class.java)!!,
                                ds.child("failureDescription").getValue(String::class.java)!!,
                                ds.child("failureImageUri").getValue(String::class.java)!!,
                                ds.child("repairState").getValue(String::class.java)!!,
                                ds.child("user").getValue(String::class.java)!!
                            )
                            Log.w("AAA", ds.child("failureDescription").getValue(String::class.java)!!)
                            failures.add(failure)
                        }
                        failureAdapter = FailureAdapter(failures)

                        recyclerView.adapter = failureAdapter
                    }

                })
        }
    }
}