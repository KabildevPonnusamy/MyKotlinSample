package com.example.mykotlinsample.Admin.AdminActivities

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.admin_addcategory.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

class Admin_Add_Category : AppCompatActivity() {


    internal lateinit var db: DBHelper
    private var mediaPath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_addcategory)
        supportActionBar ?.hide()

        db = DBHelper(this)

        val addcate_back: ImageView = findViewById<ImageView>(R.id.addcate_back)
        addcate_back.setOnClickListener {
            onBackPressed()
                    }

        val added_image: ImageView = findViewById<ImageView>(R.id.added_image)
        val add_image : ImageView = findViewById<ImageView>(R.id.add_image)
        val catename:EditText = findViewById<EditText>(R.id.catename)
        val create_cate_btn: Button = findViewById<Button>(R.id.create_cate_btn)
        added_image.setOnClickListener { this }
        add_image.setOnClickListener {
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
        create_cate_btn.setOnClickListener{
            var vatenameStr: String = catename.text.trim().toString()
            if(mediaPath!!.trim() == "") {
                Snackbar.make(it, "Please select an image", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
                    }

            if(vatenameStr == null || vatenameStr.trim() == "") {
                Snackbar.make(it, "Please enter Category name", Snackbar.LENGTH_LONG)
                    .show()
                return@setOnClickListener
                    }

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            db.addCategory(vatenameStr, mediaPath!!, "1", currentDate)
            db.close()

            Log.e("sample", "added: " + mediaPath);

            setResult(1)
            finish()
                }
             }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
        private val IMAGE_DIRECTORY = "/demonuts"
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
              }

    private fun pickImageFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

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
/*
    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.e("sample",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
        }

        try
        {
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()

            val bitmap:Bitmap = BitmapFactory.decodeFile(f.absolutePath)
            added_image!!.setImageBitmap(bitmap)
            str_filepath = f.absolutePath
            Log.e("sample", "Path: " + str_filepath)

            return f.getAbsolutePath()
                }
        catch (e1: IOException) {
            e1.printStackTrace()
                }

        return ""
           }
*/

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

                /*try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                            } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
                            }*/
                        }
                    }
                }
}