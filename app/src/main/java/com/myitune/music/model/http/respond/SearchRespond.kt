package com.myitune.music.model.http.respond

import com.myitune.datamodel.database.model.AlbumData

class SearchRespond {

    var resultCount:Long? = null

    var results:List<AlbumData>? = null

}