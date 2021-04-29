package com.myitune.datamodel.database.dao

import android.util.Log
import androidx.room.*
import com.myitune.datamodel.database.model.AlbumData

@Dao
interface AlbumDataDao {

    @Query("SELECT * FROM AlbumData")
    fun getAll(): List<AlbumData>

    @Query("SELECT * FROM AlbumData Where collectionId=:collectionId")
    fun getDataById(collectionId: Long?): List<AlbumData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(examples: List<AlbumData>)

    @Query("Delete From AlbumData")
    fun delete()

    @Transaction
    fun deleteAndInsertAll(dataList: List<AlbumData>) {
        Log.i(javaClass.simpleName, "deleteAndInsertAll().data.size:${dataList.size}")
        delete()
        insertAll(dataList)
    }
}