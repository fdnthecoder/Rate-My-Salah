package com.fdnthemuslim.ratemysalah.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class DateUtilsTest {

    @Test
    fun `getIslamicDay returns same day before start hour`() {
        val dateTime = LocalDateTime.of(2026, 3, 21, 19, 0) // 7 PM
        val startHour = 20 // 8 PM
        val result = DateUtils.getIslamicDay(dateTime, startHour)
        assertEquals(LocalDate.of(2026, 3, 21), result)
    }

    @Test
    fun `getIslamicDay returns next day at start hour`() {
        val dateTime = LocalDateTime.of(2026, 3, 21, 20, 0) // 8 PM
        val startHour = 20 // 8 PM
        val result = DateUtils.getIslamicDay(dateTime, startHour)
        assertEquals(LocalDate.of(2026, 3, 22), result)
    }

    @Test
    fun `getIslamicDay returns next day after start hour`() {
        val dateTime = LocalDateTime.of(2026, 3, 21, 23, 0) // 11 PM
        val startHour = 20 // 8 PM
        val result = DateUtils.getIslamicDay(dateTime, startHour)
        assertEquals(LocalDate.of(2026, 3, 22), result)
    }

    @Test
    fun `formatHour formats correctly`() {
        assertEquals("12 AM", DateUtils.formatHour(0))
        assertEquals("8 AM", DateUtils.formatHour(8))
        assertEquals("12 PM", DateUtils.formatHour(12))
        assertEquals("8 PM", DateUtils.formatHour(20))
    }
}
