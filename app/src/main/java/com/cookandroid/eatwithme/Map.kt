package com.cookandroid.eatwithme

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class Map : AppCompatActivity() {

    var lat : Double? = null
    var lon : Double? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        var b : Button = findViewById(R.id.button)
        var b2 : Button = findViewById(R.id.button2)
        var et : EditText = findViewById(R.id.editText)
        var tv : TextView = findViewById(R.id.textView)
        var tv2 : TextView = findViewById(R.id.textView2)
        var geocoder : Geocoder = Geocoder(this)


        b.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                var list : List<Address>? = null

                var str : String = et.getText().toString()

                try {
                    list = geocoder.getFromLocationName(str, 10)
                } catch (e : IOException) {
                    e.printStackTrace()
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 오류발생")
                }
                if (list != null) {
                    if (list!!.size == 0)
                        Toast.makeText(applicationContext, "해당 주소는 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    else {
                        var addr : Address  = list!!.get(0)
                        lat = addr.latitude
                        lon = addr.longitude
                        var intent = Intent(applicationContext, MapActivity::class.java)
                        intent.putExtra("lat", lat)
                        intent.putExtra("lon", lon)
                        startActivity(intent)
                    }
                }

            }
        })

        b2.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v : View) {
                var list : List<Address>? = null;

                var str : String = et.getText().toString();
                try {
                    list = geocoder.getFromLocationName(
                        str, // 지역 이름
                        10); // 읽을 개수
                } catch (e : IOException) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list!!.size == 0)
                        Toast.makeText(applicationContext, "해당 주소는 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    else {
                        tv.setText(list!!.get(0).latitude.toString())
                        tv2.setText(list!!.get(0).longitude.toString())
                    }
                }
            }
        })
    }
}