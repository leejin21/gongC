package com.example.gongc.feature.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gongc.databinding.FragmentWeeklyGraphBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter

class WeeklyGraph : Fragment() {
    private var _binding: FragmentWeeklyGraphBinding? = null
    private val binding get() = _binding!!
    private lateinit var barChart: BarChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // * 뷰 바인딩
        _binding = FragmentWeeklyGraphBinding.inflate(inflater, container, false)

        // * 바차트 설정
        barChart = binding.barchart
        showBarChart()
        return binding.root
    }

    private fun showBarChart(){

        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)

        barChart.legend.isEnabled = false
        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.isEnabled = false
        barChart.animateY(1000, Easing.EaseInOutCubic)
        barChart.setDrawValueAboveBar(false)

        val groupSpace = 0.08f
        val barSpace = 0.03f // x4 DataSet

        val barWidth = 0.2f // x4 DataSet

        val valueList1 = ArrayList<Double>()
        val valueList2 = ArrayList<Double>()
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()
        val title: String = "title"

        // input data
        for(i:Int in 0..5){
            valueList1.add(i*100.1)
            valueList2.add(i*200.1)
        }

        // fit the data into a bar
        for (i:Int in 0 until valueList1.size) run {
            val barEntry1 = BarEntry(i.toFloat(), valueList1[i].toFloat())
            entries1.add(barEntry1)
            val barEntry2 = BarEntry(i.toFloat(), valueList1[i].toFloat())
            entries2.add(barEntry2)
        }

        val barDataSet1 = BarDataSet(entries1, "dataset1")
        val barDataSet2 = BarDataSet(entries2, "dataset2")
        val data = BarData(barDataSet1, barDataSet2)
        data.setValueFormatter(LargeValueFormatter())

        barChart.data= data
        barChart.barData.barWidth = barWidth;
        barChart.groupBars(0f, groupSpace, barSpace)
        barChart.invalidate()

    }

}