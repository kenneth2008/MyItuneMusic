package com.myitune.datamodel.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myitune.datamodel.BuildConfig
import com.myitune.datamodel.R
import com.myitune.datamodel.database.dao.AlbumBookmarkDao
import com.myitune.datamodel.database.dao.AlbumDataDao
import com.myitune.datamodel.database.model.AlbumBookmark
import com.myitune.datamodel.database.model.AlbumData

@Database(
    entities = [
        AlbumData::class,
        AlbumBookmark::class
    ], version = 1
)
//@TypeConverters(
//    StringArrayConverter::class,
//    QRAccessRightStatusConverter::class,
//    BookingTypeConverter::class,
//    TransportInfoTypeConverter::class
//)
abstract class AppDatabase : RoomDatabase() {

    /**
     * This is the Phrase data access object instance
     * @return the dao to phrase database operations
     */

    abstract fun getAlbumBookmarkDao(): AlbumBookmarkDao

    abstract fun getAlbumDataDao(): AlbumDataDao


    companion object {

        /**
         * This is just for singleton pattern
         */
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(application: Application): AppDatabase {
            if (INSTANCE == null) {
                val dbName = if (BuildConfig.IS_UAT) {
                    application.getString(R.string.shared_database_name) + application.getString(R.string.db_name_postfix)
                } else {
                    application.getString(R.string.shared_database_name)
                }

                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        // Get PhraseRoomDatabase database instance
                        INSTANCE = Room.databaseBuilder(
                            application,
                            AppDatabase::class.java,
                            dbName
                        ).allowMainThreadQueries()
//                            .addMigrations(MIGRATION_1_2())
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}