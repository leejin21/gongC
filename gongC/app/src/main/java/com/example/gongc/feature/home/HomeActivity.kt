package com.example.gongc.feature.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.gongc.R
import com.example.gongc.feature.graph.DailyGraph
import com.example.gongc.feature.graph.MonthlyGraph
import com.example.gongc.feature.graph.WeeklyGraph
import com.example.gongc.model.dataclass.ConcentWeeklyData
import com.example.gongc.model.dataclass.HomeNicknameData
import com.example.gongc.model.network.RetrofitService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // 닉네임 가져오기(by REST API)
        loadData()
    }


    private fun loadData(){
        val sharedPreferences: SharedPreferences? = this.getSharedPreferences("sFile1", AppCompatActivity.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        Log.d("debug11", "$token 토큰")
        val call: Call<HomeNicknameData>? = token?.let {
            RetrofitService.service_ct_tab.getHomeNicknamedata(
                it
            )
        }

        call?.enqueue(object : Callback<HomeNicknameData> {
            override fun onFailure(call: Call<HomeNicknameData>, t: Throwable) {
                Log.d("debug11", "from daily graph loaddata, fail $t")
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<HomeNicknameData>,
                response: Response<HomeNicknameData>
            ) {
                Log.d("debug11", "from daily graph loaddata, on response")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
                        val data = response.body()
                        data?.responseData?.let{

                            // 프래그먼트 텍스트들 업데이트
                            val txtHomeNickname = findViewById<TextView>(R.id.txt_home_nickname)
                            txtHomeNickname.text = it.nickname

                        }
                    }?: showError(response.errorBody())
            }

        })
    }

    private fun showError(error: ResponseBody?){
        /* [REST] 화면 진입 이후 받아온 API에 문제 있을 때 호출됨 */
        val e = error ?: return
        val ob = JSONObject(e.string())
        Log.d("debug11", ob.getString("message"))
    }

}