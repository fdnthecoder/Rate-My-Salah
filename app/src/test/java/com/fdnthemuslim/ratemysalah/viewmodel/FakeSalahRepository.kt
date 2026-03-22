package com.fdnthemuslim.ratemysalah.viewmodel

import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.data.repository.ISalahRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeSalahRepository : ISalahRepository {
    val saved = mutableListOf<SalahLog>()
    override suspend fun insertSalahLog(salahLog: SalahLog) {
        // Mocking REPLACE behavior on (date, salahName)
        saved.removeIf { it.date == salahLog.date && it.salahName == salahLog.salahName }
        saved.add(salahLog)
    }
    override suspend fun getSalahLog(date: LocalDate, salahName: String) = 
        saved.find { it.date == date && it.salahName == salahName }
    override suspend fun getSalahsForDate(date: LocalDate): List<SalahLog> = 
        saved.filter { it.date == date }
    override suspend fun getSalahsForMonth(startDate: LocalDate, endDate: LocalDate): List<SalahLog> = 
        saved.filter { it.date in startDate..endDate }
    override fun getAllSalahsFlow(): Flow<List<SalahLog>> = flowOf(saved)
    override suspend fun deleteSalahLog(salahLog: SalahLog) {
        saved.remove(salahLog)
    }
    override fun getSettingsFlow(): Flow<com.fdnthemuslim.ratemysalah.data.entity.AppSettings?> = flowOf(null)
    override suspend fun getSettings(): com.fdnthemuslim.ratemysalah.data.entity.AppSettings? = null
    override suspend fun updateSettings(settings: com.fdnthemuslim.ratemysalah.data.entity.AppSettings) {}
}
