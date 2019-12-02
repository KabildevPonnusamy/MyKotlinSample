package com.example.mykotlinsample.Admin.AdminAdapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminActivities.ItemsList
import com.example.mykotlinsample.Admin.AdminFragments.CategoryFragment
import com.example.mykotlinsample.Admin.AdminInterfaces.CateAdapterOnClick
import com.example.mykotlinsample.Admin.AdminModels.CategoryList
import com.example.mykotlinsample.R

class CategoryAdapter(
    var context: Context,
    val catelist: ArrayList<CategoryList>,
    var activity: FragmentActivity
    )  : RecyclerView.Adapter<CategoryAdapter.ViewHolder> (){


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var cate_name: TextView
        var cate_image: ImageView
        var cateitemClickListener: CateAdapterOnClick? = null

        init {
            cate_name = itemView.findViewById(R.id.cate_name) as TextView
            cate_image = itemView.findViewById(R.id.cate_image) as ImageView
            itemView.setOnClickListener(this)
                   }
         fun setCateAdapterItemClick(itemClickListener: CateAdapterOnClick) {
             cateitemClickListener = itemClickListener
                        }

        override fun onClick(v: View?) {
            this.cateitemClickListener!!.onCategoryClick(v!!, adapterPosition)
                    }

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
        holder.setCateAdapterItemClick(object: CateAdapterOnClick {
            override fun onCategoryClick(view: View, position: Int) {

                         }
                    })

        val bitmap: Bitmap = BitmapFactory.decodeFile(cItems.cate_img)
        holder.cate_image!!.setImageBitmap(bitmap)

        holder.cate_image.setOnClickListener{

                    }
                }
        
    }

