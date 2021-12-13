package com.example.gongc.model.dataclass

data class ConcentDailyData(
    val status: Int,
    val message: String,
    val responseData: ResponseData
) {
    data class ResponseData(
        val concent: Int,
        val play: Int,
        val total: Int
    )
}
