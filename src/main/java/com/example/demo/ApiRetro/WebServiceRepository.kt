package com.example.demo.ApiRetro

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.demo.Model.GalleryItam

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File
import java.util.ArrayList

class WebServiceRepository(val application: Application) {
    private val gallerydataListResponseModelData= MutableLiveData<ArrayList<GalleryItam>>()


    fun getgallerydataListData(): MutableLiveData<ArrayList<GalleryItam>>? {
        return gallerydataListResponseModelData
    }


    fun sendgalleryListData(currentpage:Int) {
        val functionals = RestService.getRestService().getgalleryData(currentpage)


        functionals.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<GalleryItam>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(responseBody: ArrayList<GalleryItam>) {
                    gallerydataListResponseModelData.postValue(responseBody)
                }

                override fun onError(e: Throwable) {
                    gallerydataListResponseModelData.postValue(null)
                }

                override fun onComplete() {

                }
            })
    }
}