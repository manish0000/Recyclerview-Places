package com.example.demo.View.Adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.demo.Model.GalleryItam
import com.example.demo.R
import com.example.demo.databinding.RowGalleryitamBinding


class GalleryAdapter(var mContext: Context, var listGallery: List<GalleryItam>) :
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {
       var listItemsGallery: ArrayList<GalleryItam> = ArrayList<GalleryItam>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemBinding: RowGalleryitamBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.row_galleryitam, parent, false
        )

        return ViewHolder(itemBinding)
    }

    fun add(r: GalleryItam?) {
        listItemsGallery.add(r!!)
        notifyItemInserted(listItemsGallery.size - 1)
    }

    fun addData(listItems: ArrayList<GalleryItam>) {
        for (result in listItems) {
            add(result)
        }
    }


    override fun getItemCount(): Int {
        return listItemsGallery.size
      /*  if (listGallery != null && listGallery.size > 0) {
            return listGallery.size
        } else {
            return 0
        }
*/
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemBinding!!.progressBarCircle.visibility=View.VISIBLE
        Glide.with(mContext)
                .load(listItemsGallery.get(position).download_url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        holder.itemBinding!!.progressBarCircle.visibility=View.GONE
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        holder.itemBinding!!.progressBarCircle.visibility=View.GONE
                        return false
                    }

                })
                .into(holder.itemBinding!!.imageView);

    }


    inner class ViewHolder(itemBinding: RowGalleryitamBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {
        var itemBinding: RowGalleryitamBinding? = null

        init {
            this.itemBinding = itemBinding
        }
    }



}