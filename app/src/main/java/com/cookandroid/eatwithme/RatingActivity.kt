package com.cookandroid.eatwithme

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rating)

        val name = findViewById<TextView>(R.id.tvRating_ID)
        val rbRating_rating = findViewById<RatingBar>(R.id.rbRating_rating)
        val btnWrite_ok: Button = findViewById(R.id.btnWrite_ok)
        val btnWrite_back: Button = findViewById(R.id.btnWrite_back)

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef: DatabaseReference = database.getReference("users")

        var rating_before: Float
        var count: Int = 0
        var nickname = ArrayList<String>()

        var list = intent.getSerializableExtra("nickname") as ArrayList<String>

        nickname = list.distinct() as ArrayList<String>
        UserApiClient.instance.me { user, error ->
            nickname.remove(user?.kakaoAccount?.profile?.nickname)
        }

        val spnFirst =  findViewById(R.id.spinner) as Spinner
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nickname)
        var rating_name : String = ""

        spnFirst.adapter = adapter
        spnFirst.setSelection(1)
        spnFirst.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                rating_name = nickname[position]
                name.text = nickname[position]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {
            }
        }

        rbRating_rating.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            rbRating_rating.rating = rating

            btnWrite_ok.setOnClickListener {
                userRef.child(rating_name).child("Count").get()
                    .addOnSuccessListener {
                        count = "${it.value}".toInt()

                        userRef.child(rating_name).child("Count").setValue(count + 1)

                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                userRef.child(rating_name).child("Rating").get()
                    .addOnSuccessListener {
                        rating_before = "${it.value}".toFloat()

                        userRef.child(rating_name).child("Rating").setValue(rating + rating_before)

                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                Toast.makeText(this@RatingActivity, "평가를 등록 하였습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnWrite_back.setOnClickListener { onBackPressed() }

    }
}