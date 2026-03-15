package com.fdnthemuslim.ratemysalah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdnthemuslim.ratemysalah.data.entity.AppSettings
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.data.repository.SalahRepository
import com.fdnthemuslim.ratemysalah.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class SalahViewModel(private val repository: SalahRepository) : ViewModel() {
    
    private val _salahsForToday = MutableStateFlow<List<SalahLog>>(emptyList())
    val salahsForToday: StateFlow<List<SalahLog>> = _salahsForToday.asStateFlow()
    
    private val _salahsForSelectedDate = MutableStateFlow<List<SalahLog>>(emptyList())
    val salahsForSelectedDate: StateFlow<List<SalahLog>> = _salahsForSelectedDate.asStateFlow()
    
    private val _salahsForMonth = MutableStateFlow<List<SalahLog>>(emptyList())
    val salahsForMonth: StateFlow<List<SalahLog>> = _salahsForMonth.asStateFlow()
    
    private val _allSalahs = MutableStateFlow<List<SalahLog>>(emptyList())
    val allSalahs: StateFlow<List<SalahLog>> = _allSalahs.asStateFlow()
    
    private val _settings = MutableStateFlow<AppSettings?>(null)
    val settings: StateFlow<AppSettings?> = _settings.asStateFlow()
    
    init {
        loadTodaySalahs()
        loadAllSalahs()
        loadSettings()
    }
    
    // Salah operations
    fun loadTodaySalahs() {
        viewModelScope.launch {
            val salahs = repository.getSalahsForDate(LocalDate.now())
            _salahsForToday.value = salahs
        }
    }
    
    fun loadSalahsForDate(date: LocalDate) {
        viewModelScope.launch {
            val salahs = repository.getSalahsForDate(date)
            _salahsForSelectedDate.value = salahs
        }
    }
    
    fun loadSalahsForMonth(date: LocalDate) {
        viewModelScope.launch {
            val startDate = date.withDayOfMonth(1)
            val endDate = date.withDayOfMonth(date.lengthOfMonth())
            val salahs = repository.getSalahsForMonth(startDate, endDate)
            _salahsForMonth.value = salahs
        }
    }
    
    fun loadAllSalahs() {
        viewModelScope.launch {
            repository.getAllSalahsFlow().collect { salahs ->
                _allSalahs.value = salahs
            }
        }
    }
    
    fun saveSalahLog(date: LocalDate, salahName: String, rating: Int, notes: String?) {
        viewModelScope.launch {
            val salahLog = SalahLog(
                date = date,
                salahName = salahName,
                rating = rating,
                notes = notes,
                loggedAt = LocalDateTime.now()
            )
            repository.insertSalahLog(salahLog)
            loadTodaySalahs()
        }
    }
    
    fun deleteSalahLog(salahLog: SalahLog) {
        viewModelScope.launch {
            repository.deleteSalahLog(salahLog)
            loadTodaySalahs()
        }
    }
    
    // Settings operations
    fun loadSettings() {
        viewModelScope.launch {
            repository.getSettingsFlow().collect { settings ->
                _settings.value = settings ?: AppSettings()
            }
        }
    }
    
    fun toggleDarkMode() {
        viewModelScope.launch {
            val currentSettings = _settings.value ?: AppSettings()
            val newSettings = currentSettings.copy(darkMode = !currentSettings.darkMode)
            repository.updateSettings(newSettings)
        }
    }
    
    // Statistics
    fun getAverageRatingBySalah(): Map<String, Double> {
        val salahs = _allSalahs.value
        return Constants.SALAH_NAMES.associateWith { salahName ->
            val salahLogs = salahs.filter { it.salahName == salahName }
            if (salahLogs.isEmpty()) 0.0
            else salahLogs.map { it.rating }.average()
        }
    }
    
    fun getOverallAverageRating(): Double {
        val salahs = _allSalahs.value
        return if (salahs.isEmpty()) 0.0
        else salahs.map { it.rating }.average()
    }
    
    fun getSalahCountForDate(date: LocalDate): Int {
        return _salahsForMonth.value.count { it.date == date }
    }
}
