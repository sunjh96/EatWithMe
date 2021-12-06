package com.cookandroid.eatwithme

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class Chat : AppCompatActivity() {
    private lateinit var tvChat_content : ListView

    private var firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference : DatabaseReference = firebaseDatabase.getReference()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_room)

        tvChat_content = findViewById(R.id.tvChat_content)

        showChatList()
    }

    private fun showChatList() {
        // 리스트 어댑터 생성 및 세팅
        val adapter : ArrayAdapter<String> = ArrayAdapter < String >(this,
            android.R.layout.simple_list_item_1, android.R.id.text1)
        tvChat_content.setAdapter(adapter)

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chats").addChildEventListener(object : ChildEventListener {
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