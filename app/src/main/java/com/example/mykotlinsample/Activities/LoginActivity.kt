package com.example.mykotlinsample.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.R

class LoginActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar ?.hide()

        val emailView = findViewById<EditText>(R.id.emailView)
        val passwordView = findViewById<EditText>(R.id.passwordView)
        val back_arrow = findViewById<ImageView>(R.id.back_arrow)
        val login_btn = findViewById<Button>(R.id.login_btn)

        login_btn.setOnClickListener() {

                }
        back_arrow.setOnClickListener() {
            onBackPressed()
                }

            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
}