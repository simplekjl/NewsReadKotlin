package com.dev.newsread.search

import com.dev.newsread.Presenter
import com.dev.newsread.sync.SyncUseCase
import com.dev.newsread.util.SchedulerProvider
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

class SearchPresenter @Inject constructor(private val syncUseCase: SyncUseCase,
                                          private val schedulerProvider: SchedulerProvider) : Presenter<SearchView> {

    lateinit var view: SearchView
    private val subscriptions = CompositeSubscription()

    override fun bind(view: SearchView) {
        this.view = view
    }

    fun search(query: String) {
        syncUseCase.search(query)
                .observeOn(schedulerProvider.ui)
                .subscribe({ subjectSearch ->
                    if (subjectSearch.isEmpty()) {
                        view.onSearchResults(subjectSearch)
                    } else {
                        view.onNoSearchResults()
                    }
                }, { fail -> view.onSearchFailed(fail) })
    }
}