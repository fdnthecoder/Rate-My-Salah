package com.fdnthemuslim.ratemysalah.data.dao

import androidx.room.*
import com.fdnthemuslim.ratemysalah.data.entity.PracticeLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PracticeLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPracticeLog(practiceLog: PracticeLog)
    
    @Query("SELECT * FROM practice_logs WHERE date = :date")
    fun getPracticeLogsForDate(date: LocalDate): Flow<List<PracticeLog>>
    
    @Query("SELECT * FROM practice_logs ORDER BY date DESC, loggedAt DESC")
    fun getAllPracticeLogs(): Flow<List<PracticeLog>>
    
    @Delete
    suspend fun deletePracticeLog(practiceLog: PracticeLog)
}
