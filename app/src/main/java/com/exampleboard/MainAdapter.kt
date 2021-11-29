package com.exampleboard

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.exampleboard.AddPost

class MainAdapter : RecyclerView.Adapter<MainAdapter.PostViewHolder> {
    private lateinit var mDataSet : ArrayList<AddPost>
    private lateinit var activity : Activity

    class PostViewHolder : RecyclerView.ViewHolder {
        lateinit var cardView : CardView
        constructor(v : CardView) : super(v) {
            cardView = v
        }
    }


    constructor(activity : Activity, myDataSet : ArrayList<AddPost>) {
        mDataSet = myDataSet
        this.activity = activity
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MainAdapter.PostViewHolder {
        var cardView : CardView =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false) as CardView
        val postViewHolder : PostViewHolder = PostViewHolder(cardView)

        cardView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View) {
                var intent = Intent(activity, PostDetailActivity::class.java)
                intent.putExtra("posttitle", mDataSet.get(postViewHolder.adapterPosition).getTitle())
                intent.putExtra("postaddress", mDataSet.get(postViewHolder.adapterPosition).getAddress())
                intent.putExtra("postcontent", mDataSet.get(postViewHolder.adapterPosition).getContents())
                activity.startActivity(intent)
            }
        })
        return postViewHolder
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var cardView : CardView = holder.cardView
        var textViewtitle : TextView = cardView.findViewById(R.id.textViewtitle)
        textViewtitle.setText(mDataSet.get(position).getTitle())
        var textViewaddress : TextView = cardView.findViewById(R.id.textViewaddress)
        textViewaddress.setText(mDataSet.get(position).getAddress())
        var textViewcontent : TextView = cardView.findViewById(R.id.textViewcontent)
        textViewcontent.setText(mDataSet.get(position).getContents())


    }

    override fun getItemCount() : Int {
        return mDataSet.size
    }
}