package com.example.obrtstanar.Fragmenti

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.obrtstanar.Klase.FirebaseClass.Failure
import com.example.obrtstanar.Klase.ObrtStanar
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.Klase.ProgressDialog
import com.example.obrtstanar.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_report_failure.*
import java.util.*


class ReportFailure : Fragment(), AdapterView.OnItemSelectedListener {
    lateinit var rootView : View

    lateinit var edName : EditText
    lateinit var edLastname : EditText
    lateinit var edAddress : EditText
    lateinit var edPhoneNumber : EditText
    lateinit var swUserInfo : Switch
    lateinit var edDescription : EditText
    lateinit var spinnerRapeirTime : Spinner
    lateinit var spinnerTypeOfFaliure : Spinner
    lateinit var btnSubmitRequest : Button
    lateinit var btnLoadPicture : Button
    lateinit var repairTime: String
    lateinit var typeOfFailure: String
    lateinit var loadedPicture :TextView
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

        initVariable()

        setListenerSubmitRequest()
        setListenerUploadPicture()
        switchListener()

        return rootView
    }

    private fun initVariable(){
        edName = rootView.findViewById(R.id.edName)
        edLastname = rootView.findViewById(R.id.edLastname)
        edAddress = rootView.findViewById(R.id.edAddress)
        edPhoneNumber = rootView.findViewById(R.id.edPhoneNumber)
        edDescription = rootView.findViewById(R.id.edFailureDescription)
        swUserInfo = rootView.findViewById(R.id.swcUserInfo)
        btnLoadPicture = rootView.findViewById(R.id.btnLoadPicture)
        btnSubmitRequest = rootView.findViewById(R.id.btnSubmitRequest)
        spinnerRapeirTime = rootView.findViewById(R.id.failureSpinner)
        spinnerTypeOfFaliure = rootView.findViewById(R.id.typeOfFailureSpinner)
        loadedPicture = rootView.findViewById(R.id.loadedPicture)

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
        btnSubmitRequest.setOnClickListener {
            if(checkFields())
                uploadImageToFirebaseStorage()
            else
                Toast.makeText(activity,"Molimo Vas popunite sva polja.",Toast.LENGTH_LONG).show()
        }
    }

    private fun checkFields():Boolean{
        return !(edAddress.text.isEmpty() ||edDescription.text.isEmpty() ||edLastname.text.isEmpty() || edName.text.isEmpty())
    }

    private fun setListenerUploadPicture(){
        btnLoadPicture.setOnClickListener {
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
            edName.text.toString(),
            edLastname.text.toString(),
            edAddress.text.toString(),
            edPhoneNumber.text.toString(),
            repairTime,
            typeOfFailure,
            edFailureDescription.text.toString(),
            uri,
            "Kvar poslan",
            preferenceManager.getLoggedEmail().toString()
        )
    }
    private fun switchListener(){
        swUserInfo.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                edName.text = preferenceManager.getLoggedName()!!.toEditable()
                edLastname.text = preferenceManager.getLoggedLastname()!!.toEditable()
                edAddress.text = preferenceManager.getLoggedAddress()!!.toEditable()
                edPhoneNumber.text = preferenceManager.getLoggedPhoneNumber()!!.toEditable()
            }
            else{
                edName.text = "".toEditable()
                edLastname.text = "".toEditable()
                edAddress.text = "".toEditable()
                edPhoneNumber.text = "".toEditable()
            }
        }
    }
    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun openGallery(){
        val openGallery = Intent(Intent.ACTION_PICK)
        openGallery.type = "image/*"
        startActivityForResult(openGallery,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){

            loadedPicture.text = editPathToImgName(data.data.toString())
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