package com.fdnthemuslim.ratemysalah.data.dao

import androidx.room.*
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface SalahLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSalahLog(salahLog: SalahLog)
    
    @Query("SELECT * FROM salah_logs WHERE date = :date AND salahName = :salahName")
    suspend fun getSalahLog(date: LocalDate, salahName: String): SalahLog?
    
    @Query("SELECT * FROM salah_logs WHERE date = :date ORDER BY CASE salahName WHEN 'Fajr' THEN 1 WHEN 'Dhuhr' THEN 2 WHEN 'Asr' THEN 3 WHEN 'Maghrib' THEN 4 WHEN 'Isha' THEN 5 END")
    suspend fun getSalahsForDate(date: LocalDate): List<SalahLog>
    
    @Query("SELECT * FROM salah_logs WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getSalahsForMonth(startDate: LocalDate, endDate: LocalDate): List<SalahLog>
    
    @Query("SELECT * FROM salah_logs ORDER BY date DESC, loggedAt DESC")
    fun getAllSalahsFlow(): Flow<List<SalahLog>>
    
    @Delete
    suspend fun deleteSalahLog(salahLog: SalahLog)
}
