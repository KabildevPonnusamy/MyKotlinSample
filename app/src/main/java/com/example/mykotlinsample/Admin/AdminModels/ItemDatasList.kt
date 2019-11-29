package com.example.mykotlinsample.Admin.AdminModels

class ItemDatasList {

    var item_id: Int = 0
    var cate_id: String? = null
    var item_name: String? = null
    var item_img: String? = null
    var item_price: String? = null
    var item_ofr_price: String? = null
    var item_shown_status: String? = null
    var item_created_date: String? = null

    constructor()

    constructor(item_id: Int, cate_id: String, item_nme: String, item_img: String, item_price: String,
                item_ofr_price:String, item_shown_status: String, item_created_date:String) {
        this.item_id = item_id
        this.cate_id = cate_id
        this.item_name = item_name
        this.item_img = item_img
        this.item_price = item_price
        this.item_ofr_price = item_ofr_price
        this.item_shown_status = item_shown_status
        this.item_created_date = item_created_date
                }
}