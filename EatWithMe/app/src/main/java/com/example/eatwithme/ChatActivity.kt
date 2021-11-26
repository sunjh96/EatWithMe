package com.example.eatwithme

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class ChatActivity : AppCompatActivity() {
    private var writingarr = ArrayList<MyItem>()

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter_chat_letter

    lateinit var baeminButton : Button
    lateinit var yogiyoButton : Button
    lateinit var coupangEatsButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("안녕하세요ㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛㅛ", "hh", "2021-09-09", "09:09"))

        viewManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter_chat_letter(writingarr, R.layout.chat_recycleview_item, R.id.tvChat_content, R.id.tvChat_name, R.id.tvChat_date, R.id.tvChat_time)
        recyclerViewAdapter.mListener = object : RecyclerViewAdapter_chat_letter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val writing = writingarr[position]
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.Chat_recyclerView).apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }

        baeminButton = findViewById<Button>(R.id.baeminButton)
        yogiyoButton = findViewById<Button>(R.id.yogiyoButton)
        coupangEatsButton = findViewById<Button>(R.id.coupangEatsButton)


        val baeminPackage = "com.sampleapp" //배민 패키지 주소
        val intentBaemin = packageManager.getLaunchIntentForPackage(baeminPackage)
        val yogiyoPackage = "com.fineapp.yogiyo" //요기요 패키지 주소
        val intentYogiyo = packageManager.getLaunchIntentForPackage(yogiyoPackage)
        val coupangEatsPackage = "com.coupang.mobile.eats" //쿠팡이츠 패키지 주소
        val intentCoupangEats = packageManager.getLaunchIntentForPackage(coupangEatsPackage)

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
}