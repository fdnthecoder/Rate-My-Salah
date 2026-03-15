package com.fdnthemuslim.ratemysalah.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(tableName = "salah_logs")
data class SalahLog(
    @PrimaryKey(autoGenerate = true) 
    val id: Int = 0,
    
    val date: LocalDate,              // Gregorian date (yyyy-MM-dd)
    val salahName: String,            // "Fajr", "Dhuhr", "Asr", "Maghrib", "Isha"
    val rating: Int,                  // 1-10
    val notes: String?,               // Optional notes
    val loggedAt: LocalDateTime       // Timestamp when logged
)
