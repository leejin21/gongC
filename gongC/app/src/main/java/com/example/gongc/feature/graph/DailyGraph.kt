package com.example.gongc.feature.graph

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.gongc.R
import com.example.gongc.databinding.FragmentDailyGraphBinding
import com.example.gongc.model.dataclass.ConcentDailyData
import com.example.gongc.model.network.RetrofitService
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyGraph : Fragment() {

    private var _binding: FragmentDailyGraphBinding? = null
    private val binding get() = _binding!!
    private lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // * 뷰 바인딩
        _binding = FragmentDailyGraphBinding.inflate(inflater, container, false)

        // * 파이차트 설정
        pieChart = binding.piechart
        initPieChart()

        // * REST API
        loadData()

        return binding.root
    }

    private fun initPieChart() {
        /* [파이차트] 처음 파이차트 구성 */
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false

        pieChart.isDrawHoleEnabled = false
        pieChart.setHoleColor(Color.WHITE)
        pieChart.transparentCircleRadius = 61F
        pieChart.centerText = ""
        pieChart.setDrawEntryLabels(false)
        pieChart.setDrawCenterText(false)
        pieChart.legend.isEnabled = false
        pieChart.animateY(1000, Easing.EaseInOutCubic)


        val yValues = ArrayList<PieEntry>()
        yValues.add(PieEntry(10F, "주식"))

        val dataSet = PieDataSet(yValues, "WalletStatus")
        dataSet.selectionShift = 5f
        dataSet.colors = listOf(context?.let { ContextCompat.getColor(it, R.color.pastel_green) },
            context?.let { ContextCompat.getColor(it, R.color.pastel_red) })

        val pdata = PieData((dataSet))
        pdata.setDrawValues(false)

        pieChart.data = pdata

    }

    private fun updatePieChart(concent: Float, play: Float){
        /* [파이차트] REST 이후 파이차트 값 업데이트 */
        val yValues = ArrayList<PieEntry>()
        PieEntry(concent, "Focus").let { yValues.add(it) }
        PieEntry(play, "Play").let { yValues.add(it) }

        val dataSet = PieDataSet(yValues, "WalletStatus")
        dataSet.selectionShift = 5f
        dataSet.colors = listOf(context?.let { ContextCompat.getColor(it, R.color.pastel_green) },
            context?.let { ContextCompat.getColor(it, R.color.pastel_red) })

        val pdata = PieData((dataSet))
        pdata.setDrawValues(false)

        pieChart.data = pdata
    }

    private fun loadData(){
        /* [REST] 화면 진입 시 GET 요청하기 */
        // TODO 로그인 이미 했을 시 해당 토큰으로 보내기
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("sFile1", AppCompatActivity.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", "")
        Log.d("debug11", "$token 토큰")
        val call: Call<ConcentDailyData>? = token?.let {
            RetrofitService.service_ct_tab.getConcentDailydata(
                it
            )
        }

        call?.enqueue(object : Callback<ConcentDailyData> {
            override fun onFailure(call: Call<ConcentDailyData>, t: Throwable) {
                Log.d("debug11", "from daily graph loaddata, fail $t")
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ConcentDailyData>,
                response: Response<ConcentDailyData>
            ) {
                Log.d("debug11", "from daily graph loaddata, on response")

                response.takeIf { it.isSuccessful }
                    ?.body()
                    ?.let { _ ->
                        val data = response.body()
                        data?.responseData?.let{
                            val focusTime = it.concent
                            val playTime = it.play
                            val totalTime = it.total

                            // 프래그먼트 텍스트들 업데이트
                            binding.txtDailyFocusedTime.text = "$focusTime minutes"
                            binding.txtDailyPlayedTime.text = "$playTime minutes"
                            binding.txtDailyTotalTime.text = "$totalTime minutes"

                            // 파이 차트 업데이트
                            updatePieChart(focusTime.toFloat(), playTime.toFloat())
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