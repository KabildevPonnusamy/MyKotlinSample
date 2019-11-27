package com.example.mykotlinsample.Admin.AdminActivities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.R

class Admin_Add_Category : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_addcategory)
        supportActionBar ?.hide()

        val addcate_back: ImageView = findViewById<ImageView>(R.id.addcate_back)
        addcate_back.setOnClickListener {
            onBackPressed()
                    }
             }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
        }