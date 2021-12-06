package com.cookandroid.eatwithme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mMap : GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MapActivity)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var lat : Double? = intent.getExtras()?.getDouble("lat")
        var lon : Double? = intent.getExtras()?.getDouble("lon")
        var loc : LatLng = LatLng(lat!!, lon!!)

        var makerOptions : MarkerOptions = MarkerOptions()
        makerOptions.position(loc).title("접선지")

        mMap.addMarker(makerOptions)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15F))
    }
}