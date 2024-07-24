package com.example.littlespotify

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if(savedInstanceState == null){
            var bundle = Bundle()
            var fragment = MusicFragment()

            fragment.arguments = bundle

            supportFragmentManager.beginTransaction().replace(R.id.framelayout, MusicFragment()).commit()
        }

        var account = findViewById<ImageView>(R.id.imgaccount)
        account.setOnClickListener{
            var intent = Intent(this,acountActivity::class.java)
            startActivity(intent)
        }
        
        var bottomnav:BottomNavigationView = findViewById(R.id.bottomnav)

        bottomnav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.music ->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout,MusicFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.search ->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout,searchFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.podcast -> {
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout, PodcastFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.download ->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout,downloadFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else ->{
                    supportFragmentManager.beginTransaction().replace(R.id.framelayout,MusicFragment()).commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }

        }

    }





}