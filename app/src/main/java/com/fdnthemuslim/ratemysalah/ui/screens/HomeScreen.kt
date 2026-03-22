package com.fdnthemuslim.ratemysalah.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.ui.components.RatingDialog
import com.fdnthemuslim.ratemysalah.ui.components.SalahCard
import com.fdnthemuslim.ratemysalah.utils.Constants
import com.fdnthemuslim.ratemysalah.utils.DateUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    todaySalahs: List<SalahLog>,
    currentTime: LocalDateTime,
    onSaveSalah: (LocalDate, String, Int, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedSalah by remember { mutableStateOf("") }
    var selectedSalahLog by remember { mutableStateOf<SalahLog?>(null) }
    
    val today = currentTime.toLocalDate()
    val timeFormatter = remember { DateTimeFormatter.ofPattern("hh:mm:ss a") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Today's Prayers")
                        Text(
                            text = currentTime.format(timeFormatter),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Motivational Hadith/Quote Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "\"The first thing for which a person will be brought to account on the Day of Resurrection will be his prayer...\"",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "You can't improve what you don't measure.",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }

            // Date Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = DateUtils.getGregorianDateFormatted(today),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = DateUtils.getHijriDate(today),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
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
                    onSaveSalah(today, selectedSalah, rating, notes)
                    showDialog = false
                }
            )
        }
    }
}
