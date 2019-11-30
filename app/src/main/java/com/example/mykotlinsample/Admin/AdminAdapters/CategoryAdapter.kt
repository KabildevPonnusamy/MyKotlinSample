package com.example.mykotlinsample.Admin.AdminAdapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminActivities.ItemsList
import com.example.mykotlinsample.Admin.AdminModels.CategoryList
import com.example.mykotlinsample.R

class CategoryAdapter(var context: Context, val catelist: ArrayList<CategoryList>)  : RecyclerView.Adapter<CategoryAdapter.ViewHolder> (){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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

        holder.cate_image.setOnClickListener{
            val cateid : Int = cItems.cate_id
            val intent = Intent (context, ItemsList::class.java)
            context.startActivity(intent)
                    }
                }
        
    }

