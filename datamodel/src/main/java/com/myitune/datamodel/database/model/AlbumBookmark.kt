package com.myitune.datamodel.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class AlbumBookmark :Observable{

    @PrimaryKey(autoGenerate = true)
    var id:Long = 0

    var collectionId:Long? = null

    constructor(collectionId: Long?) {
        this.collectionId = collectionId
    }
}