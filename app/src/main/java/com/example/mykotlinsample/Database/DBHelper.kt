package com.example.mykotlinsample.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mykotlinsample.Models.ProfileDatas

class DBHelper(context: Context) : SQLiteOpenHelper (context, DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "BAKERY.db"

        //Tables
        private val PROFILE_TABLE = "Profile"
        private val COL_ID = "id"
        private val COL_EMAIL = "email"
        private val COL_PASS = "password"
        private val COL_NAME = "name"
        private val COL_MOBILE = "mobile"
        private val COL_CREATED_DATE = "created_date"
            }

    val CREATE_PROFILE_TABLE = ("CREATE TABLE $PROFILE_TABLE  ($COL_ID INTERGER PRIMARY KEY " +
            "AUTO_INCREMENT, $COL_EMAIL TEXT, $COL_PASS TEXT, $COL_NAME TEXT, $COL_MOBILE TEXT, " +
            "$COL_CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP");

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_PROFILE_TABLE)
                }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $PROFILE_TABLE")
                }

    fun getMyUser(username: String, password: String): List<ProfileDatas> {
        val myprof = ArrayList<ProfileDatas>()
        val selectQuery = "SELECT * FROM $PROFILE_TABLE whCOL_$COL_EMAIL = $username and " +
                "$COL_PASS = $password"
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
                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))

                myprof.add(mydatas)
             } while (cursor.moveToNext())
          }

        return myprof
          }

    //Add User
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
                mydatas.created_date = cursor.getString(cursor.getColumnIndex(COL_CREATED_DATE))

                myprof.add(mydatas)
                    } while (cursor.moveToNext())
                }
           return myprof
            }

    fun addUser(profdatas : ProfileDatas) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, profdatas.id)
        values.put(COL_PASS, profdatas.password)
        values.put(COL_NAME, profdatas.name)
        values.put(COL_MOBILE, profdatas.mobile)
        values.put(COL_CREATED_DATE, profdatas.created_date)

        db.insert(PROFILE_TABLE, null,values)
        db.close()
            }

    fun updateUser(profdatas : ProfileDatas):Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_EMAIL, profdatas.id)
        values.put(COL_PASS, profdatas.password)
        values.put(COL_NAME, profdatas.name)
        values.put(COL_MOBILE, profdatas.mobile)
        values.put(COL_CREATED_DATE, profdatas.created_date)

        return db.update(PROFILE_TABLE, values, "$COL_EMAIL=?", arrayOf(profdatas.email.toString()))
            }

    fun deleteUser(profdatas : ProfileDatas) {
        val db = this.writableDatabase
        db.delete(PROFILE_TABLE, "$COL_EMAIL=?", arrayOf(profdatas.email.toString()))
        db.close()
            }


}