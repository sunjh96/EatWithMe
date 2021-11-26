package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var user_chat : EditText
    private lateinit var user_edit : EditText
    private lateinit var user_next : Button
    private lateinit var chat_list : ListView

    private var firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference : DatabaseReference = firebaseDatabase.getReference()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_chat = findViewById(R.id.user_chat)
        user_edit = findViewById(R.id.user_edit)
        user_next = findViewById(R.id.user_next)
        chat_list = findViewById(R.id.chat_list)

        user_next.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View?) {
                if (user_edit.getText().toString().equals("") || user_chat.getText().toString().equals(""))
                    return

                var intent = Intent(applicationContext, ChatActivity::class.java)
                intent.putExtra("chatName", user_chat.getText().toString())
                intent.putExtra("userName", user_edit.getText().toString())
                startActivity(intent)
            }
        });

        showChatList()

    }

    private fun showChatList() {
        // 리스트 어댑터 생성 및 세팅
        val adapter : ArrayAdapter<String> = ArrayAdapter < String >(this,
            android.R.layout.simple_list_item_1, android.R.id.text1)
        chat_list.setAdapter(adapter)

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot : DataSnapshot, s : String?) {
                Log.e("LOG", "dataSnapshot.getKey() : " + dataSnapshot.getKey())
                adapter.add(dataSnapshot.getKey());
            }

            override fun onChildChanged(dataSnapshot : DataSnapshot, s : String?) {

            }

            override fun onChildRemoved(dataSnapshot : DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot : DataSnapshot, s : String?) {

            }

            override fun onCancelled(databaseError : DatabaseError) {

            }
        });
    }
}