package com.projectnaver.projectNaver

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectnaver.RecentSearch
import com.projectnaver.projectNaver.databinding.SearchMovieFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class SearchMovie_fragment : Fragment() , View.OnClickListener{
    companion object {
        private const val TAG = "SearchMovie_fragment"
    }
    private var arrayList = ArrayList<Movie.Items>()
    lateinit var binding: SearchMovieFragmentBinding
    private var serachList = ArrayList<RecentSearch>()
    private var saveList = ArrayList<RecentSearch>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //영화 검색 페이지
        binding = SearchMovieFragmentBinding.inflate(inflater , container , false)
        subscribe()
        checkComeFromSearchListView()
        return binding.root
    }
    //초기화 함수
    private fun subscribe(){
        binding.searchMovie.text.clear()
        binding.recentSerchBtn.setOnClickListener(this)
        binding.serachMovieBtn.setOnClickListener(this)
        //최근 검색어 arraylist return 함수
        serachList = callTheSaveList()
    }
    private fun checkComeFromSearchListView(){

        val title = arguments?.getString("title")//bundle에서 글자 추출
        if(title!=null){//title이 널값이 아니면
            binding.searchMovie.setText(title)//edittext에 글자 추가
            binding.serachMovieBtn.performClick()//검색 버튼 눌림

        }
    }
    //naver Api 검색 함수 부분
    private fun GetMovie(title : String){
        if(title==null || title.trim() == ""){ // 요청 오류에 대비하여 null값 체크
            Log.e(TAG ,"The title is Null!!!!" )
            Toast.makeText(context , "검색어를 입력해주세요" , Toast.LENGTH_LONG).show()
        }else {
            try{
                //Retrofit 사용
            val movieRetrofit = Client().movieRetrofit
            movieRetrofit.getDate(
                clientId = "qOAH7ceCAaVwVGyyXaAS",//Naver Api Id
                clientPw = "uFjrY5AqGw",//Naver Api password
                type = "movie.json",//open Api Url 주소
                query = "${title}" // 검색항목
            ).enqueue(object : Callback<Movie> { // response body 쪼개기
                override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                    response!!.body()!!.items.iterator().forEach {
                        arrayList.add(
                            Movie.Items(
                                it.title,
                                it.link,
                                it.image,
                                it.subtitle,
                                it.pubDate,
                                it.director,
                                it.userRating
                            )
                        )

                    }
                    val Adapter = MovieListView(arrayList)//에뎁터 추가
                    binding.listview.adapter = Adapter
//                    serachList.add(RecentSearch(title))
                    serachList.add(0 , RecentSearch((title)))//0번째 인자만 추가해서 검색항목 처리
                }

                override fun onFailure(call: Call<Movie>, t: Throwable) {
                    Log.e(TAG ,"The Naver Load is failed")
                }
            })}catch (e : Exception){
                Log.e(TAG , e.printStackTrace().toString())
            }
        }
    }
    //단말기에 저장한 검색결과 호출함수
    private fun callTheSaveList() : ArrayList<RecentSearch>{
        val searchList = activity?.getSharedPreferences("serachList", Context.MODE_PRIVATE)
        val gson  = Gson()
        val json : String? = searchList?.getString("title", "")
        if (!json.isNullOrBlank()){
        val type = object : TypeToken<ArrayList<RecentSearch?>?>() {}.type
        val searchListToTest :ArrayList<RecentSearch> = gson.fromJson(json , type)
            return searchListToTest //단말기에 저장한 정보가 null이 아닌경우 호출해와 return 해준다
        }
        else{
            val arrayList = ArrayList<RecentSearch>() // 아닐시 빈 arraylist를 return 한다.
            return arrayList
        }

    }

    override fun onClick(v : View){
        when(v.id){
            R.id.serach_movie_btn ->{ // 영화 검색버튼
                arrayList.clear()//array list 초기화 -> adapter 초기화 이유
                GetMovie(binding.searchMovie.text.toString())//Naver Api 검색
            }
            R.id.recent_serch_btn->{
                saveTheSearchList()//단말기에 정보 저장 함수
                view?.let { Navigation.findNavController(it).navigate(R.id.action_SearchMovieFragment_to_RecentSearch) }//Navagation 사용
            }
        }
    }

    private fun saveTheSearchList() {
        var check : Int = 0
        saveList.clear()
        if(serachList.size <= check){// 0~9 까지 돌리게 하기 위해 반복문
            check = serachList.size
        }else {
            check = 9
        }

        //저장하기 위해 새로운 arraylist 사용
        for(i in 0..check){
             saveList.add(RecentSearch(serachList.get(i).title))
        }
        serachList.clear()
        //기존 단말기 정보 날리고
        val searchList = activity?.getSharedPreferences("serachList", Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = searchList!!.edit()
        editor.clear();
        editor.commit();
        //새로운 정보를 단말기에 저장
        val gson = Gson()
        var json : String = gson.toJson(saveList)
        editor.putString("title",json)
        editor.commit()
    }
}

