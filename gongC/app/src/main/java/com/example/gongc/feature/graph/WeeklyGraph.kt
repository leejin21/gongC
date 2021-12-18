package com.example.gongc.feature.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.gongc.R
import com.example.gongc.databinding.FragmentWeeklyGraphBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

import com.github.mikephil.charting.formatter.ValueFormatter




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
        initBarChart()
        return binding.root
    }

    private fun initBarChart(){

        barChart.description.isEnabled = false
        barChart.setDrawGridBackground(false)

        barChart.legend.isEnabled = false
        barChart.axisLeft.isEnabled = false
        barChart.axisRight.isEnabled = false
        barChart.xAxis.isEnabled = true

        barChart.animateY(1000, Easing.EaseInOutCubic)

        val groupSpace = 0.3f
        val barSpace = 0.1f // x2 DataSet
        val barWidth = 0.25f // x2 DataSet
        // should satisfy (barSpace + barWidth) * 2 + groupSpace = 1 to make x axis labels be centered

        val valueList1 = ArrayList<Double>()
        val valueList2 = ArrayList<Double>()
        val entries1 = ArrayList<BarEntry>()
        val entries2 = ArrayList<BarEntry>()

        // Monday to Sunday add xaxis label
        val xAxisLabel = ArrayList<String>()
        xAxisLabel.add("Mon"); xAxisLabel.add("Tue"); xAxisLabel.add("Wed"); xAxisLabel.add("Thu"); xAxisLabel.add("Fri"); xAxisLabel.add("Sat"); xAxisLabel.add("Sun")

        // input data
        for(i:Int in 1..7){
            valueList1.add(0.0)
            valueList2.add(0.0)
        }

        // fit the data into a bar
        for (i:Int in 0 until valueList1.size) run {
            val barEntry1 = BarEntry(i.toFloat(), valueList1[i].toFloat())
            entries1.add(barEntry1)
            val barEntry2 = BarEntry(i.toFloat(), valueList2[i].toFloat())
            entries2.add(barEntry2)
        }

        val barDataSet1 = BarDataSet(entries1, "dataset1")
        val barDataSet2 = BarDataSet(entries2, "dataset2")

        barDataSet1.setDrawValues(false)
        barDataSet2.setDrawValues(false)

        barDataSet1.colors = listOf(context?.let { ContextCompat.getColor(it, R.color.pastel_green) })
        barDataSet2.colors = listOf(context?.let { ContextCompat.getColor(it, R.color.pastel_red) })

        val data = BarData(barDataSet1, barDataSet2)
        data.setValueFormatter(LargeValueFormatter())

        barChart.data= data
        barChart.barData.barWidth = barWidth;
        barChart.groupBars(0f, groupSpace, barSpace)

        barChart.xAxis.axisMinimum = 0F
        barChart.xAxis.axisMaximum = 0 + barChart.barData.getGroupWidth(groupSpace, barSpace) * (valueList1.size)
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabel)
        barChart.xAxis.setCenterAxisLabels(true)
        barChart.xAxis.setDrawGridLines(false)
        barChart.setScaleEnabled(false)


        barChart.invalidate()

    }

    private fun updateBarChart(concentValList:ArrayList<Double>, playValList:ArrayList<Double>){

        val concentEntries = ArrayList<BarEntry>()
        val playEntries = ArrayList<BarEntry>()

        // Monday to Sunday add xaxis label
        val xAxisLabel = ArrayList<String>()
        xAxisLabel.add("Mon"); xAxisLabel.add("Tue"); xAxisLabel.add("Wed"); xAxisLabel.add("Thu"); xAxisLabel.add("Fri"); xAxisLabel.add("Sat"); xAxisLabel.add("Sun")

        // fit the data into a bar
        for (i:Int in 0 until concentValList.size) run {
            val barEntry1 = BarEntry(i.toFloat(), concentValList[i].toFloat())
            concentEntries.add(barEntry1)
            val barEntry2 = BarEntry(i.toFloat(), playValList[i].toFloat())
            playEntries.add(barEntry2)
        }

        val concentDataSet = BarDataSet(concentEntries, "concent")
        val playDataSet = BarDataSet(playEntries, "play")

        concentDataSet.setDrawValues(false)
        playDataSet.setDrawValues(false)

        concentDataSet.colors = listOf(context?.let { ContextCompat.getColor(it, R.color.pastel_green) })
        playDataSet.colors = listOf(context?.let { ContextCompat.getColor(it, R.color.pastel_red) })

        val data = BarData(concentDataSet, playDataSet)
        data.setValueFormatter(LargeValueFormatter())

        barChart.data= data
        barChart.invalidate()

    }



}