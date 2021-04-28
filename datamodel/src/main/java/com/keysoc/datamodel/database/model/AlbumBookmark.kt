package com.keysoc.datamodel.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AlbumBookmark {

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    var collectionId:Long? = null



}