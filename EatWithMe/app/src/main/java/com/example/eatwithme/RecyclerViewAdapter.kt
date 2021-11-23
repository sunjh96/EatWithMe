package com.example.eatwithme

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class MyViewHolder(val linearView: LinearLayout) : RecyclerView.ViewHolder(linearView)

class RecyclerViewAdapter(private val myDataset: ArrayList<MyItem>, private val Title : Int, private val Name : Int, private val Date : Int, private val Time : Int) :
    RecyclerView.Adapter<MyViewHolder>() {
    var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }


    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemTitle: TextView = holder.linearView.findViewById(Title)
        itemTitle.text = myDataset[position].title

        val itemName: TextView = holder.linearView.findViewById(Name)
        itemName.text = myDataset[position].name

        val itemDate: TextView = holder.linearView.findViewById(Date)
        itemDate.text = myDataset[position].date

        val itemTime: TextView = holder.linearView.findViewById(Time)
        itemTime.text = myDataset[position].time

        if (mListener != null) {
            holder?.itemView?.setOnClickListener { v ->
                mListener?.onClick(v, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val linearView = LayoutInflater.from(parent.context)
            .inflate(R.layout.comm_recyclerview_item, parent, false) as LinearLayout

        return MyViewHolder(linearView)
    }
}

class MyItem(title : String, name : String, date : String, time : String){
    var title : String = title
    var name : String = name
    var date : String = date
    var time : String = time


    //하단은 글쓰는 파일에서
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

    }
}