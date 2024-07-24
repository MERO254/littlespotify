package com.example.littlespotify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class acountActivity : AppCompatActivity() {

    private var filepath:Uri? = null
    private lateinit var dbstorage:FirebaseStorage
    private lateinit var dbfirebase:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acount)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var floatbutton:FloatingActionButton = findViewById(R.id.floatingActionButton)
        var imagebutton: ImageButton = findViewById(R.id.imagebutton)

        imagebutton.setImageResource(R.drawable.play)

        floatbutton.setOnClickListener{
            chooseAudio()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            filepath = data.data
            uploadAudio(filepath!!)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun chooseAudio(){
        var intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select audio"), 1)

    }
    private fun uploadAudio(file:Uri){
        dbfirebase = FirebaseFirestore.getInstance()
        dbstorage = FirebaseStorage.getInstance()
        dbstorage.getReference().child("audio/${file.lastPathSegment}").putFile(file).addOnSuccessListener {
            dbstorage.getReference().child("audio/${file.lastPathSegment}").downloadUrl
                .addOnCompleteListener{
                    var value = it.result
                    var hash = hashMapOf( "audio" to value)
                    dbfirebase.collection("audio").add(hash).addOnSuccessListener {
                        Toast.makeText(this, "audio uploaded", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this,"audio not uploaded",Toast.LENGTH_SHORT).show()
                }

        }.addOnFailureListener {
            Toast.makeText(this, "no audio selected", Toast.LENGTH_SHORT).show()
        }
    }
}