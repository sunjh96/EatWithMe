package com.cookandroid.eatwithme

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.user.UserApiClient

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

        var isBool : Boolean

        cardView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View) {
                UserApiClient.instance.me { user, error ->
                    if (user?.kakaoAccount?.profile?.nickname.toString() == mDataSet.get(postViewHolder.adapterPosition).getKakao_name())
                        isBool = true
                     else
                         isBool = false

                    var intent = Intent(activity, PostDetailActivity::class.java)
                    intent.putExtra("posttitle", mDataSet.get(postViewHolder.adapterPosition).getTitle())
                    intent.putExtra("postaddress", mDataSet.get(postViewHolder.adapterPosition).getAddress())
                    intent.putExtra("postcontent", mDataSet.get(postViewHolder.adapterPosition).getContents())
                    intent.putExtra("kakao_name", mDataSet.get(postViewHolder.adapterPosition).getKakao_name())
                    intent.putExtra("createdAt", mDataSet.get(postViewHolder.adapterPosition).getCreatedAt())
                    intent.putExtra("isBool",isBool)
                    activity.startActivity(intent)
                }
            }
        })
        return postViewHolder
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var cardView : CardView = holder.cardView
        var textViewtitle : TextView = cardView.findViewById(R.id.tvComm_title)
        textViewtitle.setText(mDataSet.get(position).getTitle())
        var textView_date : TextView = cardView.findViewById(R.id.tvComm_date)
        textView_date.setText(mDataSet.get(position).getCreatedAt())
        var textView_name : TextView = cardView.findViewById(R.id.tvComm_name)
        textView_name.setText(mDataSet.get(position).getKakao_name())
    }

    override fun getItemCount() : Int {
        return mDataSet.size
    }
}