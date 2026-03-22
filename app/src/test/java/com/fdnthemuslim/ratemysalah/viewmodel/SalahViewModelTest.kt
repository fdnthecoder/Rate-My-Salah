package com.fdnthemuslim.ratemysalah.viewmodel

import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import kotlinx.coroutines.test.advanceUntilIdle
import java.time.LocalDateTime


class SalahViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `cannot save salah log for future date`() = runTest {
        val repo = FakeSalahRepository()
        val vm = SalahViewModel(repo)
        this.advanceUntilIdle()
        
        val futureDate = LocalDate.now().plusDays(1)
        
        vm.saveSalahLog(futureDate, "Fajr", 8, "Test")
        this.advanceUntilIdle()
        assertEquals(0, repo.saved.size)
    }

    @Test
    fun `can save salah log for today and updates todaySalahs flow`() = runTest {
        val repo = FakeSalahRepository()
        val vm = SalahViewModel(repo)
        this.advanceUntilIdle()
        
        val today = LocalDate.now()
        vm.saveSalahLog(today, "Fajr", 8, "Test")
        this.advanceUntilIdle()
        
        assertEquals(1, repo.saved.size)
        assertEquals(1, vm.salahsForToday.value.size)
        assertEquals("Fajr", vm.salahsForToday.value[0].salahName)
    }

    @Test
    fun `can save salah log for past date and updates selectedDate and month flows`() = runTest {
        val repo = FakeSalahRepository()
        val vm = SalahViewModel(repo)
        val past = LocalDate.now().minusDays(5)
        
        // Initial load
        vm.loadSalahsForDate(past)
        vm.loadSalahsForMonth(past)
        this.advanceUntilIdle()
        
        vm.saveSalahLog(past, "Asr", 7, "Late")
        this.advanceUntilIdle()
        
        assertEquals(1, repo.saved.size)
        assertEquals(1, vm.salahsForSelectedDate.value.size)
        assertEquals("Asr", vm.salahsForSelectedDate.value[0].salahName)
        assertEquals(1, vm.salahsForMonth.value.size)
    }

    @Test
    fun `updates existing log instead of creating new one for same date and name`() = runTest {
        val repo = FakeSalahRepository()
        val vm = SalahViewModel(repo)
        val today = LocalDate.now()
        
        vm.saveSalahLog(today, "Fajr", 8, "Initial")
        this.advanceUntilIdle()
        assertEquals(1, repo.saved.size)
        assertEquals(8, repo.saved[0].rating)
        
        vm.saveSalahLog(today, "Fajr", 9, "Updated")
        this.advanceUntilIdle()
        
        // Should still be only 1 entry in repo.saved because FakeSalahRepository should handle updates
        // Note: FakeSalahRepository implementation needs to support this for the test to be valid
        assertEquals(1, repo.saved.size)
        assertEquals(9, repo.saved[0].rating)
        assertEquals("Updated", repo.saved[0].notes)
    }
}
