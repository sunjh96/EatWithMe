package com.example.eatwithme

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var writingarr = ArrayList<MyItem>()

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.letter)

        //테스트

        var letter_contents : TextView = findViewById(R.id.tvLetter_contents)
        letter_contents.text = "안녕하세요ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"
        writingarr.add(MyItem("hi", "hh", "2021-09-09", "09:09"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))

        viewManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(
            writingarr,
            R.layout.chat_recycleview_item,
            R.id.tvChat_content,
            R.id.tvChat_name,
            R.id.tvChat_date,
            R.id.tvChat_time
        )
        recyclerViewAdapter.mListener = object : RecyclerViewAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val writing = writingarr[position]
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.Letter_recyclerView).apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }

        //recyclerViewAdapter.notifyDataSetChanged()
    }
}