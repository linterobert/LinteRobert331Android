package com.example.linterobert331

import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Property
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import java.time.Duration

class MenuActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var myVideoView: VideoView? = null
    private var myMediaController: MediaController? = null
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


        myVideoView = findViewById(R.id.myVideoView);

        setUpVideoPlayer()

        val buttonAnimator = findViewById<Button>(R.id.buttonAnimator)
        val video = findViewById<VideoView>(R.id.myVideoView);

        buttonAnimator.setOnClickListener {
            val tY = ObjectAnimator.ofFloat(buttonAnimator,
            View.ALPHA, 1.0f, 0.0f)
            tY.duration = 500
            tY.interpolator = LinearInterpolator()
            tY.start()
            animate(video, View.TRANSLATION_Y, video.translationY, video.translationY + 1000f, 500, DecelerateInterpolator())
            //animate(video, View.ALPHA, 1.0f, 0.0f, 500, LinearInterpolator())
        }

    }

    private fun setUpVideoPlayer(){
        if(myMediaController == null){
            myMediaController = MediaController(this)
            myMediaController!!.setAnchorView(this.myVideoView)
        }

        myVideoView!!.setMediaController(myMediaController);

        myVideoView!!.setVideoURI(
            Uri.parse("android.resource://" + packageName + "/" + R.raw.intro)
        )

        myVideoView!!.requestFocus()
        myVideoView!!.pause()
        myVideoView!!.setOnCompletionListener {
            Toast.makeText(applicationContext, "Video completed", Toast.LENGTH_SHORT).show()
        }
    }

    fun animate(
        target: VideoView,
        property: Property<View, Float>,
        from: Float,
        to: Float,
        duration: Long,
        interpolator: TimeInterpolator
    ){
        val tY = ObjectAnimator.ofFloat(target, property, from, to)
        tY.duration = duration
        tY.interpolator = interpolator
        tY.start()
    }
}