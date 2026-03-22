package com.fdnthemuslim.ratemysalah.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Create the new practice_logs table without affecting existing tables
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS `practice_logs` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                        `date` TEXT NOT NULL, 
                        `rakat` INTEGER NOT NULL, 
                        `rating` INTEGER NOT NULL, 
                        `notes` TEXT, 
                        `loggedAt` TEXT NOT NULL
                    )
                """.trimIndent())
            }
        }
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "rate_my_prayer_database"
                )
                .addMigrations(MIGRATION_1_2)
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
