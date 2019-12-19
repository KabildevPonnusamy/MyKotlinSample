package com.tastycafe.mykotlinsample.Users.UserFragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tastycafe.mykotlinsample.Admin.AdminModels.CategoryList
import com.tastycafe.mykotlinsample.Admin.AdminModels.ItemDatasList
import com.tastycafe.mykotlinsample.Admin.AdminSupportClasses.RecyclerItemClickListenr
import com.tastycafe.mykotlinsample.Database.DBHelper
import com.tastycafe.mykotlinsample.R
import com.tastycafe.mykotlinsample.Users.UserAdapters.UserCateAdapter
import com.tastycafe.mykotlinsample.Users.UserAdapters.UserItemsAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), View.OnClickListener {

    private val sharedPrefFile = "coffee_preference"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    var strName: String = ""
    var categorylist: ArrayList<CategoryList> = ArrayList<CategoryList>()
    var itemList: ArrayList<ItemDatasList> = ArrayList<ItemDatasList>()
    internal lateinit var db: DBHelper
    var selectpos: Int = 0
    lateinit var adapter: UserCateAdapter
    lateinit var itemadapter : UserItemsAdapter
    var cid: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        get_preferences()
        userName.setText(strName)
        show_categories()
        user_logout.setOnClickListener(this)
            }

    private fun show_categories() {

        show_category_datas()

        cate_recycle.addOnItemTouchListener(
            RecyclerItemClickListenr(requireActivity(),
                cate_recycle,
                object :
                    RecyclerItemClickListenr.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        adapter.select_pos = position
                        cate_recycle.adapter!!.notifyDataSetChanged()
                        get_item_datas(position)
                            }

                    override fun onItemLongClick(view: View?, position: Int) {
                    }
                })
            )
        }

    private fun get_item_datas(cid: Int) {
        var cateid: Int = cid + 1

        itemList.clear()
        itemList = db.getAllItems("" + cateid) as ArrayList<ItemDatasList>
        db.close()

        if(itemList != null) {
            if(itemList.size > 0) {
                no_items_layout.visibility = View.GONE
                cate_items_recycle.visibility = View.VISIBLE

                cate_items_recycle.layoutManager = LinearLayoutManager(
                    this.context, RecyclerView.HORIZONTAL,
                    false
                            )

                itemadapter =
                    UserItemsAdapter(
                        requireContext(),
                        itemList
                            )

                cate_items_recycle.adapter = itemadapter
                    } else {
                no_items_layout.visibility = View.VISIBLE
                cate_items_recycle.visibility = View.GONE
                    }
                }
            }

    private fun show_category_datas() {
        categorylist.clear()
        categorylist = db.getCategories() as ArrayList<CategoryList>
        db.close()

        if (categorylist != null) {
            if (categorylist.size > 0) {
                get_item_datas(cid)
                cate_recycle.layoutManager = LinearLayoutManager(
                    this.context, RecyclerView.HORIZONTAL,
                    false
                )

                adapter =
                    UserCateAdapter(
                        requireContext(),
                        categorylist,
                        selectpos
                    )

                cate_recycle.adapter = adapter
                    } else {

                    }
                }
            }

    private fun get_preferences() {
        db = DBHelper(requireContext())

        sharedPref = activity!!.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPref.edit()
        strName = sharedPref.getString("user_name", "").toString()
            }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.user_logout -> userLogout()
                }
            }

    private fun userLogout() {
        editor!!.clear()
        editor!!.commit()
        activity?.finish()
            }
}
