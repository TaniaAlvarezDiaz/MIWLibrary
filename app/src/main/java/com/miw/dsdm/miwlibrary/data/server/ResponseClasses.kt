package com.miw.dsdm.miwlibrary.data.server

import com.google.gson.annotations.SerializedName

data class CategoryResponse(@SerializedName("category_id") val id: Long, val name: String, val nicename: String)

data class BookResponse(
    @SerializedName("ID") val id: String,
    val title: String,
    val author: String,
    val content: String,
    @SerializedName("content_sort") val summary: String,
    val publisher: String,
    @SerializedName("publisher_date") val publicationYear: String,
    val language: String,
    @SerializedName("url_details") val detailsUrl: String,
    @SerializedName("cover") val imagePath: String,
    val categories: List<CategoryResponse>
)