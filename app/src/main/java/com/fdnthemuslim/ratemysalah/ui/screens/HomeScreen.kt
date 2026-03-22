package com.fdnthemuslim.ratemysalah.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.ui.components.RatingDialog
import com.fdnthemuslim.ratemysalah.ui.components.SalahCard
import com.fdnthemuslim.ratemysalah.utils.Constants
import com.fdnthemuslim.ratemysalah.utils.DateUtils
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    todaySalahs: List<SalahLog>,
    onSaveSalah: (LocalDate, String, Int, String?) -> Unit,
    dayStartTime: Int,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedSalah by remember { mutableStateOf("") }
    var selectedSalahLog by remember { mutableStateOf<SalahLog?>(null) }
    
    val currentIslamicDay = remember(dayStartTime) {
        DateUtils.getIslamicDay(LocalDateTime.now(), dayStartTime)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Today's Prayers")
                        Text(
                            text = DateUtils.getGregorianDateFormatted(currentIslamicDay),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Hijri Date
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = DateUtils.getHijriDate(currentIslamicDay),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Islamic day starts at ${DateUtils.formatHour(dayStartTime)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Salah Cards
            Constants.SALAH_NAMES.forEach { salahName ->
                val existingLog = todaySalahs.find { it.salahName == salahName }
                
                SalahCard(
                    salahName = salahName,
                    salahLog = existingLog,
                    onRateClick = {
                        selectedSalah = salahName
                        selectedSalahLog = existingLog
                        showDialog = true
                    }
                )
            }
        }
        
        if (showDialog) {
            RatingDialog(
                salahName = selectedSalah,
                currentRating = selectedSalahLog?.rating,
                currentNotes = selectedSalahLog?.notes,
                onDismiss = { showDialog = false },
                onSave = { rating, notes ->
                    onSaveSalah(currentIslamicDay, selectedSalah, rating, notes)
                    showDialog = false
                }
            )
        }
    }
}
