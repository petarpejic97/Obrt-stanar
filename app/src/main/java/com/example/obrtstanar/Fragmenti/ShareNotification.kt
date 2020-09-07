package com.example.obrtstanar.Fragmenti

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.obrtstanar.Klase.FirebaseClass.Notification
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
            viewModel.title.value=""
            viewModel.notificationText.value=""
        }
    }
    private fun setListeners(){
        binding.btnShareNotification.setOnClickListener {
            checkIsEmpty()
        }
    }
    private fun checkIsEmpty(){
        if(binding.notification?.title?.value!!.isEmpty() || binding.notification?.notificationText?.value!!.isEmpty()){
            Toast.makeText(context,"Molimo Vas popunite sva polja.",Toast.LENGTH_LONG).show()
        }
        else{
            progressDialog = this.context?.let { ProgressDialog(it,"Postavljanje obavjesti","Molimo pričekajte...") }!!
            progressDialog.showDialog()
            saveNotificationInDatabase(createNotification(binding.notification?.title?.value!!.toString(),binding.notification?.notificationText?.value!!.toString(),getCurrentDate()))
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

    private fun createNotification(title : String, notifiText : String,date : String) : Notification{
        return Notification(title,notifiText,date)
    }
}