package com.dev.newsread.startup

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ToggleButton
import com.dev.newsread.R
import com.dev.newsread.articles.ArticlesActivity
import com.dev.newsread.categories.BaseCategoriesActivity
import com.dev.newsread.extensions.showToast
import com.dev.newsread.extensions.startActivity
import com.dev.newsread.injection.Injector
import kotlinx.android.synthetic.main.app_bar_main.toolbar
import kotlinx.android.synthetic.main.layout_categories.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

/**
 * Created by jlcs on 1/25/18.
 */
class StartupActivity : BaseCategoriesActivity(), StartupView {

    @Inject
    override lateinit var presenter: StartupPresenter

    override val categoriesViewGroup: ViewGroup
        get() = categoriesHost

    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.appComponent.inject(this)
        presenter.bind(this)

        if (!presenter.canOpenMainView) {
            setContentView(R.layout.activity_startup)
            toolbar.title = getString(R.string.app_name)
            var name : String = "0"
            name.length

            fillCategories()

            var foundFirst = false
            for (i in 0 until categoriesHost.childCount) {
                val view = categoriesHost.getChildAt(i)
                if (view is ToggleButton && !foundFirst) {
                    view.isChecked = true
                    foundFirst = true
                }
            }

            downloadBtn.setOnClickListener {
                job = launch(UI) {
                    val dialog = showProgressDialog()
                    try {
                        presenter.downloadSourcesAsync()
                        dialog.dismiss()
                        startMainView()
                    } catch (fail: Throwable) {
                        Log.e("Sync", fail.message, fail)
                        dialog.dismiss()
                        showToast(getString(R.string.error_download_sources))
                    }
                }
            }
            presenter.onStartCategorySelect()
        } else {
            startMainView()
        }
    }

    private fun showProgressDialog() = ProgressDialog.show(this, getString(R.string.downloading_sources), "", true, false)

    private fun startMainView() {
        startActivity<ArticlesActivity>()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }
}
