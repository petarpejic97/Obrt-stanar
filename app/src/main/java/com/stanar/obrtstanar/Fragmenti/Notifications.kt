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
import com.stanar.obrtstanar.Klase.Adapters.NotificationAdapter
import com.stanar.obrtstanar.Klase.FirebaseClass.Notification
import com.stanar.obrtstanar.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Notifications : Fragment() {
    private lateinit var rootView : View
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private lateinit var progressBar : ProgressBar

    private lateinit var databaseReference: DatabaseReference
    private var notifications : MutableList<Notification> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_notifications, container, false)

        initializeVariable()

        getNotifications()

        return rootView
    }
    private fun  initializeVariable(){
        progressBar = rootView.findViewById(R.id.progressBarNotifi)
        recyclerView = rootView.findViewById(R.id.notificationRecyclerView)
        recyclerView.layoutManager = activity?.let { LinearLayoutManager(it) }
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(activity,RecyclerView.VERTICAL))
    }
    private fun getNotifications(){
        databaseReference = FirebaseDatabase.getInstance().getReference("notifications")

        databaseReference
                .addValueEventListener(object : com.google.firebase.database.ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                    }
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        for (ds in dataSnapshot.children) {

                            val notifiation =
                                Notification(
                                    ds.child("title").getValue(String::class.java)!!,
                                    ds.child("notificationText").getValue(String::class.java)!!,
                                    ds.child("date").getValue(String::class.java)!!
                                )
                            notifications.add(notifiation)
                        }
                        notificationAdapter =
                            NotificationAdapter(
                                notifications
                            )

                        recyclerView.adapter = notificationAdapter

                        closeProgresBar()

                    }

                })
        }
        private fun closeProgresBar(){
            progressBar.visibility = View.GONE
        }
}
