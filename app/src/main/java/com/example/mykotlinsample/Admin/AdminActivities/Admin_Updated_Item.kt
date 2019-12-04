package com.example.mykotlinsample.Admin.AdminActivities

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mykotlinsample.R

class Admin_Updated_Item : AppCompatActivity(), View.OnClickListener {

    var itemid: String = ""
    var itemname: String = ""
    var itemimage: String = ""
    var itemcateid: String = ""
    var itemprice: String = ""
    var itemofrprice: String = ""
    var itemshownstatus: String = ""
    var itemcreatedate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_updateitems)
        supportActionBar ?.hide()

        get_Intents()
        set_values()
                }

    private fun set_values() {
        var upditem_back: ImageView = findViewById(R.id.upditem_back)
        var updated_image: ImageView = findViewById(R.id.updated_image)
        var upd_image: ImageView = findViewById(R.id.upd_image)
        var upd_itemname: EditText = findViewById(R.id.upd_itemname)
        var upd_price_dollor: EditText = findViewById(R.id.upd_price_dollor)
        var upd_price_cents: EditText = findViewById(R.id.upd_price_cents)
        var upd_offer_dollor: EditText = findViewById(R.id.upd_offer_dollor)
        var upd_offer_cents: EditText = findViewById(R.id.upd_offer_cents)
        var status_toggle: ToggleButton = findViewById(R.id.status_toggle)
        var update_item_btn: Button = findViewById(R.id.update_item_btn)

            }

    private fun get_Intents() {
        itemid = intent.getStringExtra("itemid")
        itemname = intent.getStringExtra("itemname")
        itemimage = intent.getStringExtra("itemimage")
        itemcateid = intent.getStringExtra("itemcateid")
        itemprice = intent.getStringExtra("itemprice")
        itemofrprice = intent.getStringExtra("itemofrprice")
        itemshownstatus = intent.getStringExtra("itemshownstatus")
        itemcreatedate = intent.getStringExtra("itemcreatedate")
            }

    override fun onClick(v: View?) {
        
            }
}