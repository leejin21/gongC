package com.example.gongc.model.dataclass

data class AuthData(
    val status: Int,
    val message: String,
    val error_code: Boolean,
    val responseData: ResponseData
    ) {
    data class ResponseData(
        val token: String
    )
}
