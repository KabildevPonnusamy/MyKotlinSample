package com.example.mykotlinsample.Admin.AdminModels

class CategoryList {

    var cate_id: Int = 0
    var cate_name: String? = null
    var cate_img: String? = null
    var cate_show_status: String? = null
    var cate_created: String? = null

    constructor()

    constructor(cate_id: Int, cate_name:String, cate_img: String, cate_show_statu: String,
                cate_created: String) {
        this.cate_id = cate_id
        this.cate_name = cate_name
        this.cate_img = cate_img
        this.cate_show_status = cate_show_status
        this.cate_created = cate_created
            }
}