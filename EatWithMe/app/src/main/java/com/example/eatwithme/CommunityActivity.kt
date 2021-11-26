
package com.example.eatwithme

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
    private var writingarr = ArrayList<MyItem_comm>()

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter_comm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community)
        //테스트
        writingarr.add(MyItem_comm("hihffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", "gg", "2021-09-09", "09:10", "거래 전"))
        writingarr.add(MyItem_comm("hihi", "gg", "2021-09-09", "09:10", "거래 완료"))
        writingarr.add(MyItem_comm("hihi", "gg", "2021-09-09", "09:10", "거래 완료"))
        writingarr.add(MyItem_comm("hihi", "gg", "2021-09-09", "09:10", "거래 완료"))
        writingarr.add(MyItem_comm("hihi", "gg", "2021-09-09", "09:10", "거래 완료"))


        viewManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter_comm(writingarr, R.layout.comm_recyclerview_item,R.id.tvComm_title, R.id.tvComm_name, R.id.tvComm_date, R.id.tvComm_time, R.id.tvComm_complete)
        recyclerViewAdapter.mListener = object : RecyclerViewAdapter_comm.OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                val writing = writingarr[position]
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.Comm_recyclerView).apply {
            layoutManager = viewManager
            adapter = recyclerViewAdapter
        }

        //recyclerViewAdapter.notifyDataSetChanged()
    }
}
