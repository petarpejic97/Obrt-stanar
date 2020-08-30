package com.example.obrtstanar.Klase

import android.view.LayoutInflater
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapController(private val map: GoogleMap) {

    private lateinit var location : LatLng

    fun setMapView(loc2 : LatLng){
        setLocationMarker(loc2)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f))
        map.uiSettings.isZoomControlsEnabled = true
    }
    private fun setLocationMarker(loc : LatLng){
        location = loc
    }
    fun setMarkerTitle(title : String){
        map.addMarker(MarkerOptions().position(location).title(title))
    }
}