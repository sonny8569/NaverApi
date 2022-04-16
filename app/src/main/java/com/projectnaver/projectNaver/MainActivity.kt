package com.projectnaver.projectNaver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.projectnaver.projectNaver.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var nav : NavController
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
//    val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    private val SearchMovie_fragment = SearchMovie_fragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        subscribe()
    }
    private fun subscribe(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
//            navController.navigate(R.id.action_RecentSearch_to_SearchMovieFragment)
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.nav_host_fragment ,SearchMovie_fragment )
//        transaction.commit()
    }
}