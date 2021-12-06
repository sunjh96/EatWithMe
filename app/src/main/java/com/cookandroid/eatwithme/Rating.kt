package com.cookandroid.eatwithme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Rating : AppCompatActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating)

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef : DatabaseReference = database.getReference("users")
        val nickname = findViewById<TextView>(R.id.nickname)

        userRef.child("장오").child("Name").get().addOnSuccessListener {
            nickname.text = "${it.value}"
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }

        var num : String
        val rating_btn = findViewById<Button>(R.id.btnRating)
        val rating_edt = findViewById<EditText>(R.id.edtRating)
        rating_btn.setOnClickListener {
            val num1 : Int = Integer.parseInt(rating_edt.text.toString())
            userRef.child("장오").child("Rating").setValue(num1)

            val intent = Intent(this, Main::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            finish()
        }

    }*/
}