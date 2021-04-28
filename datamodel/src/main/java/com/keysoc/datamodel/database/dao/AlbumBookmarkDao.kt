package com.keysoc.datamodel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.keysoc.datamodel.database.model.AlbumBookmark

@Dao
interface AlbumBookmarkDao {

    @Query("SELECT * FROM AlbumBookmark")
    fun getAll(): List<AlbumBookmark>

    @Query("SELECT * FROM AlbumBookmark Where collectionId=:collectionId")
    fun getDataById(collectionId: Long?): List<AlbumBookmark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(examples: ArrayList<AlbumBookmark>)

    @Query("Delete From AlbumBookmark")
    fun delete()
}