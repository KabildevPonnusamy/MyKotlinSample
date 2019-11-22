package com.example.mykotlinsample.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.R

class SplashActivity : AppCompatActivity() {

//    private val SPLASH_TIME_OUT:Long=3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        supportActionBar?.hide()

        val app_logo = findViewById<ImageView>(R.id.app_logo);

        /*Handler().postDelayed({
            intent = Intent(applicationContext, LoginActivity::class.java)
            finish()
        }, SPLASH_TIME_OUT)*/

        val button_click = findViewById<Button>(R.id.button_click);
        button_click.setOnClickListener() {
            intent = Intent(applicationContext, RegisterActivity::class.java);
            startActivity(intent)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    );
                }

        }
}