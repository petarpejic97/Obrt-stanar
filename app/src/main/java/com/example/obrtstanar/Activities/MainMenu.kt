package com.example.obrtstanar.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.R
import com.example.obrtstanar.databinding.ActivityMainMenuBinding

class MainMenu : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainMenuBinding
    lateinit var imgLogOut : ImageView;

    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        initializeVariable()
        setListeners()
    }

    fun initializeVariable(){

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_menu)

        preferenceManager = PreferenceManager()
    }
    fun setListeners(){
        binding.tvAbousUs.setOnClickListener(this)
        binding.tvNotification.setOnClickListener(this)
        binding.tvReport.setOnClickListener(this)
        binding.tvMyReportFailures.setOnClickListener(this)
        binding.tvFinancial.setOnClickListener(this)
        binding.tvBuildings.setOnClickListener(this)
        binding.tvImportantInfo.setOnClickListener(this)
        binding.tvContact.setOnClickListener(this)
        binding.imgLogOut.setOnClickListener(this)
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
            R.id.tvFinancial -> {
                goOnFragment(FragmentContainer::class.java,"Financijsko stanje")
            }
            R.id.tvContact -> {
                goOnFragment(FragmentContainer::class.java,"Kontakt")
            }
            R.id.tvBuildings -> {
                goOnFragment(FragmentContainer::class.java,"Zgrade pod upravom")
            }
            R.id.tvImportantInfo -> {
                goOnFragment(FragmentContainer::class.java,"Korisne informacije")
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