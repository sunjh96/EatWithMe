package com.example.chat

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var CHAT_NAME : String
    private lateinit var USER_NAME : String

    private lateinit var chat_view : ListView
    private lateinit var chat_edit : EditText
    private lateinit var chat_send : Button

    private var firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference : DatabaseReference = firebaseDatabase.getReference()

    protected override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chat_view = findViewById(R.id.chat_view)
        chat_edit = findViewById(R.id.chat_edit)
        chat_send = findViewById(R.id.chat_send)

        var intent : Intent = getIntent()
        CHAT_NAME = intent.getStringExtra("chatName").toString()
        USER_NAME = intent.getStringExtra("userName").toString()

        openChat(CHAT_NAME)

        chat_send.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View) {
                if (chat_edit.getText().toString().equals(""))
                    return

                var chat : ChatDTO = ChatDTO(USER_NAME, chat_edit.getText().toString()) //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat) // 데이터 푸쉬
                chat_edit.setText("") //입력창 초기화

            }
        });
    }

    private fun addMessage(dataSnapshot : DataSnapshot, adapter :  ArrayAdapter<String>) {
        var chatDTO : ChatDTO? = dataSnapshot.getValue(ChatDTO::class.java)
        adapter.add(chatDTO?.getUserName() + " : " + chatDTO?.getMessage())
    }

    private fun removeMessage(dataSnapshot : DataSnapshot, adapter : ArrayAdapter<String>) {
        var chatDTO : ChatDTO? = dataSnapshot.getValue(ChatDTO::class.java);
        adapter.remove(chatDTO?.getUserName() + " : " + chatDTO?.getMessage());
    }

    private fun openChat(chatName : String) {

        // 리스트 어댑터 생성 및 세팅
        val adapter : ArrayAdapter<String>
                = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1)
        chat_view.setAdapter(adapter)


        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chat").child(chatName).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot : DataSnapshot, s : String?) {
                addMessage(dataSnapshot, adapter);
                Log.e("LOG", "s:"+s);
            }

            override fun onChildChanged(dataSnapshot : DataSnapshot, s : String?) {

            }

            override fun onChildRemoved(dataSnapshot : DataSnapshot) {
                removeMessage(dataSnapshot, adapter);
            }

            override fun onChildMoved(dataSnapshot : DataSnapshot, s : String?) {

            }

            override fun onCancelled(databaseError : DatabaseError) {

            }
        });
    }
}