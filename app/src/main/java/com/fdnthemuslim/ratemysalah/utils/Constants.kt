package com.fdnthemuslim.ratemysalah.utils

object Constants {
    val SALAH_NAMES = listOf("Fajr", "Dhuhr", "Asr", "Maghrib", "Isha")
    
    // Calendar color coding
    const val COLOR_GREEN = 0xFF4CAF50L  // 5 salahs logged
    const val COLOR_YELLOW = 0xFFFFC107L // 3-4 salahs logged
    const val COLOR_RED = 0xFFF44336L    // 1-2 salahs logged
    const val COLOR_GRAY = 0xFF9E9E9EL   // 0 salahs logged
}
