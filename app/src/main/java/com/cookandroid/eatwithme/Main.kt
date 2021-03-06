package com.cookandroid.eatwithme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient

class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnlogout = findViewById<Button>(R.id.btnLogout) // 로그아웃 버튼

        btnlogout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(this, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



        val btninfo = findViewById<Button>(R.id.btnMyInfo) // 내 정보 버튼

        btninfo.setOnClickListener {
            val intent = Intent(this, Information::class.java)
            startActivity(intent)
        }

        val btnmap = findViewById<ImageButton>(R.id.ivMain_maps)

        btnmap.setOnClickListener{
            val intent = Intent(this, Map::class.java)
            startActivity(intent)
        }

        val btncom = findViewById<ImageButton>(R.id.ivMain_comm)

        btncom.setOnClickListener{
            val intent = Intent(this, Board::class.java)
            startActivity(intent)
        }
    }
}