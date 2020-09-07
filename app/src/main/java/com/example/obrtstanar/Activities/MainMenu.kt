package com.example.obrtstanar.Activities

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.R

class MainMenu : AppCompatActivity(), View.OnClickListener {

    lateinit var imgLogOut : ImageView;
    lateinit var tvAboutUs : TextView;
    lateinit var tvNotification : TextView;
    lateinit var tvReportFailure : TextView;
    lateinit var tvMyReportFailures : TextView
    lateinit var tvFinancial : TextView;
    lateinit var tvBuildings : TextView;
    lateinit var tvImportantInfo : TextView;
    lateinit var tvGallery : TextView;
    lateinit var tvContact : TextView;
    lateinit var menu : Menu

    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        initializeVariable()
        setListeners()
    }

    fun initializeVariable(){
        tvAboutUs = findViewById(R.id.tvAbousUs)
        tvNotification = findViewById(R.id.tvNotification)
        tvContact = findViewById(R.id.tvContact)
        tvBuildings = findViewById(R.id.tvBuildings)
        tvReportFailure = findViewById(R.id.tvReport)
        tvMyReportFailures = findViewById(R.id.tvMyReportFailures)

        imgLogOut = findViewById(R.id.imgLogOut)
        preferenceManager = PreferenceManager()
    }
    fun setListeners(){
        tvAboutUs.setOnClickListener(this)
        tvNotification.setOnClickListener(this)
        tvContact.setOnClickListener(this)
        tvBuildings.setOnClickListener(this)
        tvReportFailure.setOnClickListener(this)
        tvMyReportFailures.setOnClickListener(this)
        imgLogOut.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvAbousUs -> {
                goOnFragment(FragmentContainer::class.java,"O nama")
            }
            R.id.tvNotification -> {
                goOnFragment(FragmentContainer::class.java,"Obavjesti")
            }
            R.id.tvReport -> {
                goOnFragment(FragmentContainer::class.java,"Prijava kvara")
            }
            R.id.tvMyReportFailures -> {
                goOnFragment(FragmentContainer::class.java,"Prijavljeni kvarovi")
            }
            R.id.tvContact -> {
                goOnFragment(FragmentContainer::class.java,"Kontakt")
            }
            R.id.tvBuildings -> {
                goOnFragment(FragmentContainer::class.java,"Zgrade pod upravom")
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