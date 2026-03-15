package com.fdnthemuslim.ratemysalah.data.dao

import androidx.room.*
import com.fdnthemuslim.ratemysalah.data.entity.PrayerLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PrayerLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrayerLog(prayerLog: PrayerLog)
    
    @Query("SELECT * FROM prayer_logs WHERE date = :date AND prayerName = :prayerName")
    suspend fun getPrayerLog(date: LocalDate, prayerName: String): PrayerLog?
    
    @Query("SELECT * FROM prayer_logs WHERE date = :date ORDER BY CASE prayerName WHEN 'Fajr' THEN 1 WHEN 'Dhuhr' THEN 2 WHEN 'Asr' THEN 3 WHEN 'Maghrib' THEN 4 WHEN 'Isha' THEN 5 END")
    suspend fun getPrayersForDate(date: LocalDate): List<PrayerLog>
    
    @Query("SELECT * FROM prayer_logs WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getPrayersForMonth(startDate: LocalDate, endDate: LocalDate): List<PrayerLog>
    
    @Query("SELECT * FROM prayer_logs ORDER BY date DESC, loggedAt DESC")
    fun getAllPrayersFlow(): Flow<List<PrayerLog>>
    
    @Delete
    suspend fun deletePrayerLog(prayerLog: PrayerLog)
}
