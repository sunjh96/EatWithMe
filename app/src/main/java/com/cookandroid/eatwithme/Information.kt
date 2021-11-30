package com.cookandroid.eatwithme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient

class Information : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myinfo)

        val id = findViewById<TextView>(R.id.tvMyinfo_ID)
        val name = findViewById<TextView>(R.id.tvMyinfo_name)
        val rating = findViewById<TextView>(R.id.tvMyinfo_Phone)

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef : DatabaseReference = database.getReference("users")

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
            userRef.child(user?.kakaoAccount?.profile?.nickname.toString()).child("Rating").get().addOnSuccessListener {
                rating.text = "${it.value}"
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }
    }
}
