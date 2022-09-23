package dev.djakonystar.urlshortener.data

import com.google.gson.annotations.SerializedName

data class RequestUrl(
    @SerializedName("url") val url: String
)
