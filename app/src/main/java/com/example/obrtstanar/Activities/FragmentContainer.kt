package com.example.obrtstanar.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.obrtstanar.Fragmenti.*
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.R
import com.google.android.material.navigation.NavigationView

class FragmentContainer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout : DrawerLayout
    lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    lateinit var toolbatTitle : TextView
    lateinit var navigationView : NavigationView
    lateinit var hView: View
    lateinit var drawerHeaderName : TextView
    lateinit var drawerHeaderEmail : TextView

    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction
    lateinit var preferenceManager : PreferenceManager

    var firstFragment : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        initializeVariable()

        setNavigationDrawer()

        setHeaderInfo()

        goOnFragment(intent.getStringExtra("fragmentId")!!)
    }

    private fun initializeVariable() {
        toolbar = findViewById(R.id.toolbar)
        toolbatTitle = toolbar.findViewById(R.id.toolbar_title)
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigationView)
        hView = navigationView.getHeaderView(0)
        drawerHeaderName = hView.findViewById(R.id.drawerHeaderName)
        drawerHeaderEmail = hView.findViewById(R.id.drawerHeaderEmail)
        preferenceManager = PreferenceManager()
    }

    private fun setNavigationDrawer(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navigationView.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()
    }

    private fun setHeaderInfo(){
        drawerHeaderEmail.text = preferenceManager.getLoggedEmail()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        goOnFragment(item.title.toString())
        return true
    }

    fun goOnFragment(fragmentTitle : String){
        when (fragmentTitle) {
            "O nama" -> {
                toolbatTitle.text="O nama"
                if(firstFragment){
                    openFirstFragment(AboutUs())
                    firstFragment=false

                }
                else{
                    replaceFragment(AboutUs())
                }
            }
            "Obavjesti" -> {

            }
            "Prijava kvara" -> {
                toolbatTitle.text="Prijava kvara"
                if(firstFragment){
                    openFirstFragment(ReportFailure())
                    firstFragment=false

                }
                else{
                    replaceFragment(ReportFailure())
                }
            }
            "Prijavljeni kvarovi" -> {
                toolbatTitle.text="Prijavljeni kvarovi"
                if(firstFragment){
                    openFirstFragment(MyReportFailure())
                    firstFragment=false

                }
                else{
                    replaceFragment(MyReportFailure())
                }
            }
            "Financijsko stanje" -> {

            }
            "Zgrade pod upravom"-> {
                toolbatTitle.text="Zgrade pod upravom"
                if(firstFragment){
                    openFirstFragment(OurBuildings())
                    firstFragment=false

                }
                else{
                    replaceFragment(OurBuildings())
                }
            }
            "Korisne informacije" -> {

            }
            "Galerija slika" -> {

            }
            "Kontakt" -> {
                toolbatTitle.text="Kontakt"
                if(firstFragment){
                    openFirstFragment(Contact())
                    firstFragment=false
                }
                else{
                    replaceFragment(Contact())
                }

            }
            "Profil" -> {
                toolbatTitle.text="Profil"
                if(firstFragment){
                    openFirstFragment(Profile(this))
                    firstFragment=false
                }
                else{
                    replaceFragment(Profile(this))
                }
            }
            "Odjava" -> {
                goOnActivity(LoginUser::class.java)
            }
        }

    }

    fun openFirstFragment(fragment : Fragment){
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.container_fragment,fragment)
        fragmentTransaction.commit()
        firstFragment = false
    }

    fun replaceFragment(fragment: Fragment){
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_fragment,fragment)
        fragmentTransaction.commit()
    }
    private fun goOnActivity(classs: Class<*>) {
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