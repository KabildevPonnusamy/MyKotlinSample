package com.example.mykotlinsample.Activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.Models.ProfileDatas
import com.example.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.register.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class RegisterActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal var profile_datas:List<ProfileDatas> = ArrayList<ProfileDatas>()
    private val sharedPrefFile = "coffee_preference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        supportActionBar?.hide()

        db = DBHelper(this)  // 668561

        val usernameView = findViewById<EditText>(R.id.username)
        val passwordView = findViewById<EditText>(R.id.password)
        val emailaddView = findViewById<EditText>(R.id.email)
        val mobView   = findViewById<EditText>(R.id.mobile)
        val register_btn = findViewById<Button>(R.id.register_btn)
        val back_arrow = findViewById<ImageView>(R.id.back_arrow)

        val login_view = findViewById<TextView>(R.id.login_view)
        val forgot_view = findViewById<TextView>(R.id.forgot_view)

        var sharedPref: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPref.edit()


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

            this.hideKeyboard(usernameView)

            val userStr: String = usernameView.text.toString()
            val passStr: String = passwordView.text.toString()
            val emailStr: String = emailaddView.text.toString()
            val mobileStr: String = mobView.text.toString()

            if(emailStr == null || emailStr.trim() == "") {
                Snackbar.make(it,"Please enter Email", Snackbar.LENGTH_LONG).show()
                email.requestFocus()
                return@setOnClickListener
                        }

            if(!isEmailValid(emailStr)) {
                Snackbar.make(it,"Please enter valid email", Snackbar.LENGTH_LONG).show()
                email.requestFocus()
                return@setOnClickListener
                      }

            if(passStr == null || passStr.trim() == "") {
                Snackbar.make(it,"Please enter Password", Snackbar.LENGTH_LONG).show()
                password.requestFocus()
                return@setOnClickListener
                        }

            if(passStr.length < 8) {
                Snackbar.make(it,"Please enter above 8 characters", Snackbar.LENGTH_LONG).show()
                password.requestFocus()
                return@setOnClickListener
                        }

            if(userStr == null || userStr.trim() == "") {
                Snackbar.make(it,"Please enter Username", Snackbar.LENGTH_LONG).show()
                username.requestFocus()
                return@setOnClickListener
                        }

            if(userStr.length < 3) {
                Snackbar.make(it,"Please enter valid name", Snackbar.LENGTH_LONG).show()
                username.requestFocus()
                return@setOnClickListener
                        }

            if(mobileStr == null || mobileStr.trim() == "") {
                Snackbar.make(it,"Please enter mobile", Snackbar.LENGTH_LONG).show()
                mobile.requestFocus()
                return@setOnClickListener
                        }

            if(mobileStr.length != 10) {
                Snackbar.make(it,"Please enter valid mobile number", Snackbar.LENGTH_LONG).show()
                mobile.requestFocus()
                return@setOnClickListener
                        }

            profile_datas = db.searchBEmail(emailStr)
            if(profile_datas.size > 0) {
                Snackbar.make(it,"Email id already registered", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                    }

            editor.putString("user_email", emailStr)
            editor.putString("user_pass", passStr)
            editor.putString("user_name", userStr)
            editor.putString("user_mobile", mobileStr)
            editor.commit()
            editor.apply()

            db.addUser(emailStr, passStr, userStr, mobileStr)
            db.close()

            showSuccessAlert()

                }
            }

    private fun showSuccessAlert() {
        username.setText("")
        password.setText("")
        email.setText("")
        mobile.setText("")
        email.requestFocus()

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.register_success)
        builder.setMessage(R.string.welcome_reg)

        builder.setPositiveButton("Ok"){dialogInterface, which ->
            dialogInterface.dismiss()
                    }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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
