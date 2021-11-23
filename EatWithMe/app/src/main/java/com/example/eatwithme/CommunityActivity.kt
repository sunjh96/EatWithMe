
package com.example.eatwithme

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var writingarr = ArrayList<MyItem>()

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community)
        //테스트
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hihi", "gg", "2021-09-09", "09:10"))
        writingarr.add(MyItem("hi", "hh", "2021-09-09", "09:09"))


        viewManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter(writingarr, R.id.tvComm_title, R.id.tvComm_name, R.id.tvComm_date, R.id.tvComm_time)
        recyclerViewAdapter.mListener = object : RecyclerViewAdapter.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val writng = writingarr[position]
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.Comm_recyclerView).apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }

        //recyclerViewAdapter.notifyDataSetChanged()
    }
}
