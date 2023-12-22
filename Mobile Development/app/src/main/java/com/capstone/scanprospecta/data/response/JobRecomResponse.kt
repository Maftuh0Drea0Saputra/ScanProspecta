package com.capstone.scanprospecta.data.response

import com.google.gson.annotations.SerializedName

data class JobRecomResponse(

    @field:SerializedName("predictions")
    val predictions: List<String>
)
