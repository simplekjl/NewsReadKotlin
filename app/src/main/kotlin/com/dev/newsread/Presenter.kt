package com.dev.newsread

/**
 * Created by jlcs on 1/25/18.
 */
interface Presenter<in T : BaseView> {
    fun bind(view : T)
}