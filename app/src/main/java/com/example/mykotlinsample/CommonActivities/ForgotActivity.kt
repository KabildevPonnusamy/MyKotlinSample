package com.example.mykotlinsample.CommonActivities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.CommonActivities.Models.ProfileDatas
import com.example.mykotlinsample.R
import com.example.mykotlinsample.SupportClasses.SupportFunctions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.forgot.*
import kotlin.collections.ArrayList

class ForgotActivity : AppCompatActivity () {

    internal lateinit var db: DBHelper
    internal var profile_datas:List<ProfileDatas> = ArrayList<ProfileDatas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot)
        supportActionBar ?.hide()

        db = DBHelper(this)

        val forgotEmail = findViewById<EditText>(R.id.forgotEmail)
        val send_btn = findViewById<Button>(R.id.send_btn)
        val back_arrow = findViewById<ImageView>(R.id.back_arrow)

        this.hideKeyboard(forgotEmail)

        back_arrow.setOnClickListener () {
           onBackPressed()
                    }

        send_btn.setOnClickListener () {

            this.hideKeyboard(forgotEmail)

            var mailstr: String = forgotEmail.text.toString()
            if(mailstr == null || mailstr.trim() == "") {
                Snackbar.make(it, "Please enter your email", Snackbar.LENGTH_SHORT).show()
                forgotEmail.requestFocus()
                return@setOnClickListener
                        }

            if(!isEmailValid(mailstr)) {
                Snackbar.make(it,"Please enter valid email", Snackbar.LENGTH_LONG).show()
                forgotEmail.requestFocus()
                return@setOnClickListener
                        }

            profile_datas = db.searchBEmail(mailstr)
            if(profile_datas.size > 0) {

                val rNum = SupportFunctions()
                var valueTwo = rNum.getRandomNumber()

                db.updateUser(mailstr, "" + valueTwo)
                db.close()

                var userName: String? = ""

                for (obj in profile_datas) {
                    userName = obj.name
                        }

                showUpdatedAlert(valueTwo, userName)

                        } else {
                Snackbar.make(it,"Email id not registered", Snackbar.LENGTH_LONG).show()
                return@setOnClickListener
                        }
                    }
                }

    private fun showUpdatedAlert(valueTwo: Int, name: String?) { // 659688
        forgotEmail.setText("")

        forgotEmail.requestFocus()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Welcome $name")
        builder.setMessage("Please use $valueTwo for temperuary password")

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