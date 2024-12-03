package com.example.taskfour.utilies

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.downloadFromURL(url: String) {
    Picasso.get().load(url).into(this)
}//coil landscapist
     /*.memoryCachePolicy(CachePolicy.ENABLED)
.diskCachePolicy(CachePolicy.ENABLED)
.build()
image.load(url, imgLoader)*/