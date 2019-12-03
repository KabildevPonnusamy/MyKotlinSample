package com.example.mykotlinsample.Admin.AdminFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykotlinsample.Admin.AdminActivities.Admin_Add_Category
import com.example.mykotlinsample.Admin.AdminActivities.ItemsList
import com.example.mykotlinsample.Admin.AdminAdapters.CategoryAdapter
import com.example.mykotlinsample.Admin.AdminModels.CategoryList
import com.example.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
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
        var recyclerView: RecyclerView = view.findViewById(R.id.caterecycle) as RecyclerView

        get_categories(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0) {
                    icon_add.visibility = View.INVISIBLE
                         } else {
                    icon_add.visibility = View.VISIBLE
                        }
                super.onScrolled(recyclerView, dx, dy)
                        }
                    })

        recyclerView.addOnItemTouchListener(RecyclerItemClickListenr(requireActivity(), recyclerView,
            object : RecyclerItemClickListenr.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                var cateid : Int = categorylist[position].cate_id
                var catename: String? = categorylist[position].cate_name
                var catestatus: String? = categorylist[position].cate_show_status
                var cateimg: String? = categorylist[position].cate_img

                val intent = Intent (context, ItemsList::class.java)
                intent.putExtra("cateid", cateid)
                intent.putExtra("catename","" + catename)
                intent.putExtra("catestatus","" + catestatus)
                intent.putExtra("cateimage", cateimg)
                startActivityForResult(intent, 10)
                requireActivity().overridePendingTransition(
                    R.anim.slide_up,
                    R.anim.no_animation
                            );
                        }
            override fun onItemLongClick(view: View?, position: Int) {
                var cateid : Int = categorylist[position].cate_id
                var catename: String? = categorylist[position].cate_name
                Log.e("sample", "LongClick: " + cateid);
                delete_category(cateid, catename)

                        }
                    }))

        icon_add.setOnClickListener {
            activity!!.intent = Intent(activity, Admin_Add_Category::class.java)
            startActivityForResult(activity!!.intent, 0)
            requireActivity().overridePendingTransition(
                R.anim.slide_up,
                R.anim.no_animation
                    );
                }

        return view
           }

    private fun delete_category(cateid: Int, catename:String?) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete $catename")
        builder.setMessage("Do you want to delete this cateory?")

        builder.setPositiveButton("Yes"){dialogInterface, which ->
            dialogInterface.dismiss()
            db.deleteCategory("" + cateid)
            db.close()
            get_categories(caterecycle)
                }
        builder.setNegativeButton("No"){dialogInterface, which ->
            dialogInterface.dismiss()
                }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
                }

    private fun get_categories(recyclerView: RecyclerView) {
        categorylist.clear()
        categorylist = db.getCategories() as ArrayList<CategoryList>
        db.close()

        if(categorylist != null) {
            if (categorylist.size > 0) {
                recyclerView.layoutManager = GridLayoutManager(this.context, 2)
                recyclerView.adapter = CategoryAdapter(requireContext(), categorylist)
                           }
                      }
                  }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 0) {
            if(resultCode == 1) {
                get_categories(caterecycle)
                        }
                    }
                }
    }