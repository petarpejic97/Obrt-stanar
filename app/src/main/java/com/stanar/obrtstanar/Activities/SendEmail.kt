package com.stanar.obrtstanar.Activities

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.stanar.obrtstanar.R
import com.stanar.obrtstanar.SendEmailViewModel
import com.stanar.obrtstanar.databinding.ActivitySendEmailBinding
import com.stanar.obrtstanar.databinding.ActivityShowPictureBinding

class SendEmail : AppCompatActivity() {

    private lateinit var viewModel : SendEmailViewModel
    private lateinit var binding : ActivitySendEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_email)

        initializeVariable()
        setUpUi()
        listeners()
    }
    private fun initializeVariable(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_send_email)
        viewModel = ViewModelProviders.of(this).get(SendEmailViewModel::class.java)
    }

    private fun setUpUi(){
        binding.apply {
            email = viewModel
            binding.email?.subject?.value = viewModel.subject.value
            binding.email?.body?.value = viewModel.body.value
        }
    }
    private fun listeners(){
        binding.imgBack.setOnClickListener {
            val intent = Intent(this, FragmentContainer::class.java)
            intent.putExtra("fragmentId", "Kontakt")
            startActivity(intent)
            finish()
        }
        binding.btnSendEmail.setOnClickListener{
            checkIsEmpty()
        }
    }
    private fun checkIsEmpty(){
        if(viewModel.subject.value == null|| viewModel.body.value == null){
            Toast.makeText(this,"Molimo Vas popunite sva polja.", Toast.LENGTH_LONG).show()
        }
        else{
           sendEmail()
        }
    }
    private fun sendEmail(){
        val i = Intent(Intent.ACTION_SEND)
           i.type = "message/rfc822"
           i.putExtra(Intent.EXTRA_EMAIL, arrayOf("obrt.stanar@gmail.com "))
           i.putExtra(Intent.EXTRA_SUBJECT, viewModel.subject.value)
           i.putExtra(Intent.EXTRA_TEXT, viewModel.body.value)
           try {
               startActivity(Intent.createChooser(i,  "Slanje...."))

           } catch (ex: ActivityNotFoundException) {
               Toast.makeText(this, "Molimo Vas preuzmite aplikaciju za slanje e-maila", Toast.LENGTH_SHORT).show()
           }
        resetFields()
    }
    private fun resetFields(){
        viewModel.subject.value = ""
        viewModel.body.value = ""
    }
}