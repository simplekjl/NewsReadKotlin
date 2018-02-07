package com.dev.newsread.sync

import com.dev.newsread.data.Article
import com.dev.newsread.data.Source
import rx.Observable
import java.io.Closeable

/**
 * Created by jlcs on 1/25/18.
 */
interface SyncUseCase : Closeable {
     fun downloadSources(categories: Collection<String>):Observable<Unit>
     fun downloadArticles(source: Source): Observable<Int>
     fun search(query: String): Observable<List<Article>>
}
