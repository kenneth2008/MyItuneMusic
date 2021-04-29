package com.myitune.datamodel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myitune.datamodel.database.model.AlbumBookmark
import com.myitune.datamodel.database.model.AlbumData

@Dao
interface AlbumBookmarkDao {

    @Query("SELECT * FROM AlbumBookmark")
    fun getAll(): MutableList<AlbumBookmark>

    @Query("SELECT * FROM AlbumBookmark Inner join AlbumData On AlbumBookmark.collectionId = AlbumData.collectionId")
    fun getDataAndJoinParent(): MutableList<AlbumData>

    @Query("SELECT * FROM AlbumBookmark Where collectionId=:collectionId Limit 1")
    fun getDataById(collectionId: Long?): AlbumBookmark?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(examples: ArrayList<AlbumBookmark>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: AlbumBookmark)

    @Query("Delete From AlbumBookmark")
    fun delete()

    @Query("Delete From AlbumBookmark Where collectionId=:collectionId ")
    fun deleteDataByID(collectionId: Long?)
}