package com.dev.newsread.storage


import com.dev.newsread.extensions.transactionAsObservable
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmQuery
import io.realm.Sort
import rx.Observable

/**
 * Created by jlcs on 1/24/18.
 */

abstract class RepositoryBase<T> : Repository<T> where T : RealmModel {

    protected val realm: Realm = Realm.getDefaultInstance()

    abstract protected val primaryKey: String

    override fun getById(id: String): T? {
        return realm.where(clazz)
                .equalTo(primaryKey, id)
                .findFirst()
    }

    override fun delete(filter: RealmQuery<T>.() -> Unit): Observable<Unit> {
        return realm.transactionAsObservable {
            val results = where(clazz)
            filter(results)
            results.findAll().deleteAllFromRealm()
        }
    }

    override fun deleteAll(): Observable<Unit> = realm.transactionAsObservable { delete(clazz) }

    override fun update(id: String, modifier: T.() -> Unit) {
        realm.executeTransaction { r ->
            val dbItem = r.where(clazz)
                    .equalTo(primaryKey, id)
                    .findFirst()
            modifier(dbItem)
        }
    }



    override fun add(item: T) {
        return realm.executeTransaction { r ->
            r.copyToRealm(item)
        }
    }

    override fun addAll(items: List<T>): Observable<Unit> =
            realm.transactionAsObservable { copyToRealmOrUpdate(items) }

    override fun count(filter: RealmQuery<T>.() -> Unit): Long {
        val results = realm.where(clazz)
        filter(results)
        return results.count()
    }

    override fun count(): Long = realm.where(clazz).count()

    override fun query(filter: RealmQuery<T>.() -> Unit, sortFields: Array<String>?, orders: Array<Sort>?): Observable<out List<T>> {
        val results = realm.where(clazz)
        filter(results)
        return if (sortFields == null) {
            results.findAllAsync().asObservable()
                    .asObservable()
                    .filter { r -> r.isLoaded }
                    .first()
        } else {
            results.findAllSortedAsync(sortFields, orders)
                    .asObservable()
                    .filter { r -> r.isLoaded }
                    .first()

        }
    }

    override fun getAll(): Observable<out List<T>> {
        return realm.where(clazz)
                .findAllAsync()
                .asObservable()
                .filter { r -> r.isLoaded }
                .first()
    }

    override fun close() {
        realm.close()
    }
}