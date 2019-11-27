package com.example.mykotlinsample.Admin.AdminAdapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminModels.CategoryList
import com.example.mykotlinsample.R
import kotlinx.android.synthetic.main.admin_addcategory.*
import java.io.File
import java.net.URI

class CategoryAdapter(var context: Context, val catelist: ArrayList<CategoryList>)  : RecyclerView.Adapter<CategoryAdapter.ViewHolder> (){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cate_name = itemView.findViewById(R.id.cate_name) as TextView
        val cate_image = itemView.findViewById(R.id.cate_image) as ImageView
                    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(v)
                }

    override fun getItemCount(): Int {
          return catelist.size
                }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cItems : CategoryList = catelist[position]
        holder.cate_name.text = cItems.cate_name

        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.cate_img)
        holder.cate_image!!.setImageBitmap(bitmap)
//        holder.cate_image.setImageURI(Uri.fromFile(File(cItems.cate_img)))
                }

}