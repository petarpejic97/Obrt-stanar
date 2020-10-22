package com.example.obrtstanar.Fragmenti

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.example.obrtstanar.Klase.FirebaseClass.Failure
import com.example.obrtstanar.Klase.ObrtStanar
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.Klase.ProgressDialog
import com.example.obrtstanar.R
import com.example.obrtstanar.ReportFailureViewModel
import com.example.obrtstanar.UserViewModel
import com.example.obrtstanar.databinding.FragmentProfileBinding
import com.example.obrtstanar.databinding.FragmentReportFailureBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_report_failure.*
import java.util.*


class ReportFailure : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var rootView : View

    private lateinit var binding : FragmentReportFailureBinding
    private lateinit var viewModel : ReportFailureViewModel

    //lateinit var swUserInfo : Switch

    lateinit var spinnerRapeirTime : Spinner
    lateinit var spinnerTypeOfFaliure : Spinner

    lateinit var repairTime: String
    lateinit var typeOfFailure: String
    lateinit var loadedPicture :ImageView
    lateinit var preferenceManager: PreferenceManager
    var selectedPhotoUri : Uri = Uri.EMPTY

    lateinit var progressDialog: ProgressDialog

    lateinit var fragmentTransaction: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_report_failure, container, false)

        initVariable(inflater,container)
        setUpUI()
        setListenerSubmitRequest()
        setListenerUploadPicture()
        switchListener()

        return binding.root
    }

    private fun initVariable(inflater: LayoutInflater,container: ViewGroup?){
        viewModel = ViewModelProviders.of(this).get(ReportFailureViewModel::class.java)
        binding = FragmentReportFailureBinding.inflate(inflater,container,false)

        spinnerRapeirTime = binding.root.findViewById(R.id.failureSpinner)
        spinnerTypeOfFaliure = binding.root.findViewById(R.id.typeOfFailureSpinner)
        //loadedPicture = rootView.findViewById(R.id.loadedPicture)

        preferenceManager = PreferenceManager()
        progressDialog = this.activity?.let { ProgressDialog(it,"Slanje kvara","Molimo vas pričekajte...") }!!

        spinnerRapeirTime.onItemSelectedListener = this
        spinnerTypeOfFaliure.onItemSelectedListener = this
        ArrayAdapter.createFromResource(ObrtStanar.ApplicationContext, R.array.failure_array, R.layout.spinneritem).also {
                adapterrepair -> adapterrepair.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerRapeirTime.adapter = adapterrepair
        }
        ArrayAdapter.createFromResource(ObrtStanar.ApplicationContext, R.array.typeoffalilure_array, R.layout.spinneritem).also {
                adaptertype -> adaptertype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTypeOfFaliure.adapter = adaptertype
        }

    }

    private fun setUpUI(){
        binding.apply {
            reportFailure = viewModel
        }
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        val spinner = parent as Spinner
        if (spinner.id == R.id.failureSpinner) {
            repairTime = parent.getItemAtPosition(position).toString()
        }
        else if (spinner.id == R.id.typeOfFailureSpinner) {
            typeOfFailure = parent.getItemAtPosition(position).toString()
        }

    }

    private fun setListenerSubmitRequest(){
        binding.btnSubmitRequest.setOnClickListener {
            Log.w("AAA","AAA")
            if(checkFields())
                uploadImageToFirebaseStorage()
            else
                Toast.makeText(activity,"Molimo Vas popunite sva polja.",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkFields():Boolean{
        return !(binding.reportFailure?.name?.value!!.isEmpty() || binding.reportFailure?.lastname?.value!!.isEmpty()
                || binding.reportFailure?.phoneNumber?.value!!.isEmpty() || binding.reportFailure?.address?.value!!.isEmpty()
                || binding.reportFailure?.description?.value!!.isEmpty()  )
    }

    private fun setListenerUploadPicture(){
        binding.btnLoadPicture.setOnClickListener {
            openGallery()
        }
    }

    private fun uploadImageToFirebaseStorage(){
        val filename = UUID.randomUUID().toString()
        val storageReference = FirebaseStorage.getInstance().getReference("/images/$filename")

        progressDialog.showDialog()

        if(selectedPhotoUri == Uri.EMPTY){
            saveFailureInDatabase("")
        }
        else{
            storageReference.putFile(selectedPhotoUri).addOnSuccessListener {
                storageReference.downloadUrl.addOnSuccessListener {
                    saveFailureInDatabase(it.toString())
                }
            }
        }

    }
    private fun saveFailureInDatabase(photoUri : String ){
        val failure = createFailure(photoUri)
        val database = FirebaseDatabase.getInstance()
        val key = database.getReference("failures").push().key
        val myRef = key?.let { database.getReference().child("failures").child(it).setValue(failure) }
        myRef?.addOnSuccessListener {
            progressDialog.progresDismis()
            Toast.makeText(activity,"Kvar poslan administratoru!",Toast.LENGTH_SHORT).show()
            goOnFragment(MyReportFailure())
        }
    }


    private fun createFailure(uri: String) : Failure {
        return Failure(
            binding.reportFailure?.name?.value.toString(),
            binding.reportFailure?.lastname?.value.toString(),
            binding.reportFailure?.address?.value.toString(),
            binding.reportFailure?.phoneNumber?.value.toString(),
            repairTime,
            typeOfFailure,
            binding.reportFailure?.description?.value.toString(),
            uri,
            "Kvar poslan",
            preferenceManager.getLoggedEmail().toString()
        )
    }
    private fun switchListener(){
        binding.swcUserInfo.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                Log.w("AAA",preferenceManager.getLoggedName().toString())
                binding.apply {
                    reportFailure = viewModel
                    viewModel.name.value = preferenceManager.getLoggedName()
                    viewModel.lastname.value = preferenceManager.getLoggedLastname()
                    viewModel.address.value = preferenceManager.getLoggedAddress()
                    viewModel.phoneNumber.value = preferenceManager.getLoggedPhoneNumber()

                }
            }
            else{
                binding.apply {
                    reportFailure = viewModel
                    viewModel.name.value = ""
                    viewModel.lastname.value = ""
                    viewModel.address.value = ""
                    viewModel.phoneNumber.value = ""

                }
            }
        }
    }

    private fun openGallery(){
        val openGallery = Intent(Intent.ACTION_PICK)
        openGallery.type = "image/*"
        startActivityForResult(openGallery,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){

            //loadedPicture.text = editPathToImgName(data.data.toString())
            binding.loadedPicture.setImageResource(R.drawable.yes)
            selectedPhotoUri = data.data!!

        }
    }

    private fun editPathToImgName(path : String) : String {
        val twoFimgname = path.substring(path.lastIndexOf("%")+1);
        return twoFimgname.substring(2,twoFimgname.length)
    }
    private fun goOnFragment(fragment: Fragment){
        val fragmentManager = this.getFragmentManager()!!
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_fragment,fragment)
        fragmentTransaction.commit()
    }
}