package com.example.mykotlinsample.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mykotlinsample.Admin.AdminModels.CategoryList
import com.example.mykotlinsample.Common.CommonModels.ProfileDatas

class DBHelper(context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "BAKERY.db"

        //Profile Tables
        private val PROFILE_TABLE = "Profile"
        private val COL_ID = "id"
        private val COL_EMAIL = "email"
        private val COL_PASS = "password"
        private val COL_NAME = "name"
        private val COL_MOBILE = "mobile"
//        private val COL_CREATED_DATE = "created_date"

        //Category Table
        private val CATEGORY_TABLE = "Category"
        private val CATE_ID = "cate_id"
        private val CATE_NAME = "cate_name"
        private val CATE_IMAGE = "cate_img"
        private val CATE_SHOW_STATUS = "cate_show_status"
        private val CATE_CREATED_DATE = "cate_created_date"

            }

    val CREATE_PROFILE_TABLE = ("CREATE TABLE $PROFILE_TABLE ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COL_EMAIL TEXT, $COL_PASS TEXT, $COL_NAME TEXT, $COL_MOBILE TEXT)")

    val CREATE_CATEGORY_TABLE = ("CREATE TABLE $CATEGORY_TABLE ($CATE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$CATE_NAME TEXT, $CATE_IMAGE TEXT, $CATE_SHOW_STATUS TEXT, $CATE_CREATED_DATE TEXT) ")

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_PROFILE_TABLE)
        db!!.execSQL(CREATE_CATEGORY_TABLE)
                }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $PROFILE_TABLE")
        db!!.execSQL("DROP TABLE IF EXISTS $CATEGORY_TABLE")
                }

    // Get user By Username and Passsword
    fun getMyUser(username: String, password: String): List<ProfileDatas> {
        val myprof = ArrayList<ProfileDatas>()
        val selectQuery = "SELECT * FROM $PROFILE_TABLE where $COL_EMAIL = '$username' and " +
                "$COL_PASS = '$password'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()) {
            do {
                val mydatas = ProfileDatas()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                mydatas.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                mydatas.password = cursor.getString(cursor.getColumnIndex(COL_PASS))
                mydatas.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                mydatas.mobile = cursor.getString(cursor.getColumnIndex(COL_MOBILE))
//                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))

                myprof.add(mydatas)
             } while (cursor.moveToNext())
          }

        return myprof
          }

    //Search user by Email
    fun searchBEmail(username: String): List<ProfileDatas> {
        val myprof = ArrayList<ProfileDatas>()
        val selectQuery = "SELECT * FROM $PROFILE_TABLE where $COL_EMAIL = '$username' "
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()) {
            do {
                val mydatas = ProfileDatas()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                mydatas.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                mydatas.password = cursor.getString(cursor.getColumnIndex(COL_PASS))
                mydatas.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                mydatas.mobile = cursor.getString(cursor.getColumnIndex(COL_MOBILE))
//                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))

                myprof.add(mydatas)
                } while (cursor.moveToNext())
            }
        return myprof
        }

    //Get All Category
    fun getCategories():List<CategoryList> {
        val mycates = ArrayList<CategoryList>()
        val selectQuery = "SELECT * FROM $CATEGORY_TABLE where $CATE_SHOW_STATUS = '1'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()) {
            do {
                val mydatas = CategoryList()
                mydatas.cate_id = cursor.getInt(cursor.getColumnIndex(CATE_ID))
                mydatas.cate_name = cursor.getString(cursor.getColumnIndex(CATE_NAME))
                mydatas.cate_img = cursor.getString(cursor.getColumnIndex(CATE_IMAGE))
                mydatas.cate_show_status = cursor.getString(cursor.getColumnIndex(CATE_SHOW_STATUS))
                mydatas.cate_created = cursor.getString(cursor.getColumnIndex(CATE_CREATED_DATE))
                mycates.add(mydatas)
                 } while (cursor.moveToNext())
              }
        return mycates
            }

    //Get particular category details
    fun getthisCategories(cate_id: String):List<CategoryList> {
        val mycates = ArrayList<CategoryList>()
        val selectQuery = "SELECT * FROM $CATEGORY_TABLE where $CATE_SHOW_STATUS = '1' and $CATE_ID = $cate_id"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()) {
            do {
                val mydatas = CategoryList()
                mydatas.cate_id = cursor.getInt(cursor.getColumnIndex(CATE_ID))
                mydatas.cate_name = cursor.getString(cursor.getColumnIndex(CATE_NAME))
                mydatas.cate_img = cursor.getString(cursor.getColumnIndex(CATE_IMAGE))
                mydatas.cate_show_status = cursor.getString(cursor.getColumnIndex(CATE_SHOW_STATUS))
                mydatas.cate_created = cursor.getString(cursor.getColumnIndex(CATE_CREATED_DATE))
                mycates.add(mydatas)
            } while (cursor.moveToNext())
        }
        return mycates
    }

    //Get All Users
    val getUser: List<ProfileDatas> get() {
        val myprof = ArrayList<ProfileDatas>()
        val selectQuery = "SELECT * FROM $PROFILE_TABLE"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()) {
            do {
                val mydatas = ProfileDatas()
                mydatas.id = cursor.getInt(cursor.getColumnIndex(COL_ID))
                mydatas.email = cursor.getString(cursor.getColumnIndex(COL_EMAIL))
                mydatas.password = cursor.getString(cursor.getColumnIndex(COL_PASS))
                mydatas.name = cursor.getString(cursor.getColumnIndex(COL_NAME))
                mydatas.mobile = cursor.getString(cursor.getColumnIndex(COL_MOBILE))
//                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))
                myprof.add(mydatas)
                    } while (cursor.moveToNext())
                }
           return myprof
            }

    // Add Users
    fun addUser(email: String, password: String, name: String, mobile: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, email)
        values.put(COL_PASS, password)
        values.put(COL_NAME, name)
        values.put(COL_MOBILE, mobile)
