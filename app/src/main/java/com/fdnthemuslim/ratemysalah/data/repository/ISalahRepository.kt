package com.fdnthemuslim.ratemysalah.data.repository

import com.fdnthemuslim.ratemysalah.data.entity.AppSettings
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ISalahRepository {
    suspend fun insertSalahLog(salahLog: SalahLog)
    suspend fun getSalahLog(date: LocalDate, salahName: String): SalahLog?
    suspend fun getSalahsForDate(date: LocalDate): List<SalahLog>
    suspend fun getSalahsForMonth(startDate: LocalDate, endDate: LocalDate): List<SalahLog>
    fun getAllSalahsFlow(): Flow<List<SalahLog>>
    suspend fun deleteSalahLog(salahLog: SalahLog)
    fun getSettingsFlow(): Flow<AppSettings?>
    suspend fun getSettings(): AppSettings?
    suspend fun updateSettings(settings: AppSettings)
}