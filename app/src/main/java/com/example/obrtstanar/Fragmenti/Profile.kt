package com.example.obrtstanar.Fragmenti

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.obrtstanar.Klase.FirebaseHelper
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.Klase.ProgressDialog
import com.example.obrtstanar.Klase.FirebaseClass.User
import com.example.obrtstanar.R
import com.example.obrtstanar.UserViewModel
import com.example.obrtstanar.databinding.FragmentProfileBinding
import com.google.firebase.database.*


class Profile(mcontext: Context) : Fragment() {

    lateinit var rootView: View
    private lateinit var viewModel : UserViewModel
    private lateinit var binding : FragmentProfileBinding
    private lateinit var loggedUser : User
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var progressDialog : ProgressDialog
    private lateinit var loggedkey : String
    private lateinit var updatedUser : User
    lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    init{
        val context = mcontext
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        initVariables(inflater,container)

        getLoggedUser()

        setListeners()

        checkIsAdmin()

        return binding.root
    }

    private fun initVariables(inflater: LayoutInflater,container: ViewGroup?){
        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        progressDialog = this.context?.let { ProgressDialog(it,"Učitavanje podataka","Molimo pričekajte...") }!!
        preferenceManager = PreferenceManager()

    }

    private fun getLoggedUser(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("users")

        progressDialog.showDialog()

        myRef.run {
            orderByChild("email").equalTo(preferenceManager.getLoggedEmail().toString())
                .addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener{
                    override fun onCancelled(error: DatabaseError) {
                    }
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (ds in dataSnapshot.children) {
                            loggedkey = ds.key.toString()
                            val name = ds.child("name").getValue(String::class.java)
                            val lastname = ds.child("lastName").getValue(String::class.java)
                            val phoneNumber = ds.child("phoneNumber").getValue(String::class.java)
                            val address = ds.child("address").getValue(String::class.java)
                            val email = ds.child("email").getValue(String::class.java)

                            createUser(name!!,lastname!!,phoneNumber!!,address!!,email!!)

                            progressDialog.progresDismis()
                        }
                    }

                })
        }

    }

    private fun createUser(name: String,lastname:String,phone: String,address: String, email: String){
        loggedUser = User(
            name,
            lastname,
            phone,
            address,
            email
        )

        fillFields()

    }

    private fun fillFields(){
        binding.apply {
            user = viewModel
            viewModel.name.value = loggedUser.name
            viewModel.lastname.value = loggedUser.lastName
            viewModel.email.value = loggedUser.email
            viewModel.address.value = loggedUser.address
            viewModel.phoneNumber.value = loggedUser.phoneNumber

        }
    }

    private fun setListeners(){
        binding.btnResetPassword.setOnClickListener {
            sendPasswordResetEmail()
        }
        binding.btnUpdateData.setOnClickListener {
            createUpdatedUser()
        }
        binding.btnSetNotification.setOnClickListener {
            goOnFragment(ShareNotification())
        }
    }

    private fun sendPasswordResetEmail(){
        val firebaseHelper = FirebaseHelper()
        this!!.context?.let { firebaseHelper.sendPasswordResetMail(it,preferenceManager.getLoggedEmail().toString()) }
    }

    private fun createUpdatedUser(){
        if(binding.user?.name?.value!!.isEmpty() || binding.user?.lastname?.value!!.isEmpty() || binding.user?.phoneNumber?.value!!.isEmpty() ||
            binding.user?.address?.value!!.isEmpty() || binding.user?.email?.value!!.isEmpty()){
            showToast("Molimo Vas popunite sva polja.")

        }
        else{
            updatedUser = User(
                binding.user?.name?.value.toString(),
                binding.user?.lastname?.value.toString(),
                binding.user?.phoneNumber?.value.toString(),
                binding.user?.address?.value.toString(),
                binding.user?.email?.value.toString()
            )
            updateUserInDatabase()
            saveInPreferenceManager(binding.user?.name?.value.toString(),binding.user?.lastname?.value.toString(),
                binding.user?.phoneNumber?.value.toString(),binding.user?.address?.value.toString(),binding.user?.email?.value.toString())
        }

    }

    private fun saveInPreferenceManager(name:String, lastName : String, phoneNumber : String, address : String, email : String){
        preferenceManager.saveLoggedName(name)
        preferenceManager.saveLoggedLastname(lastName)
        preferenceManager.savePhoneNumber(phoneNumber)
        preferenceManager.saveAddress(address)
        preferenceManager.saveLoggedEmail(email)
    }

    private fun updateUserInDatabase(){
        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.getReference()
        databaseReference.child("users").child(loggedkey)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    databaseReference.child("users/"+loggedkey).setValue(updatedUser);
                    showToast("Podaci ažurirani.")
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("User", databaseError.message)
                }
            })
    }

    private fun showToast(message : String){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }

    private fun checkIsAdmin(){
        if(preferenceManager.getLoggedEmail() == "petar.pejic@outlook.com"){
            binding.btnSeeFailures.visibility = VISIBLE
            binding.btnSetNotification.visibility = VISIBLE
        }
    }

    private  fun goOnFragment(fragment : Fragment){
        val fragmentManager = this.getFragmentManager()!!
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_fragment,fragment)
        fragmentTransaction.commit()
    }
}