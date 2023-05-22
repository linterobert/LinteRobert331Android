package com.example.linterobert331

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
        }

        var profileButton = findViewById<Button>(R.id.button2)
        profileButton.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        var mapButton = findViewById<Button>(R.id.button5)
        mapButton.setOnClickListener{
            startActivity(Intent(this, ClientMapActivity::class.java))
        }
        var workoutsActivity = findViewById<Button>(R.id.button6)
        workoutsActivity.setOnClickListener{
            startActivity(Intent(this, WorkoutsActivity::class.java))
        }
        var logout = findViewById<TextView>(R.id.textView5)
        logout.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}