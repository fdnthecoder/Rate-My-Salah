package com.fdnthemuslim.ratemysalah.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fdnthemuslim.ratemysalah.data.converters.Converters
import com.fdnthemuslim.ratemysalah.data.dao.AppSettingsDao
import com.fdnthemuslim.ratemysalah.data.dao.SalahLogDao
import com.fdnthemuslim.ratemysalah.data.dao.PracticeLogDao
import com.fdnthemuslim.ratemysalah.data.entity.AppSettings
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.data.entity.PracticeLog

@Database(
    entities = [SalahLog::class, AppSettings::class, PracticeLog::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun salahLogDao(): SalahLogDao
    abstract fun appSettingsDao(): AppSettingsDao
    abstract fun practiceLogDao(): PracticeLogDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rate_my_prayer_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
