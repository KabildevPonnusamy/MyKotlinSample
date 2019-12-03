package com.example.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import kotlinx.android.synthetic.main.admin_itemsact.*

class ItemsList : AppCompatActivity() {

    internal lateinit var db: DBHelper
    var itemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    var cateid: Int? = null
    var catename: String? = null
    var catestatus: String? = null
    var cateimage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_itemsact)
        supportActionBar ?.hide()

        db = DBHelper(applicationContext)
        val item_recycleView: RecyclerView = findViewById<RecyclerView>(R.id.item_recycle)
        val add_items: ImageView = findViewById<ImageView>(R.id.add_items)
        val arrow_back: ImageView = findViewById<ImageView>(R.id.arrow_back)
        val item_name: TextView = findViewById<TextView>(R.id.item_name)
        val item_image: ImageView = findViewById<ImageView>(R.id.item_image)
        arrow_back.setOnClickListener {
          setResult(-1)
          finish()
            }

        get_intents()

        add_items.setOnClickListener {

               }

        get_Items(item_recycleView)
               }

    private fun get_intents() {
        cateid = intent.getIntExtra("cateid", 0)
        catename = intent.getStringExtra("catename")
        catestatus = intent.getStringExtra("catestatus")
        cateimage = intent.getStringExtra("cateimage")
        item_name.setText(catename)
        val bitmap: Bitmap = BitmapFactory.decodeFile(cateimage)
        item_image!!.setImageBitmap(bitmap)
               }

    private fun get_Items(itemRecycleview: RecyclerView) {
        itemList.clear()
        itemList = db.getAllItems() as ArrayList<ItemDatasList>
        db.close()
            }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2) {
            if(resultCode == 3) {
                Log.e("sample", "Callback Done");
                get_Items(item_recycle)
                    }
                }
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }
        }