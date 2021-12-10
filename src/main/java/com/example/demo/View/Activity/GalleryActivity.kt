package com.example.demo.View.Activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Model.GalleryItam

import com.example.demo.R
import com.example.demo.Utils.PaginationScrollListener
import com.example.demo.View.Adapter.GalleryAdapter
import com.example.demo.Viewmodel.GalleryViewModel
import com.example.demo.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding
    private lateinit var viewModel: GalleryViewModel
    private var adapter: GalleryAdapter? = null
    private var listGallery = ArrayList<GalleryItam>()
    private val PAGE_START = 1
    private var PAGE_SIZE=3
    private val isLastPage = false
    private var currentPage: Int = PAGE_START
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screenOrientation()
        binding= DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        init()
    }

    private fun init(){
        intilizeRecycler()
        viewModel = ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        viewModel.getgallerydataResponseModelViewModel()
            ?.observe(this, object : Observer<ArrayList<GalleryItam>> {
                override fun onChanged(galleryViewModels: ArrayList<GalleryItam>?) {
                    if (galleryViewModels != null) {
                        binding.shimmerLayout.stopShimmerAnimation()
                        binding.shimmerLayout.visibility=View.GONE
                        isLoading = false
                        binding.progressBarCircle.visibility=View.GONE
                        listGallery.addAll(galleryViewModels!!)
                        setAdapters(listGallery)
                    }else{
                        binding.shimmerLayout.stopShimmerAnimation()
                        binding.shimmerLayout.visibility=View.GONE
                        isLoading = false
                        binding.progressBarCircle.visibility=View.GONE
                    }


                }
            })

        callApi(currentPage)


    }
    private fun callApi(currentpage:Int){
        if (isNetworkConnected(this)){
            isLoading=true
            binding.shimmerLayout.startShimmerAnimation()
            binding.shimmerLayout.visibility=View.VISIBLE
            if(currentpage>1){
                binding.progressBarCircle.visibility=View.VISIBLE
            }
            viewModel.setgalleryData(currentpage)
        }

    }

    private fun setAdapters(data: ArrayList<GalleryItam>) {
        adapter!!.addData(data)
    }


    private fun intilizeRecycler() {
        adapter = GalleryAdapter(this, listGallery)
        val gridLayoutManager: LinearLayoutManager =
            GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false)
        binding.recyclerItam.setLayoutManager(gridLayoutManager)
        binding.recyclerItam.setItemAnimator(DefaultItemAnimator())
        binding.recyclerItam.setAdapter(adapter)
        binding.recyclerItam.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager!!) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }
            override fun isLoading(): Boolean {
                return isLoading
            }
            override fun loadMoreItems() {
                if(isLoading == false) {
                    currentPage += 1
                    if (currentPage<=PAGE_SIZE)
                        callApi(currentPage)
                }
            }
        })


    }


    private fun screenOrientation(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    }

    fun isNetworkConnected(mContext: Context): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}