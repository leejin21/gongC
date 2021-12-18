package com.example.gongc.model.dataclass

data class ConcentWeeklyData(
    val status: Int,
    val message: String,
    val responseData: ResponseData
) {
    data class ResponseData(
        val concentValList: ArrayList<Double>,
        val playValList: ArrayList<Double>,
        val concentTime: Int,
        val playTime: Int,
        val totalTime: Int
    )
}
