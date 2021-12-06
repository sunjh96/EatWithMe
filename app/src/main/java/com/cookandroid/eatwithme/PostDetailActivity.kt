package com.cookandroid.eatwithme

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient
import java.io.IOException

class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postdetail)
        var count : Int = 0
        var delete : Int = 0
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef: DatabaseReference = database.getReference("posts")
        val userRef1: DatabaseReference = database.getReference("users")
        var geocoder : Geocoder = Geocoder(this)
        var lat : Double? = null
        var lon : Double? = null
        var cal_count : Float = 0.0F
        var cal_rating : Float = 0.0F


        var posttitle: String? = getIntent().getExtras()?.getString("posttitle")
        var textViewtitle: TextView = findViewById(R.id.tvComm_title)
        textViewtitle.setText(posttitle)

        var postaddress: String? = getIntent().getExtras()?.getString("postaddress")
        var textViewaddress: TextView = findViewById(R.id.tvLetter_address)
        textViewaddress.setText(postaddress)

        var postcontent: String? = getIntent().getExtras()?.getString("postcontent")
        var textViewcontent: TextView = findViewById(R.id.tvLetter_contents)
        textViewcontent.setText(postcontent)

        var kakao_name: String? = getIntent().getExtras()?.getString("kakao_name")
        var tvLetter_writer: TextView = findViewById(R.id.tvLetter_writer)
        tvLetter_writer.setText(kakao_name)

        var textView_name: TextView = findViewById(R.id.tvComm_name)
        textView_name.setText(kakao_name)

        var tvComm_date: String? = getIntent().getExtras()?.getString("createdAt")
        var textView_date: TextView = findViewById(R.id.tvComm_date)
        textView_date.setText(tvComm_date)

        var isBool : Boolean? = getIntent().getExtras()?.getBoolean("isBool")

        var btn_chat: Button = findViewById(R.id.btnLetter_chat)
        var btn_edit: Button = findViewById(R.id.btnLetter_edit)
        var btn_delete: Button = findViewById(R.id.btnLetter_del)
        var btn_complete : Button = findViewById(R.id.btnLetter_complete)
        var rbLetter_ratingr : RatingBar = findViewById(R.id.rbLetter_ratingr)

        var intent_edit = Intent(applicationContext, EditPostActivity::class.java)
        var intent_chat = Intent(applicationContext, ChatActivity::class.java)

        if( isBool == true ){
            btn_edit.visibility = View.VISIBLE
            btn_delete.visibility = View.VISIBLE
            btn_complete.visibility = View.VISIBLE
        }
        else{
            btn_edit.visibility = View.INVISIBLE
            btn_delete.visibility = View.INVISIBLE
            btn_complete.visibility = View.INVISIBLE
        }

        userRef1.child(kakao_name.toString()).child("Count").get().addOnSuccessListener {
            cal_count = "${it.value}".toFloat()
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
        userRef1.child(kakao_name.toString()).child("Rating").get().addOnSuccessListener {
            cal_rating= "${it.value}".toFloat()
            if ( cal_count.toInt() == 0 )
                rbLetter_ratingr.setRating(0F)
            else
                rbLetter_ratingr.setRating(cal_rating / cal_count)
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }


        btn_complete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                userRef.child("count").get()
                    .addOnSuccessListener {
                        count = "${it.value}".toInt()
                        userRef.child("delete").get()
                            .addOnSuccessListener {
                                delete = "${it.value}".toInt()
                                userRef.get()
                                    .addOnSuccessListener {
                                        for (i in 0 until count) {
                                            userRef.child(i.toString()).child("content")
                                                .get().addOnSuccessListener {
                                                    if ("${it.value}" == postcontent) {
                                                        userRef.child(i.toString())
                                                            .removeValue()
                                                        userRef.child("delete")
                                                            .setValue(delete + 1)
                                                        finish()
                                                    }
                                                }
                                        }
                                    }.addOnFailureListener {
                                        Log.e("firebase", "Error getting data", it)
                                    }
                            }
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                Toast.makeText(this@PostDetailActivity, "게시글이 삭제 되었습니다.", Toast.LENGTH_SHORT).show()

            }
        })

        var btnLetter_back : Button = findViewById(R.id.btnLetter_back)
        btnLetter_back.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                onBackPressed()
            }
        })

        var btnLetter_map : Button = findViewById(R.id.btnLetter_map)
        btnLetter_map.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                var list : List<Address>? = null

                var str : String = postaddress.toString()

                try {
                    list = geocoder.getFromLocationName(str, 10)
                } catch (e : IOException) {
                    e.printStackTrace()
                    Log.e("test", "입출력 오류 - 서버에서 주소변환시 오류발생")
                }
                if (list != null) {
                    if (list!!.size == 0)
                        Toast.makeText(applicationContext, "해당 주소는 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    else {
                        var addr : Address = list!!.get(0)
                        lat = addr.latitude
                        lon = addr.longitude
                        var intent = Intent(applicationContext, MapActivity::class.java)
                        intent.putExtra("lat", lat)
                        intent.putExtra("lon", lon)
                        startActivity(intent)
                    }
                }
            }
        })

        btn_chat.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                UserApiClient.instance.me { user, error ->
                    userRef1.child(user?.kakaoAccount?.profile?.nickname.toString()).child("Name")
                        .get().addOnSuccessListener {
                            var kname = "${it.value}"

                            intent_chat.putExtra("kakao_name", kname)
                            intent_chat.putExtra("chat_name", posttitle)
                            startActivity(intent_chat)
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                }
            }
        })

        btn_edit.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                userRef.child("count").get()
                    .addOnSuccessListener {
                        count = "${it.value}".toInt()
                            userRef.get()
                                .addOnSuccessListener {
                                    for( i in 0 until count ){
                                        userRef.child(i.toString()).child("content").get().addOnSuccessListener{
                                            if("${it.value}" == postcontent){
                                                intent_edit.putExtra("parent_key",i)
                                                startActivity(intent_edit)
                                                finish()
                                            }
                                        }
                                    }
                                }.addOnFailureListener{
                                    Log.e("firebase", "Error getting data", it)
                                }
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                Toast.makeText(this@PostDetailActivity, "게시글이 수정 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        btn_delete.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {

                userRef.child("count").get()
                    .addOnSuccessListener {
                        count = "${it.value}".toInt()
                            userRef.child("delete").get()
                                .addOnSuccessListener {
                                    delete = "${it.value}".toInt()
                                        userRef.get()
                                            .addOnSuccessListener {
                                                for (i in 0 until count) {
                                                    userRef.child(i.toString()).child("content")
                                                        .get().addOnSuccessListener {
                                                            if ("${it.value}" == postcontent) {
                                                                userRef.child(i.toString())
                                                                    .removeValue()
                                                                userRef.child("delete")
                                                                    .setValue(delete + 1)
                                                                finish()
                                                            }
                                                        }
                                                }
                                            }.addOnFailureListener {
                                                Log.e("firebase", "Error getting data", it)
                                            }
                                }
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                Toast.makeText(this@PostDetailActivity, "게시글이 삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

