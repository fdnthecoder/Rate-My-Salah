package com.fdnthemuslim.ratemysalah.data.repository

import com.fdnthemuslim.ratemysalah.data.dao.AppSettingsDao
import com.fdnthemuslim.ratemysalah.data.dao.SalahLogDao
import com.fdnthemuslim.ratemysalah.data.dao.PracticeLogDao
import com.fdnthemuslim.ratemysalah.data.entity.AppSettings
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.data.entity.PracticeLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class SalahRepository(
    private val salahLogDao: SalahLogDao,
    private val appSettingsDao: AppSettingsDao,
    private val practiceLogDao: PracticeLogDao
) : ISalahRepository {
    // Salah Log operations
    override suspend fun insertSalahLog(salahLog: SalahLog) {
        salahLogDao.insertSalahLog(salahLog)
    }

    override suspend fun getSalahLog(date: LocalDate, salahName: String): SalahLog? {
        return salahLogDao.getSalahLog(date, salahName)
    }

    override suspend fun getSalahsForDate(date: LocalDate): List<SalahLog> {
        return salahLogDao.getSalahsForDate(date)
    }

    override suspend fun getSalahsForMonth(startDate: LocalDate, endDate: LocalDate): List<SalahLog> {
        return salahLogDao.getSalahsForMonth(startDate, endDate)
    }

    override fun getAllSalahsFlow(): Flow<List<SalahLog>> {
        return salahLogDao.getAllSalahsFlow()
    }

    override suspend fun deleteSalahLog(salahLog: SalahLog) {
        salahLogDao.deleteSalahLog(salahLog)
    }

    // Practice operations
    override suspend fun insertPracticeLog(practiceLog: PracticeLog) {
        practiceLogDao.insertPracticeLog(practiceLog)
    }

    override fun getPracticeLogsForDate(date: LocalDate): Flow<List<PracticeLog>> {
        return practiceLogDao.getPracticeLogsForDate(date)
    }

    override fun getAllPracticeLogs(): Flow<List<PracticeLog>> {
        return practiceLogDao.getAllPracticeLogs()
    }

    override suspend fun deletePracticeLog(practiceLog: PracticeLog) {
        practiceLogDao.deletePracticeLog(practiceLog)
    }

    // Settings operations
    override fun getSettingsFlow(): Flow<AppSettings?> {
        return appSettingsDao.getSettingsFlow()
    }

    override suspend fun getSettings(): AppSettings? {
        return appSettingsDao.getSettings()
    }

    override suspend fun updateSettings(settings: AppSettings) {
        appSettingsDao.updateSettings(settings)
    }
}
