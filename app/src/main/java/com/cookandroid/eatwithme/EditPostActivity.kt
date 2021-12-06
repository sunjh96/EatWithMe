package com.cookandroid.eatwithme

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient
import java.text.SimpleDateFormat
import java.util.*

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpost)

        var uploadBtn: Button = findViewById<Button>(R.id.btnWrite_ok)
        var btnWrite_back : Button = findViewById(R.id.btnWrite_back)

        btnWrite_back.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })


        uploadBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                upload()
                finish()
            }
        })
    }

    fun upload() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef: DatabaseReference = database.getReference("posts")
        var title: String = findViewById<EditText>(R.id.tvWrite_title).getText().toString()
        var address: String = findViewById<EditText>(R.id.tvWrite_address).getText().toString()
        var content: String = findViewById<EditText>(R.id.tvWrite_contents).getText().toString()
        var parent_key : Int? = getIntent().getExtras()?.getInt("parent_key")
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss")

        UserApiClient.instance.me { user, error ->
            userRef.get()
                .addOnSuccessListener {
                    userRef.child(parent_key.toString())
                        .child("address").setValue(address)
                    userRef.child(parent_key.toString())
                        .child("content").setValue(content)
                    userRef.child(parent_key.toString())
                        .child("kakao_id").setValue(user?.id)
                    userRef.child(parent_key.toString())
                        .child("kakao_name").setValue(user?.kakaoAccount?.profile?.nickname)
                    userRef.child(parent_key.toString())
                        .child("createdAt").setValue(simpleDateFormat.format(Date()))
                    userRef.child(parent_key.toString())
                        .child("title").setValue(title)
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
        }
    }
}