package com.cookandroid.eatwithme

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var CHAT_NAME : String
    private lateinit var KAKAO_NAME : String
    private lateinit var Chat_View : ListView
    private lateinit var tvChat_mycontent : EditText
    private lateinit var btnChat_send : Button
    //private lateinit var databaseRef: DatabaseReference

    var list = mutableListOf<String>()

    private var firebaseDatabase : FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference : DatabaseReference = firebaseDatabase.getReference()
    val userRef : DatabaseReference = firebaseDatabase.getReference("chats")

    protected override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var tvChat_check : Button = findViewById(R.id.tvChat_check)
        val intent_rating = Intent(this, RatingActivity :: class.java)
        var btn_comm_back : Button = findViewById(R.id.btnComm_back)

        Chat_View = findViewById(R.id.Chat_View)
        tvChat_mycontent = findViewById(R.id.tvChat_mycontent)
        btnChat_send = findViewById(R.id.btnChat_send)

        var intent : Intent = getIntent()
        CHAT_NAME = intent.getStringExtra("chat_name").toString()
        KAKAO_NAME = intent.getStringExtra("kakao_name").toString()

        //databaseRef = FirebaseDatabase.getInstance().reference

        var list1 = ArrayList<String>()
        userRef.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (rest in snapshot.child(CHAT_NAME).children){
                    list.add(rest.child("user_name").value.toString())
                }
                intent_rating.putStringArrayListExtra("nickname",list as java.util.ArrayList<String>)
            }
            override fun onCancelled(error: DatabaseError) {
                println("loadItem:onCancelled : ${error.toException()}")
            }
        })





        openChat(CHAT_NAME)

        btnChat_send.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View) {
                if (tvChat_mycontent.getText().toString().equals(""))
                    return

                var chat : ChatDTO = ChatDTO(KAKAO_NAME.toString(), tvChat_mycontent.getText().toString()) //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chats").child(CHAT_NAME).push().setValue(chat) // 데이터 푸쉬
                tvChat_mycontent.setText("") //입력창 초기화
            }
        });

        //if ( isChecked == true)
        tvChat_check.isEnabled = true

        tvChat_check.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if( list.distinct().size < 2) {
                    Toast.makeText(this@ChatActivity, "평가할 사람이 없습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else
                    startActivity(intent_rating)
            }
        })

        btn_comm_back.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        val baeminPackage = "com.sampleapp" //배민 패키지 주소
        val intentBaemin = packageManager.getLaunchIntentForPackage(baeminPackage)
        val yogiyoPackage = "com.fineapp.yogiyo" //요기요 패키지 주소
        val intentYogiyo = packageManager.getLaunchIntentForPackage(yogiyoPackage)
        val coupangEatsPackage = "com.coupang.mobile.eats" //쿠팡이츠 패키지 주소
        val intentCoupangEats = packageManager.getLaunchIntentForPackage(coupangEatsPackage)

        lateinit var baeminButton : Button
        lateinit var yogiyoButton : Button
        lateinit var coupangEatsButton : Button

        baeminButton = findViewById<Button>(R.id.baeminButton)
        yogiyoButton = findViewById<Button>(R.id.yogiyoButton)
        coupangEatsButton = findViewById<Button>(R.id.coupangEatsButton)

        baeminButton.setOnClickListener {
            try {
                startActivity(intentBaemin)
            } catch (e: Exception) {
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + baeminPackage))
                startActivity(intentPlayStore)
            }
        }

        yogiyoButton.setOnClickListener {
            try {
                startActivity(intentYogiyo)
            } catch (e: Exception) {
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + yogiyoPackage))
                startActivity(intentPlayStore)
            }
        }

        coupangEatsButton.setOnClickListener {
            try {
                startActivity(intentCoupangEats)
            } catch (e: Exception) {
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + coupangEatsPackage))
                startActivity(intentPlayStore)
            }
        }

    }

    private fun addMessage(dataSnapshot : DataSnapshot, adapter :  ArrayAdapter<String>) {
        var chatDTO : ChatDTO? = dataSnapshot.getValue(ChatDTO::class.java)
        adapter.add(chatDTO?.getUser_name() + " : " + chatDTO?.getMessage())
    }

    private fun removeMessage(dataSnapshot : DataSnapshot, adapter : ArrayAdapter<String>) {
        var chatDTO : ChatDTO? = dataSnapshot.getValue(ChatDTO::class.java);
        adapter.remove(chatDTO?.getUser_name() + " : " + chatDTO?.getMessage());
    }

    private fun openChat(chatName : String) {

        // 리스트 어댑터 생성 및 세팅
        val adapter : ArrayAdapter<String>
                = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1)
        Chat_View.setAdapter(adapter)


        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리
        databaseReference.child("chats").child(chatName).addChildEventListener(object : ChildEventListener {
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