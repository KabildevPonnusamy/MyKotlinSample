package com.example.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.admin_addcategory.*
import java.text.SimpleDateFormat
import java.util.*

class Admin_Add_Items : AppCompatActivity() {

    internal lateinit var db: DBHelper
    private var mediaPath: String = ""

    var cateid: Int? = null
    var str_Iname: String? = null
    var str_Pdollor: String? = null
    var str_Pcent: String? = null
    var str_Odollor:String? = null
    var str_Ocent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_additems)
        supportActionBar ?.hide()

        db = DBHelper(this)

        var additem_back: ImageView = findViewById(R.id.additem_back)
        var added_image: ImageView = findViewById(R.id.added_image)
        var add_image: ImageView = findViewById(R.id.add_image)
        var itemname: EditText = findViewById(R.id.itemname)
        var price_dollor : EditText = findViewById(R.id.price_dollor)
        var price_cents: EditText = findViewById(R.id.price_cents)
        var offer_dollor: EditText = findViewById(R.id.offer_dollor)
        var offer_cents: EditText = findViewById(R.id.offer_cents)

        var create_item_btn: Button = findViewById(R.id.create_item_btn)

        additem_back.setOnClickListener{
            onBackPressed()
                }

        getIntents()

        added_image.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED) {
                    // Permission Denied
                    val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                      requestPermissions(permission, PERMISSION_CODE)
                        } else {
                    //Permission already denied
                    pickImageFromGallery()
                            }
                        } else {
                    //Lower version
                    pickImageFromGallery()
                            }
                        }

        create_item_btn.setOnClickListener {
            str_Iname = itemname.text.trim().toString()
            str_Pdollor = price_dollor.text.trim().toString()
            str_Pcent = price_cents.text.trim().toString()
            str_Odollor = offer_dollor.text.trim().toString()
            str_Ocent = offer_cents.text.trim().toString()

            if(mediaPath.trim() == "") {
                Snackbar.make(it, "Please select an image", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
                        }

            if(str_Iname == null || str_Iname.equals("") ) {
                itemname.requestFocus()
                Snackbar.make(it, resources.getString(R.string.enter_item_name), Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
                    }

            if(str_Pdollor == null || str_Pdollor!!.trim() == "") {
                price_dollor.requestFocus()
                Snackbar.make(it, resources.getString(R.string.enter_price_dollor), Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
                    }

            if((str_Pdollor + "." + str_Pcent).equals("00.00")) {
                price_dollor.requestFocus()
                Snackbar.make(it, resources.getString(R.string.enter_price_valid_dollor), Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
                    }

            if(str_Pdollor.toString().toInt() > 0) {
                if(str_Pdollor.toString().length == 1) {
                    str_Pdollor = "0" + str_Pdollor
                        }
                    }

            if(str_Pcent == null || str_Pcent!!.trim() == "") {
                price_cents.requestFocus()
                Snackbar.make(it, resources.getString(R.string.enter_price_cents), Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
                    }

            if(str_Odollor == null || str_Odollor!!.trim() == "") {
                str_Odollor = "00"
                    }

            if(str_Ocent == null || str_Ocent!!.trim() == "") {
                str_Ocent = "00"
                    }

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            db.addItems("" + cateid, str_Iname!!, mediaPath, str_Pdollor + "." + str_Pcent ,
                str_Odollor + "." + str_Ocent, "1", currentDate)
            db.close()

            setResult(4)
            finish()
                }
            }

    private fun getIntents() {
        cateid = intent.getIntExtra("cateid", 0)
                }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_PICK_CODE)
                }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        when(requestCode) {
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from pop up denied
                    Snackbar.make(add_image, "Permission Denied", Snackbar.LENGTH_LONG)
                        .show()
                        }
                    }
                }
            }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_PICK_CODE)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(contentURI!!, filePathColumn, null,
                    null, null)
                assert(cursor != null)
                cursor!!.moveToFirst()

                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                mediaPath = cursor.getString(columnIndex)

                added_image.setImageBitmap(BitmapFactory.decodeFile(mediaPath))
                cursor.close()
                Log.e("sample", "Path: " + mediaPath)
                    }
                }
            }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
                }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
                }
    }