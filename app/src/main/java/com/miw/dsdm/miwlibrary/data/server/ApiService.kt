package com.miw.dsdm.miwlibrary.data.server

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?get_categories")
    suspend fun getAllCategories(): Response<List<CategoryResponse>>

    @GET
    suspend fun getBooks(@Query("num_items") numBooks: Int): Response<List<BookResponse>>

}