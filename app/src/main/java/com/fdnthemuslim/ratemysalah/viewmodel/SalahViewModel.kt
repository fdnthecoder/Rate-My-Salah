package com.fdnthemuslim.ratemysalah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fdnthemuslim.ratemysalah.data.entity.AppSettings
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.data.entity.PracticeLog
import com.fdnthemuslim.ratemysalah.data.repository.ISalahRepository
import com.fdnthemuslim.ratemysalah.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class SalahViewModel(private val repository: ISalahRepository) : ViewModel() {
    
    private val _salahsForToday = MutableStateFlow<List<SalahLog>>(emptyList())
    val salahsForToday: StateFlow<List<SalahLog>> = _salahsForToday.asStateFlow()
    
    private val _salahsForSelectedDate = MutableStateFlow<List<SalahLog>>(emptyList())
    val salahsForSelectedDate: StateFlow<List<SalahLog>> = _salahsForSelectedDate.asStateFlow()
    
    private val _salahsForMonth = MutableStateFlow<List<SalahLog>>(emptyList())
    val salahsForMonth: StateFlow<List<SalahLog>> = _salahsForMonth.asStateFlow()
    
    private val _allSalahs = MutableStateFlow<List<SalahLog>>(emptyList())
    val allSalahs: StateFlow<List<SalahLog>> = _allSalahs.asStateFlow()
    
    private val _practiceLogs = MutableStateFlow<List<PracticeLog>>(emptyList())
    val practiceLogs: StateFlow<List<PracticeLog>> = _practiceLogs.asStateFlow()
    
    private val _settings = MutableStateFlow<AppSettings?>(null)
    val settings: StateFlow<AppSettings?> = _settings.asStateFlow()
    
    private val _currentTime = MutableStateFlow(LocalDateTime.now())
    val currentTime: StateFlow<LocalDateTime> = _currentTime.asStateFlow()
    
    init {
        viewModelScope.launch {
            repository.getSettingsFlow().collect { settings ->
                _settings.value = settings ?: AppSettings()
                loadTodaySalahs()
            }
        }
        loadAllSalahs()
        loadPracticeLogs()
        startClock()
    }
    
    private fun startClock() {
        viewModelScope.launch {
            var lastDate = _currentTime.value.toLocalDate()
            while (true) {
                val now = LocalDateTime.now()
                _currentTime.value = now
                
                // If the day has changed, reload today's prayers
                if (now.toLocalDate() != lastDate) {
                    lastDate = now.toLocalDate()
                    loadTodaySalahs()
                }
                
                delay(1000)
            }
        }
    }
    
    // Salah operations
    fun loadTodaySalahs() {
        viewModelScope.launch {
            val today = LocalDate.now()
            val salahs = repository.getSalahsForDate(today)
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
        val today = LocalDate.now()
        if (date.isAfter(today)) return // Block saving for future dates
        
        viewModelScope.launch {
            // Find existing log to update if it exists
            val existingLogs = repository.getSalahsForDate(date)
            val existingLog = existingLogs.find { it.salahName == salahName }
            
            val salahLog = SalahLog(
                id = existingLog?.id ?: 0, // Keep same ID if updating
                date = date,
                salahName = salahName,
                rating = rating,
                notes = notes,
                loggedAt = LocalDateTime.now()
            )
            repository.insertSalahLog(salahLog)
            
            // Reload all relevant states IN THE SAME COROUTINE to ensure UI update
            val updatedToday = repository.getSalahsForDate(today)
            _salahsForToday.value = updatedToday
            
            val updatedSelected = repository.getSalahsForDate(date)
            _salahsForSelectedDate.value = updatedSelected
            
            val startDate = date.withDayOfMonth(1)
            val endDate = date.withDayOfMonth(date.lengthOfMonth())
            val updatedMonth = repository.getSalahsForMonth(startDate, endDate)
            _salahsForMonth.value = updatedMonth
        }
    }
    
    fun deleteSalahLog(salahLog: SalahLog) {
        viewModelScope.launch {
            repository.deleteSalahLog(salahLog)
            
            // Reload relevant states immediately
            loadTodaySalahs()
            loadSalahsForDate(salahLog.date)
            loadSalahsForMonth(salahLog.date)
        }
    }
    
    // Practice operations
    fun loadPracticeLogs() {
        viewModelScope.launch {
            repository.getAllPracticeLogs().collect { logs ->
                _practiceLogs.value = logs
            }
        }
    }
    
    fun savePracticeLog(rakat: Int, rating: Int, notes: String?) {
        viewModelScope.launch {
            val practiceLog = PracticeLog(
                date = LocalDate.now(),
                rakat = rakat,
                rating = rating,
                notes = notes,
                loggedAt = LocalDateTime.now()
            )
            repository.insertPracticeLog(practiceLog)
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
}
