package com.example.mykotlinsample.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.R
import kotlinx.android.synthetic.main.register.view.*

class ForgotActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot)
        supportActionBar ?.hide()

        val forgotEmail = findViewById<EditText>(R.id.forgotEmail)
        val send_btn = findViewById<Button>(R.id.send_btn)
        val back_arrow = findViewById<ImageView>(R.id.back_arrow)

        back_arrow.setOnClickListener () {
           onBackPressed()
                    }

        send_btn.setOnClickListener () {

                    }

            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
        }