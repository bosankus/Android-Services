package com.androidplay.services.model.model

import com.squareup.moshi.Json

data class ErrorResponse(
    @field:Json(name = "code") val code: Int = 0,
    @field:Json(name = "message") val msd: String = "",
)
