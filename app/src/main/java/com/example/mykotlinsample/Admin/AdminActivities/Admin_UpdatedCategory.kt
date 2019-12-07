package com.example.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.admin_addcategory.*
import kotlinx.android.synthetic.main.admin_updcategory.*

class Admin_UpdatedCategory : AppCompatActivity(), View.OnClickListener {

    internal lateinit var db: DBHelper

    var cateid : Int = 0
    var catename: String? = null
    var catestatus: String? = null
    var cateimg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_updcategory)
        supportActionBar ?.hide()

        db = DBHelper(this)

        init_views()
        get_intents()
            }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
                }

    private fun get_intents() {
        cateid = intent.getIntExtra("cateid", 0)
        catename = intent.getStringExtra("catename")
        catestatus = intent.getStringExtra("catestatus")
        cateimg = intent.getStringExtra("cateimg")
        upd_catename.setText(catename)
        if(catestatus.equals("0")) {
            cate_status.isChecked = false
                    } else {
            cate_status.isChecked = true
                }

        val bitmap: Bitmap = BitmapFactory.decodeFile(cateimg)
        updated_image.setImageBitmap(bitmap)
        cate_status.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
                buttonView, isChecked ->
            if(isChecked) {
                catestatus = "1"
                     } else {
                catestatus = "0"
                      }
                 })
            }

    private fun init_views() {
        var updcate_back: ImageView = findViewById(R.id.updcate_back)
        var updated_image: ImageView = findViewById(R.id.updated_image)
        var upd_add_image: ImageView = findViewById(R.id.upd_add_image)
        var upd_catename: EditText = findViewById(R.id.upd_catename)
        var cate_status: ToggleButton = findViewById(R.id.cate_status)
        var update_cate_btn:Button = findViewById(R.id.update_cate_btn)
        updcate_back.setOnClickListener(this)
        upd_add_image.setOnClickListener(this)
        update_cate_btn.setOnClickListener(this)
            }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.updcate_back -> onBackPressed()
            R.id.upd_add_image -> get_image()
            R.id.update_cate_btn -> cate_validation(v)
                }
            }

    private fun cate_validation(v:View) {
        catename = upd_catename.text.trim().toString()
        if(catename == null || catename!!.trim() == "") {
            upd_catename.requestFocus()
            Snackbar.make(v, "Please enter Category name", Snackbar.LENGTH_LONG)
                .show()
            return
                }
            }

    private fun get_image() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED) {
                // Permission Denide
                val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
                    } else {
                        // Permission Already Denied
                        pickImageFromGallery()
                    }
                } else {
                    // Lower Versions
                    pickImageFromGallery()
                }
            }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
                cateimg = cursor.getString(columnIndex)

                updated_image.setImageBitmap(BitmapFactory.decodeFile(cateimg))
                cursor.close()
                Log.e("sample", "Path: " + cateimg)
                    }
                }
            }

    override fun onBackPressed() {
        setResult(10)
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
          }

}