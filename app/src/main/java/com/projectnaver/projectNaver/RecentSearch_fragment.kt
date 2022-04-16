package com.projectnaver.projectNaver

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectnaver.RecentSearch
import com.projectnaver.projectNaver.databinding.RecentFragmentBinding

class RecentSearch_fragment  : Fragment(){
    companion object{
        private const val TAG = "RecentSearch_Fragment"
    }
    lateinit var binding : RecentFragmentBinding
    private var searchList = ArrayList<RecentSearch>(10)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): LinearLayout {
        binding = RecentFragmentBinding.inflate(inflater , container , false)
        subscribe()
        return binding.root
    }
    private fun subscribe(){
        //단말기 정보 호출
        val searchList = activity?.getSharedPreferences("serachList", Context.MODE_PRIVATE)
        val gson  = Gson()
        val json : String? = searchList?.getString("title", "")
        val type = object : TypeToken<ArrayList<RecentSearch?>?>() {}.type
        val searchListToTest :ArrayList<RecentSearch> = gson.fromJson(json , type)
        for( i in searchListToTest){
            Log.d(TAG , i.title)
        }
        //adapter 생성
            val adapter = searchListAdapter(searchListToTest)
            binding.serachList.adapter = adapter
        //adapter 터치시 영화 검색 화면으로 넘어가고 터치한 item bundle로 묶어 전송
            binding.serachList.onItemClickListener = AdapterView.OnItemClickListener{ parent: AdapterView<*>, view: View, position: Int, id: Long ->
                val item = parent.getItemAtPosition(position) as RecentSearch
                val bundle = Bundle()
                bundle.putString("title", item.title)
                view?.let { Navigation.findNavController(it).navigate(R.id.action_RecentSearch_to_SearchMovieFragment,bundle) }
            }

    }
}