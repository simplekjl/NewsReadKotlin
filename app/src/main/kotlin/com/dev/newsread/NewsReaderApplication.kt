package com.dev.newsread

import android.app.Application
import com.dev.newsread.injection.Injector
import com.squareup.picasso.Picasso
import io.realm.Realm

/**
 * Application class for the project
 *
 * Created by jlcs on 1/26/18.
 */

class NewsReaderApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Injector.init(this)
        val picasso = Picasso.Builder(this)
                .build()
        Picasso.setSingletonInstance(picasso)
    }
}