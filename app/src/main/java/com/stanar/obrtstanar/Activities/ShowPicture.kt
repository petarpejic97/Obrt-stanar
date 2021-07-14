package com.stanar.obrtstanar.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.stanar.obrtstanar.Klase.ProgressDialog
import com.stanar.obrtstanar.R
import com.stanar.obrtstanar.ShowPictureViewModel
import com.stanar.obrtstanar.databinding.ActivityShowPictureBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show_picture.*

class ShowPicture : AppCompatActivity() {
    lateinit var imgfFailure : ImageView
    private lateinit var viewModel : ShowPictureViewModel
    private lateinit var binding : ActivityShowPictureBinding

    private lateinit var progressDialog : ProgressDialog

    lateinit var imgUri : String
    lateinit var typeOfFailure : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_picture)

        initializeVariable()
        setUpUi()
        backListener()
        Log.w("FFF","UDEM")
    }
    private fun initializeVariable(){
        val bundle = intent.extras
        imgUri = bundle?.getString("uriImg").toString()
        typeOfFailure = bundle?.getString("typeoffailure").toString()

        imgfFailure = findViewById(R.id.failureImage)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_picture)
        viewModel = ViewModelProviders.of(this).get(ShowPictureViewModel::class.java)

        progressDialog = ProgressDialog(this,"Učitavanje fotografije","Molimo Pričekajte...")

    }
    private fun setUpUi(){
        binding.apply {
            showPic = viewModel
            viewModel.typeoffailure.value = typeOfFailure
        }
        progressDialog.showDialog()
        setImage()
    }
    private fun setImage(){
        if(imgUri != "NoImg"){
            Picasso.get().
            load(imgUri).
            placeholder(R.drawable.noimg).
            fit().
            centerCrop().
            into(failureImage, object: com.squareup.picasso.Callback {
                override fun onSuccess() {
                    progressDialog.progresDismis()
                }

                override fun onError(e: java.lang.Exception?) {
                    Log.w("AAA",e)
                }
            })
        }
        else{
            Picasso.get().
            load(R.drawable.noimg).
            fit().
            centerCrop().
            into(failureImage, object: com.squareup.picasso.Callback {
                override fun onSuccess() {
                    progressDialog.progresDismis()
                }

                override fun onError(e: java.lang.Exception?) {
                    Log.w("AAA",e)
                }
            })
        }


    }
    private fun backListener(){
        binding.imgBack.setOnClickListener {
            val intent = Intent(this, FragmentContainer::class.java)
            intent.putExtra("fragmentId", "SeeAllFailures")
            startActivity(intent)
        }
    }
}