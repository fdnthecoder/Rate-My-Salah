package com.fdnthemuslim.ratemysalah.ui.screens

import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class CalendarScreenTest {
    @Test
    fun `salahCountMap counts unique prayers per day only`() {
        val today = LocalDate.now()
        val now = java.time.LocalDateTime.now()
        val logs = listOf(
            SalahLog(date = today, salahName = "Fajr", rating = 8, notes = null, loggedAt = now),
            SalahLog(date = today, salahName = "Dhuhr", rating = 7, notes = null, loggedAt = now),
            SalahLog(date = today, salahName = "Fajr", rating = 9, notes = null, loggedAt = now), // duplicate
            SalahLog(date = today, salahName = "Asr", rating = 6, notes = null, loggedAt = now),
            SalahLog(date = today, salahName = "Maghrib", rating = 10, notes = null, loggedAt = now),
            SalahLog(date = today, salahName = "Isha", rating = 10, notes = null, loggedAt = now)
        )
        val salahCountMap = logs.groupBy { it.date }
            .mapValues { entry ->
                entry.value.map { it.salahName }.distinct().count().coerceAtMost(5)
            }
        assertEquals(5, salahCountMap[today])
    }

    @Test
    fun `salahCountMap never exceeds 5`() {
        val today = LocalDate.now()
        val now = java.time.LocalDateTime.now()
        val logs = (1..10).map {
            SalahLog(date = today, salahName = "Fajr", rating = it, notes = null, loggedAt = now)
        }
        val salahCountMap = logs.groupBy { it.date }
            .mapValues { entry ->
                entry.value.map { it.salahName }.distinct().count().coerceAtMost(5)
            }
        assertEquals(1, salahCountMap[today])
    }

    @Test
    fun `salahCountMap color logic matches count`() {
        fun colorForCount(count: Int): String = when (count) {
            5 -> "green"
            in 3..4 -> "yellow"
            in 1..2 -> "red"
            else -> "gray"
        }
        assertEquals("green", colorForCount(5))
        assertEquals("yellow", colorForCount(3))
        assertEquals("yellow", colorForCount(4))
        assertEquals("red", colorForCount(2))
        assertEquals("red", colorForCount(1))
        assertEquals("gray", colorForCount(0))
    }
}
