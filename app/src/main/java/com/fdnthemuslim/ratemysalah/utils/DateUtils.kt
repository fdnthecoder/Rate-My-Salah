package com.fdnthemuslim.ratemysalah.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter

object DateUtils {
    /**
     * Determines the "Islamic day" based on a start hour (e.g., 20 for 8 PM).
     * If the current time is at or after startHour, it's considered the next day.
     */
    fun getIslamicDay(dateTime: LocalDateTime, startHour: Int): LocalDate {
        return if (dateTime.hour >= startHour) {
            dateTime.toLocalDate().plusDays(1)
        } else {
            dateTime.toLocalDate()
        }
    }

    fun formatHour(hour: Int): String {
        val amPm = if (hour < 12) "AM" else "PM"
        val displayHour = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        return "$displayHour $amPm"
    }
    
    fun getHijriDate(gregorianDate: LocalDate): String {
        val hijrahDate = HijrahDate.from(gregorianDate)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return hijrahDate.format(formatter)
    }
    
    fun getGregorianDateFormatted(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")
        return date.format(formatter)
    }
    
    fun getMonthStart(date: LocalDate): LocalDate {
        return date.withDayOfMonth(1)
    }
    
    fun getMonthEnd(date: LocalDate): LocalDate {
        return date.withDayOfMonth(date.lengthOfMonth())
    }
}
