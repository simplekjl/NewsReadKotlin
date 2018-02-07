package com.dev.newsread.startup

import android.content.SharedPreferences
import com.dev.newsread.Presenter
import com.dev.newsread.categories.BaseCategoriesPresenter
import com.dev.newsread.data.Source
import com.dev.newsread.storage.Repository
import com.dev.newsread.sync.SyncUseCase
import com.dev.newsread.util.CATEGORIES_TO_RES_MAP
import com.dev.newsread.util.SchedulerProvider
import rx.Observable
import javax.inject.Inject

/**
 * Presenter class for the Startup interface
 * Created by jlcs on 1/25/18.
 */
class StartupPresenter@Inject constructor(private val syncUseCase: SyncUseCase,
                                          private val sharedPreferences: SharedPreferences,
                                          private val sourcesRepository: Repository<Source>,
                                          private val schedulerProvider: SchedulerProvider) : BaseCategoriesPresenter(sharedPreferences), Presenter<StartupView> {

    protected override lateinit var view: StartupView

    override fun bind(view: StartupView) {
        this.view = view
    }

    fun downloadSources() : Observable<Unit> {
        if(sourcesRepository.count() == 0L){
            return  syncUseCase.downloadSources(CATEGORIES_TO_RES_MAP.keys)
        }
       return Observable.just(Unit)

    }

    val canOpenMainView: Boolean
        get() = sourcesRepository.count() > 0

    fun close() {
        syncUseCase.close()
    }


}