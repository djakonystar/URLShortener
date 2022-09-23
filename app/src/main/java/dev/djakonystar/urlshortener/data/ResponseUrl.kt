package dev.djakonystar.urlshortener.data

import com.google.gson.annotations.SerializedName

data class ResponseUrl(
    @SerializedName("result_url") val resultUrl: String
)
