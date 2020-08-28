package com.example.obrtstanar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainMenu : AppCompatActivity(), View.OnClickListener {

    lateinit var imgLogOut : ImageView;
    lateinit var tvAboutUs : TextView;
    lateinit var tvNotification : TextView;
    lateinit var tvReportFailure : TextView;
    lateinit var tvFinancial : TextView;
    lateinit var tvBuildings : TextView;
    lateinit var tvImportantInfo : TextView;
    lateinit var tvGallery : TextView;
    lateinit var tvContact : TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        initializeVariable()
        setListeners()
    }

    fun initializeVariable(){
        tvAboutUs = findViewById(R.id.tvAbousUs)
        tvContact = findViewById(R.id.tvContact)
    }
    fun setListeners(){
        tvAboutUs.setOnClickListener(this)
        tvContact.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvAbousUs -> {
                goOnActivity(FragmentContainer::class.java,"O nama")
            }
            R.id.tvContact -> {
                goOnActivity(FragmentContainer::class.java,"Kontakt")
            }
        }
    }
    private fun goOnActivity(classs: Class<*>,fragmentTitle : String) {
        val intent = Intent(this, classs)
        intent.putExtra("fragmentId", fragmentTitle)
        startActivity(intent)
        finish()
    }
}