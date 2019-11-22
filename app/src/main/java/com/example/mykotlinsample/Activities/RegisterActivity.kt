package com.example.mykotlinsample.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.example.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        supportActionBar?.hide()

        val usernameView = findViewById<EditText>(R.id.username)
        val passwordView = findViewById<EditText>(R.id.password)
        val emailaddView = findViewById<EditText>(R.id.email)
        val mobView   = findViewById<EditText>(R.id.mobile)
        val register_btn = findViewById<Button>(R.id.register_btn)
        val back_arrow = findViewById<ImageView>(R.id.back_arrow)

        val login_view = findViewById<TextView>(R.id.login_view);
        val forgot_view = findViewById<TextView>(R.id.forgot_view);

        this.hideKeyboard(usernameView)

        back_arrow.setOnClickListener () {
            onBackPressed()
                }
        login_view.setOnClickListener() {
            intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
            );
                }

        forgot_view.setOnClickListener() {
            intent = Intent(applicationContext, ForgotActivity::class.java)
            startActivity(intent)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
            );
                }

        register_btn.setOnClickListener() {

            val userView: String = usernameView.text.toString()
            val passView: String = passwordView.text.toString()
            val emailView: String = emailaddView.text.toString()
            val mobileView: String = mobView.text.toString()

            if(emailView == null || emailView.trim() == "") {
                Snackbar.make(it,"Please enter Email", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                        }

            if(!isEmailValid(emailView)) {
                Snackbar.make(it,"Please enter valid email", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                      }

            if(passView == null || passView.trim() == "") {
                Snackbar.make(it,"Please enter Password", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                        }

            if(userView == null || userView.trim() == "") {
                Snackbar.make(it,"Please enter Username", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                        }

            if(mobileView == null || mobileView.trim() == "") {
                Snackbar.make(it,"Please enter mobile", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                        }

            if(mobileView.length == 10) {
                Snackbar.make(it,"Please enter valid mobile number", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                        }

            Log.e("sampleapp", "Username: " + userView)
            Log.e("sampleapp", "Password: " + passView)
            Log.e("sampleapp", "Email: " + emailView)
            Log.e("sampleapp", "Mobile: " + mobileView)

                    }
            }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }

    fun Activity.hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
    }


