package com.example.obrtstanar

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.obrtstanar.Fragmenti.AboutUs
import com.example.obrtstanar.Fragmenti.Contact
import com.google.android.material.navigation.NavigationView
import kotlin.properties.Delegates

class FragmentContainer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout : DrawerLayout
    lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    lateinit var toolbar : androidx.appcompat.widget.Toolbar
    lateinit var navigationView : NavigationView

    lateinit var fragmentManager: FragmentManager
    lateinit var fragmentTransaction: FragmentTransaction

    var firstFragment : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)

        initializeVariable()

        setNavigationDrawer()

        goOnFragment(intent.getStringExtra("fragmentId")!!)
    }

    private fun initializeVariable() {
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigationView)
    }

    private fun setNavigationDrawer(){
        setSupportActionBar(toolbar)
        navigationView.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        goOnFragment(item.title.toString())
        return true
    }

    fun goOnFragment(fragmentTitle : String){
        when (fragmentTitle) {
            "O nama" -> {
                if(firstFragment){
                    openFirstFragment(AboutUs())
                }
                else{
                    replaceFragment(AboutUs())
                }
            }
            "Obavjesti" -> {

            }
            "Prijava kvara" -> {

            }
            "Financijsko stanje" -> {

            }
            "Zgrade pod upravom"-> {

            }
            "Korisne informacije" -> {

            }
            "Galerija slika" -> {

            }
            "Kontakt" -> {
                if(firstFragment){
                    openFirstFragment(Contact())
                }
                else{
                    replaceFragment(Contact())
                }

            }
            "Postavke" -> {

            }
            "Odjava" -> {

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
}