package com.exampleboard

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class AddPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addpost)

        var uploadBtn : Button = findViewById<Button>(R.id.uploadBtn)

        uploadBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                upload()
                finish()
            }
        })
    }

    fun upload() {
        var db : FirebaseFirestore = FirebaseFirestore.getInstance()

        val title : String = findViewById<EditText>(R.id.title).getText().toString()
        val address : String = findViewById<EditText>(R.id.address).getText().toString()
        val content : String = findViewById<EditText>(R.id.content).getText().toString()

        var addpost : AddPost = AddPost(title, address, content)
        db.collection("posts").add(addpost).addOnSuccessListener(object :
            OnSuccessListener<DocumentReference> {
                override fun onSuccess(documentReference : DocumentReference) {
                    Log.d("AddPostActivity", "DocumentSnapshot written with ID: " + documentReference.getId())
                }
            }).addOnFailureListener(object : OnFailureListener {
                override fun onFailure(e : Exception) {
                    Log.w("AddPostActivity", "error writing document", e)
                }
            })
    }
}