package com.keysoc.datamodel.database.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AlbumData() :Parcelable {

    var wrapperType : String? = null

    var collectionType : String? = null
    var artistId : Long? = null

    @PrimaryKey(autoGenerate = true)
    var collectionId : Long? = null

    var amgArtistId : Long? = null

    var artistName : String? = null
    var collectionName : String? = null
    var collectionCensoredName : String? = null
    var artistViewUrl : String? = null
    var collectionViewUrl : String? = null
    var artworkUrl60 : String? = null
    var artworkUrl100 : String? = null
    var collectionPrice : Double ? = null
    var collectionExplicitness : String? = null
    var trackCount : Int? = null
    var copyright : String? = null
    var country : String? = null
    var currency : String? = null
    var releaseDate : String? = null // "2008-02-01T08:00:00Z"
    var primaryGenreName : String? = null

    constructor(parcel: Parcel) : this() {
        wrapperType = parcel.readString()
        collectionType = parcel.readString()
        artistId = parcel.readValue(Long::class.java.classLoader) as? Long
        collectionId = parcel.readValue(Long::class.java.classLoader) as? Long
        amgArtistId = parcel.readValue(Long::class.java.classLoader) as? Long
        artistName = parcel.readString()
        collectionName = parcel.readString()
        collectionCensoredName = parcel.readString()
        artistViewUrl = parcel.readString()
        collectionViewUrl = parcel.readString()
        artworkUrl60 = parcel.readString()
        artworkUrl100 = parcel.readString()
        collectionPrice = parcel.readValue(Double::class.java.classLoader) as? Double
        collectionExplicitness = parcel.readString()
        trackCount = parcel.readValue(Int::class.java.classLoader) as? Int
        copyright = parcel.readString()
        country = parcel.readString()
        currency = parcel.readString()
        releaseDate = parcel.readString()
        primaryGenreName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(wrapperType)
        parcel.writeString(collectionType)
        parcel.writeValue(artistId)
        parcel.writeValue(collectionId)
        parcel.writeValue(amgArtistId)
        parcel.writeString(artistName)
        parcel.writeString(collectionName)
        parcel.writeString(collectionCensoredName)
        parcel.writeString(artistViewUrl)
        parcel.writeString(collectionViewUrl)
        parcel.writeString(artworkUrl60)
        parcel.writeString(artworkUrl100)
        parcel.writeValue(collectionPrice)
        parcel.writeString(collectionExplicitness)
        parcel.writeValue(trackCount)
        parcel.writeString(copyright)
        parcel.writeString(country)
        parcel.writeString(currency)
        parcel.writeString(releaseDate)
        parcel.writeString(primaryGenreName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlbumData> {
        override fun createFromParcel(parcel: Parcel): AlbumData {
            return AlbumData(parcel)
        }

        override fun newArray(size: Int): Array<AlbumData?> {
            return arrayOfNulls(size)
        }
    }

}