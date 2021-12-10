package com.example.demo.ApiRetro

import com.example.demo.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit

class RestService {

    companion object {
       private val CONNECTION_TIME = 180
        const val BASE_URL = "https://picsum.photos/v2/"
        fun getRestService(): Rest {
            val okHttpClient = getOkHttpClint()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(Rest::class.java)
        }


        fun getOkHttpClint(): OkHttpClient {
            val builder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                builder.addInterceptor(loggingInterceptor)
                builder.addNetworkInterceptor(StethoInterceptor())

            }

            builder.connectTimeout(CONNECTION_TIME.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(CONNECTION_TIME.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(CONNECTION_TIME.toLong(), TimeUnit.SECONDS)

            return builder.build()
        }
   }
}