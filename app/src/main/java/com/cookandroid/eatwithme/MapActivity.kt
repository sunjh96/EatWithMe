package com.cookandroid.eatwithme

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var mMap : GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        var lat : Double? = intent.getExtras()?.getDouble("lat")
        var lon : Double? = intent.getExtras()?.getDouble("lon")
        var loc : LatLng = LatLng(lat!!, lon!!)
        var writer : String? = intent.getStringExtra("writer")

        var makerOptions : MarkerOptions = MarkerOptions()
        makerOptions.position(loc).title(writer)
        var bitmapdraw : BitmapDrawable = resources.getDrawable(R.drawable.writer) as BitmapDrawable
        var b : Bitmap = bitmapdraw.bitmap
        var smallMarker : Bitmap = Bitmap.createScaledBitmap(b,200,200,false)
        makerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
        makerOptions.alpha(0.7f)

        mMap.addMarker(makerOptions)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15F))
    }
}