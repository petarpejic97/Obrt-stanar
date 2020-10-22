package com.example.obrtstanar.Fragmenti

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.getChannelId
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentContainer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.toolbox.Volley
import com.example.obrtstanar.Activities.MainMenu
import com.example.obrtstanar.Klase.Controllers.NotificationController
import com.example.obrtstanar.Klase.FirebaseClass.Notification
import com.example.obrtstanar.Klase.ObrtStanar
import com.example.obrtstanar.Klase.ProgressDialog
import com.example.obrtstanar.NotificationViewModel
import com.example.obrtstanar.R
import com.example.obrtstanar.UserViewModel
import com.example.obrtstanar.databinding.FragmentProfileBinding
import com.example.obrtstanar.databinding.FragmentShareNotificationBinding
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class ShareNotification : Fragment() {
    private lateinit var rootView : View
    private lateinit var viewModel : NotificationViewModel
    private lateinit var binding : FragmentShareNotificationBinding
    private lateinit var btnShareNotification : Button
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_share_notification, container, false)

        initVariables(inflater,container)

        setUpUI()

        setListeners()

        return binding.root
    }

    private fun initVariables(inflater: LayoutInflater,container: ViewGroup?){
        btnShareNotification  = rootView.findViewById(R.id.btnShareNotification)
        viewModel = ViewModelProviders.of(this).get(NotificationViewModel::class.java)
        binding = FragmentShareNotificationBinding.inflate(inflater,container,false)
    }

    private fun setUpUI(){
        binding.apply {
            notification = viewModel
            binding.notification?.title?.value = viewModel.title.value
            binding.notification?.notificationText?.value = viewModel.notificationText.value
        }
    }
    private fun setListeners(){
        binding.btnShareNotification.setOnClickListener {
            Log.w("AAA","22222")
            checkIsEmpty()
        }
    }
    private fun checkIsEmpty(){
        if(viewModel.title.value == null|| viewModel.notificationText.value == null){
            Toast.makeText(context,"Molimo Vas popunite sva polja.",Toast.LENGTH_LONG).show()
        }
        else{
            progressDialog = this.context?.let { ProgressDialog(it,"Postavljanje obavjesti","Molimo pričekajte...") }!!
            progressDialog.showDialog()
            saveNotificationInDatabase(Notification(binding.notification?.title?.value!!.toString(),binding.notification?.notificationText?.value!!.toString(),getCurrentDate()))
        }
    }
    private fun saveNotificationInDatabase(notification : Notification){
        val database = FirebaseDatabase.getInstance()
        val key = database.getReference("notifications").push().key
        val myRef = key?.let { database.getReference().child("notifications").child(it).setValue(notification) }
        myRef?.addOnSuccessListener {

            progressDialog.progresDismis()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate() : String {
        val sdf = SimpleDateFormat("dd.M.yyyy hh:mm:ss")
        return sdf.format(Date())
    }
}