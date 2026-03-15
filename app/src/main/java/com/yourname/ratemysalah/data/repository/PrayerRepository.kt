package com.yourname.ratemysalah.data.repository

import com.yourname.ratemysalah.data.dao.AppSettingsDao
import com.yourname.ratemysalah.data.dao.PrayerLogDao
import com.yourname.ratemysalah.data.entity.AppSettings
import com.yourname.ratemysalah.data.entity.PrayerLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class PrayerRepository(
    private val prayerLogDao: PrayerLogDao,
    private val appSettingsDao: AppSettingsDao
) {
    // Prayer Log operations
    suspend fun insertPrayerLog(prayerLog: PrayerLog) {
        prayerLogDao.insertPrayerLog(prayerLog)
    }
    
    suspend fun getPrayerLog(date: LocalDate, prayerName: String): PrayerLog? {
        return prayerLogDao.getPrayerLog(date, prayerName)
    }
    
    suspend fun getPrayersForDate(date: LocalDate): List<PrayerLog> {
        return prayerLogDao.getPrayersForDate(date)
    }
    
    suspend fun getPrayersForMonth(startDate: LocalDate, endDate: LocalDate): List<PrayerLog> {
        return prayerLogDao.getPrayersForMonth(startDate, endDate)
    }
    
    fun getAllPrayersFlow(): Flow<List<PrayerLog>> {
        return prayerLogDao.getAllPrayersFlow()
    }
    
    suspend fun deletePrayerLog(prayerLog: PrayerLog) {
        prayerLogDao.deletePrayerLog(prayerLog)
    }
    
    // Settings operations
    fun getSettingsFlow(): Flow<AppSettings?> {
        return appSettingsDao.getSettingsFlow()
    }
    
    suspend fun getSettings(): AppSettings? {
        return appSettingsDao.getSettings()
    }
    
    suspend fun updateSettings(settings: AppSettings) {
        appSettingsDao.updateSettings(settings)
    }
}
