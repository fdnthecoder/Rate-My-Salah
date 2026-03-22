package com.fdnthemuslim.ratemysalah.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class DateUtilsTest {

    @Test
    fun `getMonthStart returns first day of month`() {
        val date = LocalDate.of(2026, 3, 21)
        val result = DateUtils.getMonthStart(date)
        assertEquals(LocalDate.of(2026, 3, 1), result)
    }

    @Test
    fun `getMonthEnd returns last day of month`() {
        val date = LocalDate.of(2026, 3, 21)
        val result = DateUtils.getMonthEnd(date)
        assertEquals(LocalDate.of(2026, 3, 31), result)
    }
}
