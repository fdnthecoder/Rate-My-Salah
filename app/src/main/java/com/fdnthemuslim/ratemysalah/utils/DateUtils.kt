package com.fdnthemuslim.ratemysalah.utils

import java.time.LocalDate
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter

object DateUtils {
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