//        values.put(COL_CREATED_DATE, profdatas.created_date)

        db.insert(PROFILE_TABLE, null,values)
        db.close()
            }

    // Update Password
    fun updateUser(email: String, password: String):Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_PASS, password)

        return db.update(PROFILE_TABLE, values, "$COL_EMAIL=?", arrayOf(email))
            }

    // Delete User
    fun deleteUser(profdatas : ProfileDatas) {
        val db = this.writableDatabase
        db.delete(PROFILE_TABLE, "$COL_EMAIL=?", arrayOf(profdatas.email.toString()))
        db.close()
            }

         /* Category Tables */
    //Add Category
    fun addCategory(catename: String, cateimg: String, cateshown: String, catecreated: String) {
       val db = this.writableDatabase
        val values = ContentValues()
        values.put(CATE_NAME, catename)
        values.put(CATE_IMAGE, cateimg)
        values.put(CATE_SHOW_STATUS, cateshown)
        values.put(CATE_CREATED_DATE, catecreated)
        db.insert(CATEGORY_TABLE, null, values)
        db.close()
            }

    //Delete Category
    fun deleteCategory (cate_id: String) {
        val db = this.writableDatabase
        db.delete (CATEGORY_TABLE, "$CATE_ID=?", arrayOf(cate_id))
        db.close()
            }

    //Update Category
    fun updateCategory (cate_name: String, cate_img: String, cate_status:String):Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CATE_NAME, cate_name)
        values.put(CATE_IMAGE, cate_img)
        values.put(CATE_SHOW_STATUS, cate_status)
        return db.update(CATEGORY_TABLE, values, "$CATE_NAME=? and $CATE_IMAGE=? and " +
                "$CATE_SHOW_STATUS=?", arrayOf(cate_name, cate_img, cate_status))
            }





}