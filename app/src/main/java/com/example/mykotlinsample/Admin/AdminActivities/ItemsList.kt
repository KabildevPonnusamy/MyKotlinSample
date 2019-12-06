package com.example.mykotlinsample.Admin.AdminActivities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminAdapters.ItemsAdapter
import com.example.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.example.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.admin_fragcategory.*
import kotlinx.android.synthetic.main.admin_itemsact.*

class ItemsList : AppCompatActivity() , View.OnClickListener {

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
        val item_recycleView = findViewById<RecyclerView>(R.id.item_recycle) as RecyclerView
        val add_items: ImageView = findViewById<ImageView>(R.id.add_items)
        val arrow_back: ImageView = findViewById<ImageView>(R.id.arrow_back)
        val item_name: TextView = findViewById<TextView>(R.id.item_name)
        val item_image: ImageView = findViewById<ImageView>(R.id.item_image)
        var fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener(this)

        arrow_back.setOnClickListener {
          onBackPressed()
            }

        item_recycleView.addOnItemTouchListener(
            RecyclerItemClickListenr(this, item_recycleView,
                object : RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {

                            }

                    override fun onItemLongClick(view: View?, position: Int) {
                        var itemid : Int = itemList[position].item_id
                        var itemname: String? = itemList[position].item_name
                        var itemimage: String? = itemList[position].item_img
                        var itemcateid: String? = itemList[position].cate_id
                        var itemprice: String? = itemList[position].item_price
                        var itemofrprice: String? = itemList[position].item_ofr_price
                        var itemshownstatus: String? = itemList[position].item_shown_status
                        var itemcreateddate: String? = itemList[position].item_created_date

                        Log.e("sample", "LongClkItm: " + itemname);
                        delete_item(itemid, itemname, itemimage, itemcateid, itemprice, itemofrprice,
                            itemshownstatus, itemcreateddate)
                            }
                        })
                    )

        get_intents()

        add_items.setOnClickListener {
            intent = Intent(applicationContext, Admin_Add_Items::class.java)
            intent.putExtra("cateid", cateid)
            startActivityForResult(intent, 3)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                        )
                    }

        get_Items(item_recycleView)
               }

    private fun delete_item(itemid: Int, itemname: String?, itemimage: String?, itemcateid: String?, itemprice: String?,
                            itemofrprice: String?, itemshownstatus: String?, itemcreatedate: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Item $itemname")
        builder.setMessage("Do you want to do operation?")

        builder.setPositiveButton("Delete"){dialogInterface, which ->
            dialogInterface.dismiss()
            db.deleteItems("" + itemid)
            db.close()
            get_Items(item_recycle)
                 }

        builder.setNegativeButton("Update"){dialogInterface, which ->
            dialogInterface.dismiss()
            intent = Intent(applicationContext, Admin_Updated_Item::class.java)
            intent.putExtra("cateid", cateid)

            intent.putExtra("itemid", "" + itemid)
            intent.putExtra("itemname", itemname)
            intent.putExtra("itemimage", itemimage)
            intent.putExtra("itemcateid", itemcateid)
            intent.putExtra("itemprice", itemprice)
            intent.putExtra("itemofrprice", itemofrprice)
            intent.putExtra("itemshownstatus", itemshownstatus)
            intent.putExtra("itemcreatedate", itemcreatedate)

            startActivityForResult(intent, 5)
            overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    )
                 }

        val alertDialog: AlertDialog = builder.create()
//        alertDialog.setCancelable(false)
        alertDialog.show()
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
        itemList = db.getAllItems("" + cateid) as ArrayList<ItemDatasList>
        db.close()

        itemRecycleview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = ItemsAdapter(applicationContext, itemList)
        itemRecycleview.adapter = adapter
            }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 3) {
            if(resultCode == 4) {
                Log.e("sample", "Callback Done");
                get_Items(item_recycle)
                    }
                }

        if(requestCode == 5) {
            if(resultCode == 6) {
                get_Items(item_recycle)
                    }
                }
            }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition( R.anim.no_animation, R.anim.slide_down);
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fab -> show_hidden_items(v)
                }
            }

    private fun show_hidden_items(v: View) {

                }
}