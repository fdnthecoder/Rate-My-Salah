package com.fdnthemuslim.ratemyprayer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fdnthemuslim.ratemyprayer.data.converters.Converters
import com.fdnthemuslim.ratemyprayer.data.dao.AppSettingsDao
import com.fdnthemuslim.ratemyprayer.data.dao.PrayerLogDao
import com.fdnthemuslim.ratemyprayer.data.entity.AppSettings
import com.fdnthemuslim.ratemyprayer.data.entity.PrayerLog

@Database(
    entities = [PrayerLog::class, AppSettings::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prayerLogDao(): PrayerLogDao
    abstract fun appSettingsDao(): AppSettingsDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rate_my_prayer_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
