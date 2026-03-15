package com.fdnthemuslim.ratemysalah.data.repository

import com.fdnthemuslim.ratemysalah.data.dao.AppSettingsDao
import com.fdnthemuslim.ratemysalah.data.dao.SalahLogDao
import com.fdnthemuslim.ratemysalah.data.entity.AppSettings
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class SalahRepository(
    private val salahLogDao: SalahLogDao,
    private val appSettingsDao: AppSettingsDao
) {
    // Salah Log operations
    suspend fun insertSalahLog(salahLog: SalahLog) {
        salahLogDao.insertSalahLog(salahLog)
    }
    
    suspend fun getSalahLog(date: LocalDate, salahName: String): SalahLog? {
        return salahLogDao.getSalahLog(date, salahName)
    }
    
    suspend fun getSalahsForDate(date: LocalDate): List<SalahLog> {
        return salahLogDao.getSalahsForDate(date)
    }
    
    suspend fun getSalahsForMonth(startDate: LocalDate, endDate: LocalDate): List<SalahLog> {
        return salahLogDao.getSalahsForMonth(startDate, endDate)
    }
    
    fun getAllSalahsFlow(): Flow<List<SalahLog>> {
        return salahLogDao.getAllSalahsFlow()
    }
    
    suspend fun deleteSalahLog(salahLog: SalahLog) {
        salahLogDao.deleteSalahLog(salahLog)
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
