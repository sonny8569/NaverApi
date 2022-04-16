package com.projectnaver.projectNaver

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.projectnaver.projectNaver.databinding.MovieAdapterBinding
import java.io.InputStream
import java.net.URL

//영화 ListView adapter
class MovieListView(val movie : ArrayList<Movie.Items>):BaseAdapter() {
    companion object {
        private const val TAG = "MovieListView"
    }
    override fun getCount(): Int {
        return movie.size
    }

    override fun getItem(postion: Int): Any {
        return movie[postion]
    }

    override fun getItemId(postion: Int): Long {
        return postion.toLong()
    }

    override fun getView(postion: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = MovieAdapterBinding.inflate(LayoutInflater.from(parent!!.context))
        val handler = Handler()
        val showTheMovie = movie[postion]
        binding.title.text = "제목"+" "+showTheMovie.title.replace("<b>","").replace("</b>","")
        binding.date.text = "출시"+" "+showTheMovie.pubDate
        binding.rate.text = "평점"+" "+showTheMovie.userRating.toString()
        val t = Thread {
            try {
                if (showTheMovie.image != null) {
                    val url = URL(showTheMovie.image)
                    val instream: InputStream = url.openStream()
                    val bitmap = BitmapFactory.decodeStream(instream)
                    if(bitmap!==null) {
                        handler.post(Runnable//외부쓰레드에서 메인 UI에 접근하기 위해 Handler를 사용
                        {
                            Log.d(TAG , "Image is Success Load")
                            binding.image.setImageBitmap(bitmap)
                        })
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG , e.toString())
            }
        }
        t.start()
        binding.layout.setOnClickListener(View.OnClickListener {
            parent!!.context.applicationContext.startActivity(
                Intent(Intent.ACTION_VIEW , Uri.parse(showTheMovie.link)).addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                ))
        })
        return binding.root
    }
}