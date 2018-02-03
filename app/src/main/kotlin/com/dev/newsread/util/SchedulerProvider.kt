package com.dev.newsread.util

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by jlcs on 2/2/18.
 */


interface SchedulerProvider{
    val io : Scheduler
    val ui : Scheduler
    val computation : Scheduler
    val immediate : Scheduler
}


class SchedulerProviderImpl : SchedulerProvider{
    override val io: Scheduler
        get() = Schedulers.io()
    override val ui: Scheduler
        get() = AndroidSchedulers.mainThread()
    override val computation: Scheduler
        get() = Schedulers.computation()
    override val immediate: Scheduler
        get() = Schedulers.immediate()


}