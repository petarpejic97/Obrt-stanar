package com.stanar.obrtstanar.Activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.stanar.obrtstanar.Klase.PreferenceManager
import com.stanar.obrtstanar.R
import com.stanar.obrtstanar.databinding.ActivityMainMenuBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainMenu : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityMainMenuBinding
    private lateinit var flipper : ViewFlipper
    private lateinit var imageSlider : ImageSlider
    private lateinit var databaseReference: DatabaseReference

    lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initializeVariable()
        setSlider()
        setListeners()
    }

    fun initializeVariable(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main_menu)

        preferenceManager = PreferenceManager()

        imageSlider = findViewById(R.id.image_slider)
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
        binding.imgFacebook.setOnClickListener(this)
        binding.imgInstagram.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvAbousUs -> {
                goOnFragment(FragmentContainer::class.java,"O nama")
            }
            R.id.tvNotification -> {
                goOnFragment(FragmentContainer::class.java,"Obavijesti")
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
            R.id.imgFacebook ->{
                openWeb("https://www.facebook.com/Obrt-Stanar-1138156759628736/")
            }
            R.id.imgInstagram ->{
                openWeb("https://www.instagram.com/stanar_upravljanje/?igshid=1s50nkngocyfv")
            }
        }
    }
    private fun goOnFragment(classs: Class<*>,fragmentTitle : String) {
        val intent = Intent(this, classs)
        intent.putExtra("fragmentId", fragmentTitle)
        startActivity(intent)
        //finish()
    }
    private fun setSlider(){
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider) // init imageSlider

        val imageList = ArrayList<SlideModel>()
        val webList = ArrayList<String>()

        databaseReference = FirebaseDatabase.getInstance().getReference("sponsors")

        databaseReference
            .addValueEventListener(object : com.google.firebase.database.ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    for (ds in dataSnapshot.children) {
                        val slidemodel  = SlideModel(ds.child("imageUrl").value.toString(), ds.child("title").value.toString(),
                            ScaleTypes.FIT)
                        webList.add(ds.child("web").value.toString())
                        imageList.add(slidemodel)
                    }
                    imageSlider.setImageList(imageList)
                    imageSlider.setItemClickListener(object : ItemClickListener {
                        override fun onItemSelected(position: Int) {
                            openWeb(webList[position])
                        }
                    })
                }
            })
    }
    private fun goOnActivity(classs: Class<*>){
        setPreferences()
        val intent = Intent(this, classs)
        startActivity(intent)
        finish()
    }
    private fun setPreferences(){
        preferenceManager.saveLoggedEmail("Niste prijavljeni.")
        preferenceManager.saveLoginStatus("false")
    }
    private fun openWeb(website : String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
        startActivity(browserIntent)
    }
}