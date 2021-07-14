package com.stanar.obrtstanar.Fragmenti

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.stanar.obrtstanar.Activities.SendEmail
import com.stanar.obrtstanar.Klase.Controllers.MapController
import com.stanar.obrtstanar.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class Contact : Fragment(),OnMapReadyCallback {
    protected lateinit var rootView: View

    private lateinit var tvPhoneNumber : TextView
    private lateinit var btnSendEmail : Button
    private lateinit var imgCall : ImageView

    private lateinit var mapController : MapController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_contact, container, false)

        initializeVariables()
        listener()
        anim()
        return rootView
    }

    private fun initializeVariables(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        tvPhoneNumber = rootView.findViewById(R.id.tvTelMob)
        btnSendEmail = rootView.findViewById(R.id.btnSendEmail)
        imgCall = rootView.findViewById(R.id.imgCall)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mapController =
            MapController(googleMap!!)
        mapController.setMapView(LatLng(45.2904889,18.8101935),14.0F)
        mapController.setMarkerTitle("Obrt Stanar")
    }

    private fun listener(){
        tvPhoneNumber.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+385992399777"))
            startActivity(intent)
        }

        btnSendEmail.setOnClickListener {
            val intent = Intent(activity, SendEmail::class.java)
            startActivity(intent)

        }
    }
    private fun anim(){
        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            tvPhoneNumber,
            PropertyValuesHolder.ofFloat("scaleX", 1.02f),
            PropertyValuesHolder.ofFloat("scaleY", 1.02f)
        )
        val scaleimg: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            imgCall,
            PropertyValuesHolder.ofFloat("scaleX", 1.04f),
            PropertyValuesHolder.ofFloat("scaleY", 1.04f)
        )
        scaleDown.duration = 500
        scaleDown.repeatCount = ObjectAnimator.INFINITE
        scaleDown.repeatMode = ObjectAnimator.REVERSE
        scaleDown.start()

        scaleimg.duration = 500
        scaleimg.repeatCount = ObjectAnimator.INFINITE
        scaleimg.repeatMode = ObjectAnimator.REVERSE
        scaleimg.start()
    }
}