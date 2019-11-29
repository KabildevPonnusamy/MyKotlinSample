package com.example.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import kotlinx.android.synthetic.main.admin_itemsact.*

class ItemsList : AppCompatActivity() {

    internal lateinit var db: DBHelper
    var itemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_itemsact)

        db = DBHelper(applicationContext)
        val item_recycleView: RecyclerView = findViewById<RecyclerView>(R.id.item_recycle)
        val add_items: ImageView = findViewById<ImageView>(R.id.add_items)
        add_items.setOnClickListener {

                    }

        get_Items(item_recycleView)
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
        }