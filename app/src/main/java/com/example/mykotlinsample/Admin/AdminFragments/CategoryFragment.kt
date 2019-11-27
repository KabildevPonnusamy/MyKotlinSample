package com.example.mykotlinsample.Admin.AdminFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminActivities.Admin_Add_Category
import com.example.mykotlinsample.Admin.AdminAdapters.CategoryAdapter
import com.example.mykotlinsample.Admin.AdminModels.CategoryList
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import kotlinx.android.synthetic.main.admin_fragcategory.*

class CategoryFragment : Fragment() {

    var categorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
    internal lateinit var db: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view : View = inflater?.inflate(R.layout.admin_fragcategory, null)
        db = DBHelper(requireContext())

        val icon_add = view.findViewById<ImageView>(R.id.icon_add)
        val caterecycle = view.findViewById<RecyclerView>(R.id.caterecycle)
        get_categories()
        icon_add.setOnClickListener {
            activity!!.intent = Intent(activity, Admin_Add_Category::class.java)
            startActivity(activity!!.intent)
            requireActivity().overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    );
                }

        return view
                }

    private fun get_categories() {
        categorylist = db.getCategories() as ArrayList<CategoryList>
        db.close()

        if(categorylist != null) {
            if (categorylist.size > 0) {
                caterecycle.layoutManager = GridLayoutManager(activity, 2)
                caterecycle.adapter = CategoryAdapter(requireContext(), categorylist)
                    } else {

                       }
                  }
              }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


                }
}