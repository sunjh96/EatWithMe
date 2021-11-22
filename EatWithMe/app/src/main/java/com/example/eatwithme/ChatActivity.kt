package com.example.eatwithme

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var baeminButton : Button
    lateinit var yogiyoButton : Button
    lateinit var coupangEatsButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat)

        baeminButton = findViewById<Button>(R.id.baeminButton)
        yogiyoButton = findViewById<Button>(R.id.yogiyoButton)
        coupangEatsButton = findViewById<Button>(R.id.coupangEatsButton)

        val baeminPackage = "com.sampleapp" //배민 패키지 주소
        val intentBaemin = packageManager.getLaunchIntentForPackage(baeminPackage)
        val yogiyoPackage = "com.fineapp.yogiyo" //요기요 패키지 주소
        val intentYogiyo = packageManager.getLaunchIntentForPackage(yogiyoPackage)
        val coupangEatsPackage = "com.coupang.mobile.eats" //쿠팡이츠 패키지 주소
        val intentCoupangEats = packageManager.getLaunchIntentForPackage(coupangEatsPackage)

        baeminButton.setOnClickListener {
            try {
                startActivity(intentBaemin)
            } catch (e: Exception) {
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + baeminPackage))
                startActivity(intentPlayStore)
            }
        }

        yogiyoButton.setOnClickListener {
            try {
                startActivity(intentYogiyo)
            } catch (e: Exception) {
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + yogiyoPackage))
                startActivity(intentPlayStore)
            }
        }

        coupangEatsButton.setOnClickListener {
            try {
                startActivity(intentCoupangEats)
            } catch (e: Exception) {
                val intentPlayStore = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + coupangEatsPackage))
                startActivity(intentPlayStore)
            }
        }
    }
}

