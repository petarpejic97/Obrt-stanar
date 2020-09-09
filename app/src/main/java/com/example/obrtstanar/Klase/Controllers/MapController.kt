package com.example.obrtstanar.Klase.Controllers

import android.view.LayoutInflater
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapController(private val map: GoogleMap) {
    private var markerOptions : MarkerOptions = MarkerOptions()


    fun setMapView(loc : LatLng,zoom : Float){
        markerOptions.position(loc)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.position, zoom))
        map.uiSettings.isZoomControlsEnabled = true
    }
    fun setMarkerTitle(title : String){
        map.addMarker(MarkerOptions().position(markerOptions.position).title(title))
    }
    fun setMarker(location : LatLng){
        markerOptions.position(location)
        map.addMarker(markerOptions)
    }
}