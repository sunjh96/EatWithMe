package com.cookandroid.eatwithme

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient
import java.text.SimpleDateFormat
import java.util.*

class AddPostActivity : AppCompatActivity() {
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
        var count : Int = 0
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef: DatabaseReference = database.getReference("posts")
        var title: String = findViewById<EditText>(R.id.tvWrite_title).getText().toString()
        var address: String = findViewById<EditText>(R.id.tvWrite_address).getText().toString()
        var content: String = findViewById<EditText>(R.id.tvWrite_contents).getText().toString()
        var simpleDateFormat : SimpleDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss")

        userRef.child("count").get()
            .addOnSuccessListener {
                count = "${it.value}".toInt()
                UserApiClient.instance.me { user, error ->
                    userRef.get()
                        .addOnSuccessListener {
                            userRef.child(count.toString())
                                .child("address").setValue(address)
                            userRef.child(count.toString())
                                .child("content").setValue(content)
                            userRef.child(count.toString())
                                .child("kakao_id").setValue(user?.id)
                            userRef.child(count.toString())
                                .child("kakao_name").setValue(user?.kakaoAccount?.profile?.nickname)
                            userRef.child(count.toString())
                                .child("createdAt").setValue(simpleDateFormat.format(Date()))
                            userRef.child(count.toString())
                                .child("title").setValue(title)
                            userRef.child("count").setValue(count + 1)
                        }.addOnFailureListener{
                            Log.e("firebase", "Error getting data", it)
                        }
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        Toast.makeText(this@AddPostActivity, "게시글이 등록 되었습니다.", Toast.LENGTH_SHORT).show()

    }
}