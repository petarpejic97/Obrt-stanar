package com.stanar.obrtstanar.Fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stanar.obrtstanar.Fragmenti.Listener.UpdateFinishedFailure
import com.stanar.obrtstanar.Klase.Adapters.FailureAdapterForNormalUsers
import com.stanar.obrtstanar.Klase.FirebaseClass.FailureWithId
import com.stanar.obrtstanar.Klase.PreferenceManager
import com.stanar.obrtstanar.R
import com.google.firebase.database.*

class MyReportFailure : Fragment() {
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private lateinit var failureAdapterForNormalUsers: FailureAdapterForNormalUsers
    private lateinit var preferenceManager : PreferenceManager
    private lateinit var progressBar : ProgressBar
    private lateinit var databaseReference: DatabaseReference
    private var failures : MutableList<FailureWithId> = mutableListOf()

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
        progressBar = rootView.findViewById(R.id.progressBar)
        preferenceManager = PreferenceManager()
        recyclerView = rootView.findViewById(R.id.failureRecyclerView)
        recyclerView.layoutManager = activity?.let { LinearLayoutManager(it) }
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(activity,RecyclerView.VERTICAL))
    }

    fun getMyFailures(){
        databaseReference = FirebaseDatabase.getInstance().getReference("failures")

        databaseReference.run {
            orderByChild("user").equalTo(preferenceManager.getLoggedEmail().toString())
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
                        val updateFinishedFailure = object :
                            UpdateFinishedFailure {
                            override fun updateFinished(id: String, state: String) {
                                updateInFirebase(id,state)
                                failureAdapterForNormalUsers.refreshData()
                            }

                        }
                        failureAdapterForNormalUsers =
                            FailureAdapterForNormalUsers(failures,updateFinishedFailure)

                        recyclerView.adapter = failureAdapterForNormalUsers

                        closeProgresBar()

                    }

                })
        }
    }
    private fun updateInFirebase(id: String, state : String){
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference()
        databaseReference.child("failures").child(id).child("finished").setValue(state)

    }
    private fun closeProgresBar(){
        progressBar.visibility = View.GONE
    }
}
