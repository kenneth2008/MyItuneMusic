package com.keysoc.datamodel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.keysoc.datamodel.database.model.AlbumData

@Dao
interface AlbumDataDao {

    @Query("SELECT * FROM AlbumData")
    fun getAll(): List<AlbumData>

    @Query("SELECT * FROM AlbumData Where collectionId=:collectionId")
    fun getDataById(collectionId: Long?): List<AlbumData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(examples: ArrayList<AlbumData>)

    @Query("Delete From AlbumData")
    fun delete()
}