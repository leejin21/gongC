package com.example.gongc.feature.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewpager2.widget.ViewPager2
import com.example.gongc.R
import com.example.gongc.feature.graph.DailyGraph
import com.example.gongc.feature.graph.WeeklyGraph
import com.example.gongc.model.dataclass.HomeNicknameData
import com.example.gongc.model.network.RetrofitService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.gongc.MainActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 툴바 연결
        toolbar = findViewById<Toolbar>(R.id.toolbar)
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

        // 인텐트 저장하기(for 로그아웃 이벤트)
        intent = Intent(this, MainActivity::class.java)
    }

    // 로그아웃 이벤트 캐리
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true;
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                // <로그아웃> 이벤트 발생
                Log.d("debug11", "로그아웃함")
                // 토큰이 계속 초기화가 되기때문에 sharedPreferences로 저장하여 초기화 방지
                val sharedPreferences = getSharedPreferences("sFile1", MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                val token = "" // 토큰에 빈 문자열 입력
                editor.putString("token", token) // key, value를 이용하여 저장하는 형태
                editor.apply()

                // 홈 화면으로 이동
                startActivity(intent)
                true
            }
            else -> {
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
            }
        }
    }

    // REST API: home/nickname
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