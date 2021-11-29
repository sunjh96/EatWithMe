package com.exampleboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var floatingButton : FloatingActionButton = findViewById(R.id.floatingActionButton)
        var intent = Intent(applicationContext, AddPostActivity::class.java)
        var intent2 = Intent(applicationContext, PostDetailActivity::class.java)
        var db : FirebaseFirestore = FirebaseFirestore.getInstance()

        db.collection("posts").get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
            override fun onComplete(task : Task<QuerySnapshot>) {
                if (task.isSuccessful()) {
                    var postList = ArrayList<AddPost>()
                    for(document in task.getResult()!!) {
                        Log.d("MainActivity", document.getId() + " => " + document.getData())
                        postList.add(AddPost(document.getData().get("title").toString(),
                            document.getData().get("address").toString(),
                            document.getData().get("contents").toString()))
                    }
                    var recyclerView : RecyclerView = findViewById(R.id.recyclerView)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

                    recyclerView.adapter = MainAdapter(this@MainActivity, postList)

                }
                else {
                    Log.d("MainActivity","Error getting documents : ", task.getException())
                }
            }
        })

        floatingButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v : View?) {
                startActivity(intent)
            }
        })
    }
}