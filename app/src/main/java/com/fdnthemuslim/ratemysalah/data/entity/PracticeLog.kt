package com.fdnthemuslim.ratemysalah.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "practice_logs")
data class PracticeLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: LocalDate,
    val rakat: Int,           // 2 or 4
    val rating: Int,          // 1-10
    val notes: String?,
    val loggedAt: LocalDateTime
)
