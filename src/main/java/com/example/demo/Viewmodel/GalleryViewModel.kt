package com.example.demo.Viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.demo.ApiRetro.WebServiceRepository
import com.example.demo.Model.GalleryItam


class GalleryViewModel (application: Application) : AndroidViewModel(application) {
    var webServiceRepository: WebServiceRepository = WebServiceRepository(application)
    var context: Context = application!!
    var gallerydataResponseModelData: MutableLiveData<ArrayList<GalleryItam>>? = null

    init {
        gallerydataResponseModelData = webServiceRepository.getgallerydataListData()

    }

    fun getgallerydataResponseModelViewModel(): MutableLiveData<ArrayList<GalleryItam>>? {
        return gallerydataResponseModelData
    }

    fun setgalleryData(
            currentpage:Int
    ) {

        webServiceRepository.sendgalleryListData(currentpage
        )

    }

}