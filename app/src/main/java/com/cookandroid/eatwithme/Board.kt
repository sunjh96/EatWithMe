package com.cookandroid.eatwithme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kakao.sdk.user.UserApiClient
import java.time.LocalDate

class Board : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community)

        var floatingButton : FloatingActionButton = findViewById(R.id.floatingActionButton)
        var intent = Intent(applicationContext, AddPostActivity::class.java)
        var swipe_layout : SwipeRefreshLayout = findViewById(R.id.swipe_layout)
        var recyclerView : RecyclerView = findViewById(R.id.Comm_recyclerView)
        var doubleBackToExit = false
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val userRef : DatabaseReference = database.getReference("posts")
        var postList = ArrayList<AddPost>()
        var count : Int = 0
        var delete : Int = 0

        var btnComm_back : Button = findViewById(R.id.btnComm_back)
        btnComm_back.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                fun runDelayed(millis: Long, function: () -> Unit) {
                    Handler(Looper.getMainLooper()).postDelayed(function, millis)
                }

                fun onBackPressed() {
                    if (doubleBackToExit) {
                        finishAffinity()
                    } else {
                        Toast.makeText(this@Board, "종료하시려면 뒤로가기를 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                        doubleBackToExit = true
                        runDelayed(1500L) {
                            doubleBackToExit = false
                        }
                    }
                }
                onBackPressed()
            }
        })

        userRef.child("count").get()
            .addOnSuccessListener {
                count = "${it.value}".toInt()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        userRef.child("delete").get()
            .addOnSuccessListener {
                delete = "${it.value}".toInt()
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        UserApiClient.instance.me { user, error ->
            userRef.get()
                .addOnSuccessListener {
                    for(i in (count - 1) + delete downTo 0) {
                        if ( "${it.child(i.toString()).child("kakao_name").value}" == "null" ){
                            continue
                        }

                        postList.add(
                            AddPost(
                                "${it.child(i.toString()).child("title").value}",
                                "${it.child(i.toString()).child("address").value}",
                                "${it.child(i.toString()).child("content").value}",
                                "${it.child(i.toString()).child("kakao_name").value}",
                                "${it.child(i.toString()).child("kakao_id").value}",
                                "${it.child(i.toString()).child("createdAt").value}"
                            )
                        )
                    }

                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(this@Board)
                    recyclerView.adapter = MainAdapter(this@Board, postList)
                }.addOnFailureListener {
                    Log.e("firebase", "Error getting data", it)
                }
        }

        swipe_layout.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                postList.clear()
                userRef.child("count").get()
                    .addOnSuccessListener {
                        count = "${it.value}".toInt()
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                userRef.child("delete").get()
                    .addOnSuccessListener {
                        delete = "${it.value}".toInt()
                    }.addOnFailureListener {
                        Log.e("firebase", "Error getting data", it)
                    }
                UserApiClient.instance.me { user, error ->
                    userRef.get()
                        .addOnSuccessListener {
                            for(j in ( count - 1 ) + delete downTo 0) {
                                if ( "${it.child(j.toString()).child("kakao_name").value}" == "null" ){
                                    continue
                                }

                                postList.add(
                                    AddPost(
                                        "${it.child(j.toString()).child("title").value}",
                                        "${it.child(j.toString()).child("address").value}",
                                        "${it.child(j.toString()).child("content").value}",
                                        "${it.child(j.toString()).child("kakao_name").value}",
                                        "${it.child(j.toString()).child("kakao_id").value}",
                                        "${it.child(j.toString()).child("createdAt").value}"
                                    )
                                )
                            }

                            recyclerView.setHasFixedSize(true)
                            recyclerView.layoutManager = LinearLayoutManager(this@Board)
                            recyclerView.adapter = MainAdapter(this@Board, postList)
                        }.addOnFailureListener {
                            Log.e("firebase", "Error getting data", it)
                        }
                }
                swipe_layout.isRefreshing = false
            }
        })

        floatingButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View?) {
                startActivity(intent)
            }
        })
    }
}