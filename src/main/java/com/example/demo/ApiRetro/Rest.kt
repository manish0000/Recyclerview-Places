package com.example.demo.ApiRetro

import com.example.demo.Model.GalleryItam
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Rest {
    @GET("list?")
    abstract fun getgalleryData(@Query("page") page:Int): Observable<ArrayList<GalleryItam>>

}