package com.cookandroid.eatwithme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient

class Information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myinfo)
        var doubleBackToExit = false
        val id = findViewById<TextView>(R.id.tvMyinfo_ID)
        val name = findViewById<TextView>(R.id.tvMyinfo_name)
        val count = findViewById<TextView>(R.id.tvMyinfo_Count)
        var btnComm_back : Button = findViewById(R.id.btnComm_back)
        var rbMyinfo : RatingBar = findViewById(R.id.rbMyinfo)

        var cal_rating : Float = 0.0F
        var cal_count : Float = 0.0F

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef : DatabaseReference = database.getReference("users")


        btnComm_back.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                fun runDelayed(millis: Long, function: () -> Unit) {
                    Handler(Looper.getMainLooper()).postDelayed(function, millis)
                }

                fun onBackPressed() {
                    if (doubleBackToExit) {
                        finishAffinity()
                    } else {
                        Toast.makeText(this@Information, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                        doubleBackToExit = true
                        runDelayed(1500L) {
                            doubleBackToExit = false
                        }
                    }
                }
                onBackPressed()
            }
        })

        UserApiClient.instance.me { user, error ->
            userRef.child(user?.kakaoAccount?.profile?.nickname.toString()).child("ID").get().addOnSuccessListener {
                id.text = "${it.value}"
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
            userRef.child(user?.kakaoAccount?.profile?.nickname.toString()).child("Name").get().addOnSuccessListener {
                name.text = "${it.value}"
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
            userRef.child(user?.kakaoAccount?.profile?.nickname.toString()).child("Count").get().addOnSuccessListener {
                count.text = "${it.value}" + "번 매칭 성공!"
                cal_count = "${it.value}".toFloat()
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
            userRef.child(user?.kakaoAccount?.profile?.nickname.toString()).child("Rating").get().addOnSuccessListener {
                cal_rating= "${it.value}".toFloat()
                if ( cal_count.toInt() == 0 )
                    rbMyinfo.setRating(0F)
                else
                    rbMyinfo.setRating(cal_rating / cal_count)
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }
    }
}
