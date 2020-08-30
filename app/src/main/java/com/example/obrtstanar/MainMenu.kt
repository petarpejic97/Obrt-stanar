package com.example.obrtstanar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.obrtstanar.Klase.PreferenceManager

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
    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        initializeVariable()
        setListeners()
    }

    fun initializeVariable(){
        tvAboutUs = findViewById(R.id.tvAbousUs)
        tvContact = findViewById(R.id.tvContact)
        imgLogOut = findViewById(R.id.imgLogOut)
        preferenceManager = PreferenceManager()
    }
    fun setListeners(){
        tvAboutUs.setOnClickListener(this)
        tvContact.setOnClickListener(this)
        imgLogOut.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvAbousUs -> {
                goOnFragment(FragmentContainer::class.java,"O nama")
            }
            R.id.tvContact -> {
                goOnFragment(FragmentContainer::class.java,"Kontakt")
            }
            R.id.imgLogOut ->{
                goOnActivity(LoginUser::class.java)
            }
        }
    }
    private fun goOnFragment(classs: Class<*>,fragmentTitle : String) {
        val intent = Intent(this, classs)
        intent.putExtra("fragmentId", fragmentTitle)
        startActivity(intent)
        finish()
    }
    private fun goOnActivity(classs: Class<*>){
        setPreferences()
        val intent = Intent(this, classs)
        startActivity(intent)
        finish()
    }
    private fun setPreferences(){
        preferenceManager.saveLoggedEmail("Niste prijavljeni.")
        preferenceManager.setLoginStatus("false")
    }
}