package com.example.mykotlinsample.Admin.AdminFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminAdapters.CategoryAdapter
import com.example.mykotlinsample.Admin.AdminModels.CategoryList
import com.example.mykotlinsample.Common.CommonModels.ProfileDatas
import com.example.mykotlinsample.Database.DBHelper
import com.example.mykotlinsample.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragcategory.*

class CategoryFragment : Fragment() {

    var categorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
    internal lateinit var db: DBHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view : View = inflater?.inflate(R.layout.fragcategory, null)
        db = DBHelper(requireContext())

        get_categories()

        return view
                }

    private fun get_categories() {
        categorylist = db.getCategories() as ArrayList<CategoryList>
        db.close()

        if(categorylist != null) {
            if (categorylist.size > 0) {
                categoryList.layoutManager = GridLayoutManager(activity, 2)
                categoryList.adapter = CategoryAdapter(requireContext(), categorylist)
                    } else {

                       }
                  }
              }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


                }
}