package com.example.eatwithme

import android.bluetooth.BluetoothDevice
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
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
        recyclerViewAdapter = RecyclerViewAdapter(writingarr)
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

    class RecyclerViewAdapter(private val myDataset: ArrayList<MyItem>) :
        RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
        var mListener : OnItemClickListener? = null

        interface OnItemClickListener{
            fun onClick(view: View, position: Int)
        }

        class MyViewHolder(val linearView: LinearLayout) : RecyclerView.ViewHolder(linearView)

        override fun getItemCount(): Int {
            return myDataset.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val itemTitle : TextView = holder.linearView.findViewById(R.id.tvComm_title)
            itemTitle.text = myDataset[position].title

            val itemName : TextView = holder.linearView.findViewById(R.id.tvComm_name)
            itemName.text = myDataset[position].name

            val itemDate : TextView = holder.linearView.findViewById(R.id.tvComm_date)
            itemDate.text = myDataset[position].date

            val itemTime : TextView = holder.linearView.findViewById(R.id.tvComm_time)
            itemTime.text = myDataset[position].time

            if(mListener!=null){
                holder?.itemView?.setOnClickListener{v->
                    mListener?.onClick(v, position)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val linearView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false) as LinearLayout

            return MyViewHolder(linearView)
        }
    }

}

class MyItem(title : String, name : String, date : String, time : String){
    lateinit var title : String
    lateinit var name : String
    lateinit var date : String
    lateinit var time : String

    init{
        this.title = title
        this.name = name
        this.date = date
        this.time = time
    }

    //하단은 글쓰는 파일에서
/*  
    @RequiresApi(Build.VERSION_CODES.O)
    fun date_to_stirng(){
        // 현재시간 가져오기
        val long_now = System.currentTimeMillis()

        // 현재 시간을 Date 타입으로 변환
        val t_date = Date(long_now)

        // 날짜, 시간을 가져오고 싶은 형태 선언
        val d_dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ko", "KR"))
        val t_dateFormat = SimpleDateFormat("kk:mm", Locale("ko", "KR"))

        // 현재 시간을 dateFormat 에 선언한 형태의 String 으로 변환
        date = d_dateFormat.format(t_date)
        time = t_dateFormat.format(t_date)

    }*/
}