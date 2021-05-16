package com.myitune.music.view.home

import com.myitune.core.model.config.observer.MyAppSubject
import com.myitune.core.view.activity.base.BaseActivity
import com.myitune.datamodel.database.AppDatabase
import com.myitune.datamodel.database.model.AlbumBookmark
import java.util.function.Predicate

class AlbumDataManager(
    private val act: BaseActivity<*>
) : MyAppSubject<AlbumBookmark>() {

     var dataList: MutableList<AlbumBookmark> =
        AppDatabase.getDatabase(act.myApp).getAlbumBookmarkDao().getAll()

    fun add(data: AlbumBookmark) {
        dataList.add(data)
        notifyObservers(null)
    }

    fun delete(collectionId: Long?) {
        dataList.removeIf { t -> t.collectionId == collectionId }
        notifyObservers(null)
    }

}