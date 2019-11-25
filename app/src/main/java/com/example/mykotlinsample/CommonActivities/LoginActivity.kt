package com.example.mykotlinsample.CommonActivities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.Admin.Activities.Dashboard
import com.example.mykotlinsample.Users.Activities.DashboardActivity
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.CommonActivities.Models.ProfileDatas
import com.example.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper
    internal var profile_datas:List<ProfileDatas> = ArrayList<ProfileDatas>()
    private val sharedPrefFile = "coffee_preference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        supportActionBar ?.hide()

        db = DBHelper(this)

        view_init()

        var sharedPref: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharedPref.edit()

        login_btn.setOnClickListener() {

            this.hideKeyboard(emailView)

            val emailstr = emailView.text.toString()
            val passstr = passwordView.text.toString()

            if(emailstr == null || emailstr.trim() == "") {
                Snackbar.make(it,"Please enter Email", Snackbar.LENGTH_LONG).show()
                emailView.requestFocus()
                return@setOnClickListener
                        }

            if(!isEmailValid(emailstr)) {
                Snackbar.make(it,"Please enter valid email", Snackbar.LENGTH_LONG).show()
                emailView.requestFocus()
                return@setOnClickListener
                        }

            if(passstr == null || passstr.trim() == "") {
                Snackbar.make(it,"Please enter Password", Snackbar.LENGTH_LONG).show()
                passwordView.requestFocus()
                return@setOnClickListener
                        }
            if (emailstr.equals("admin@admin.com") && passstr.equals("admin")) {

                editor.putString("user_email", emailstr)
                editor.putString("user_pass", passstr)
                editor.apply()
                editor.commit()

                intent = Intent(this, Dashboard::class.java)
                startActivity(intent)

                    } else {

                profile_datas = db.getMyUser(emailstr, passstr)
                db.close()

                if(profile_datas.size > 0) {
                    editor.putString("user_email", emailstr)
                    editor.putString("user_pass", passstr)
                    editor.apply()
                    editor.commit()

                    showSuccessAlert()
                } else {
                    Snackbar.make(it, "Please check your login credentials", Snackbar.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                           }
                        }
                  }
            }

    private fun view_init() {
        val emailView = findViewById<EditText>(R.id.emailView)
        val passwordView = findViewById<EditText>(R.id.passwordView)
        val back_arrow = findViewById<ImageView>(R.id.login_back)
        val login_btn = findViewById<Button>(R.id.login_btn)

        this.hideKeyboard(emailView)
        back_arrow.setOnClickListener() {
            onBackPressed()
                }
            }

    private fun showSuccessAlert() {
        emailView.setText("")
        passwordView.setText("")
        emailView.requestFocus()

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.login_success)
        builder.setMessage(R.string.welcome_login_msg)

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