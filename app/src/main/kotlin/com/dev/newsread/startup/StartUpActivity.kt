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
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * Created by jlcs on 1/25/18.
 */
class StartupActivity :  BaseCategoriesActivity(), StartupView {

    @Inject
    override lateinit var presenter: StartupPresenter

    override val categoriesViewGroup: ViewGroup
        get() = categoriesHost

    private var dialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.appComponent.inject(this)
        presenter.bind(this)

        if (!presenter.canOpenMainView) {
            setContentView(R.layout.activity_startup)
            toolbar.title = getString(R.string.app_name)
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
                val dialog = showProgressDialog()
                presenter.downloadSources()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe({},{fail ->
                            Log.e("Sync",fail.message,fail)
                            dialog.dismiss()
                        },{
                            dialog.dismiss()
                            startMainView()
                        })
            }
            presenter.onStart()
        } else {
            startMainView()
        }
    }

    private fun showProgressDialog() = ProgressDialog.show(this, getString(R.string.downloading_sources), "", true, false)

    override fun startMainView() {
        dialog?.dismiss()
        startActivity<ArticlesActivity>()
    }

    override fun onError(fail: Throwable) {
        Log.e("Sync", fail.message, fail)
        dialog?.dismiss()
        showToast(getString(R.string.error_download_sources))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.close()
    }

}