package com.fdnthemuslim.ratemysalah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fdnthemuslim.ratemysalah.data.database.AppDatabase
import com.fdnthemuslim.ratemysalah.data.repository.SalahRepository
import com.fdnthemuslim.ratemysalah.data.repository.ISalahRepository
import com.fdnthemuslim.ratemysalah.navigation.NavGraph
import com.fdnthemuslim.ratemysalah.ui.theme.RateMyPrayerTheme
import com.fdnthemuslim.ratemysalah.viewmodel.SalahViewModel
import com.fdnthemuslim.ratemysalah.viewmodel.SalahViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var repository: ISalahRepository
    private lateinit var viewModel: SalahViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize database and repository
        database = AppDatabase.getDatabase(applicationContext)
        repository = SalahRepository(
            salahLogDao = database.salahLogDao(),
            appSettingsDao = database.appSettingsDao()
        )
        
        // Create ViewModel with factory
        val viewModelFactory = SalahViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SalahViewModel::class.java]
        
        setContent {
            // Collect state from ViewModel
            val todaySalahs by viewModel.salahsForToday.collectAsState()
            val salahsForSelectedDate by viewModel.salahsForSelectedDate.collectAsState()
            val salahsForMonth by viewModel.salahsForMonth.collectAsState()
            val settings by viewModel.settings.collectAsState()
            
            // Calculate stats
            val averageRatingBySalah = remember(todaySalahs, salahsForMonth) {
                viewModel.getAverageRatingBySalah()
            }
            val overallAverage = remember(todaySalahs, salahsForMonth) {
                viewModel.getOverallAverageRating()
            }
            
            val isDarkMode = settings?.darkMode ?: false
            
            RateMyPrayerTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(
                        todaySalahs = todaySalahs,
                        salahsForSelectedDate = salahsForSelectedDate,
                        salahsForMonth = salahsForMonth,
                        averageRatingBySalah = averageRatingBySalah,
                        overallAverage = overallAverage,
                        isDarkMode = isDarkMode,
                        onSaveSalah = { date, salahName, rating, notes ->
                            viewModel.saveSalahLog(date, salahName, rating, notes)
                        },
                        onLoadSalahsForDate = { date ->
                            viewModel.loadSalahsForDate(date)
                        },
                        onLoadSalahsForMonth = { yearMonth ->
                            viewModel.loadSalahsForMonth(yearMonth.atDay(1))
                        },
                        onToggleDarkMode = {
                            viewModel.toggleDarkMode()
                        }
                    )
                }
            }
        }
    }
}
