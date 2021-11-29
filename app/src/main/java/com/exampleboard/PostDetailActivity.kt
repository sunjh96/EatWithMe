package com.exampleboard

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.io.Serializable

class PostDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postdetail)

        var posttitle : String? = getIntent().getExtras()?.getString("posttitle")
        var textViewtitle : TextView = findViewById(R.id.textViewtitle)
        textViewtitle.setText(posttitle)
        var postaddress : String? = getIntent().getExtras()?.getString("postaddress")
        var textViewaddress : TextView = findViewById(R.id.textViewaddress)
        textViewaddress.setText(postaddress)
        var postcontent : String? = getIntent().getExtras()?.getString("postcontent")
        var textViewcontent : TextView = findViewById(R.id.textViewcontent)
        textViewcontent.setText(postcontent)

        var chat_btn : Button = findViewById(R.id.chat_btn)
        var profile_btn : Button = findViewById(R.id.profile_btn)

    }
 }