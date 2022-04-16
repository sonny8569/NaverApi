package com.projectnaver.projectNaver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.projectnaver.RecentSearch
import com.projectnaver.projectNaver.databinding.SearchAdapterBinding
//최근 검색 adapter
class searchListAdapter (val searchList : ArrayList<RecentSearch>):BaseAdapter(){
    override fun getCount(): Int {
        return searchList.size
    }

    override fun getItem(postion: Int): Any {
        return searchList[postion]
    }

    override fun getItemId(postion: Int): Long {
       return postion.toLong()
    }

    override fun getView(postion: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = SearchAdapterBinding.inflate(LayoutInflater.from(parent!!.context))
        val list = searchList[postion]
        binding.searchRecent.text = list.title
        return binding.root
    }

}