package com.example.gongc.model.dataclass

data class HomeNicknameData(
    val status: Int,
    val message: String,
    val responseData: ResponseData
) {
    data class ResponseData(
        val nickname: String,
    )
}
