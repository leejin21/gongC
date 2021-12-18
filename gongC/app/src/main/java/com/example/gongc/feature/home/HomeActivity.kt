package com.example.gongc.feature.home

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.gongc.R
import com.example.gongc.feature.graph.DailyGraph
import com.example.gongc.feature.graph.MonthlyGraph
import com.example.gongc.feature.graph.WeeklyGraph
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 툴바 연결
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // 탭 레이아웃, 뷰 페이저 연결
        val tabLayout = findViewById<TabLayout>(R.id.layout_home_tab)
        tabLayout.isTabIndicatorFullWidth = false
//        tabLayout.setSelectedTabIndicator(R.color.deep_green)

        val viewPager = findViewById<ViewPager2>(R.id.pager_home)

        val pagerAdapter = PagerFragmentStateAdapter(this)
        pagerAdapter.addFragment(DailyGraph())
        pagerAdapter.addFragment(WeeklyGraph())

        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){})

        // 탭 레이아웃 붙이기
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            val tabName = when(position){
                0 -> "Daily"
                1 -> "Weekly"
                else -> "-"
            }
            tab.text = tabName
        }.attach()

    }
}