package com.kangaroo.filmore.Views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kangaroo.filmore.Models.OneMovie
import com.kangaroo.filmore.R
import com.kangaroo.filmore.Views.ui.detail.DetailActivity
import com.kangaroo.filmore.Views.ui.discover.DiscoverFragment
import com.kangaroo.filmore.Views.ui.home.HomeFragment
import com.kangaroo.filmore.Views.ui.search.SearchFragment

class BottomMainActivity : AppCompatActivity() {

lateinit var navView: BottomNavigationView
lateinit var fragmentContainerView: FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_main_)

        navView = findViewById(R.id.nav_view)
        fragmentContainerView = findViewById(R.id.nav_host_fragment)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                   setFragment(HomeFragment())
                    true
                }
                R.id.navigation_discovery -> {
                    setFragment(DiscoverFragment())
                    true
                }
                R.id.navigation_search -> {
                    setFragment(SearchFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun setFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }

    fun onClickItem(id: String, oneMovie: OneMovie){
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", id)
        intent.putExtra("oneMovie", oneMovie)
        startActivity(intent)
    }


}
