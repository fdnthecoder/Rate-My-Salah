package com.fdnthemuslim.ratemysalah.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.ui.components.CalendarGrid
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    salahsForMonth: List<SalahLog>,
    onDateClick: (LocalDate) -> Unit,
    onMonthChange: (YearMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    
    // Calculate unique salah counts per day (max 5)
    val salahCountMap = remember(salahsForMonth) {
        salahsForMonth.groupBy { it.date }
            .mapValues { entry ->
                entry.value.map { it.salahName }.distinct().count().coerceAtMost(5)
            }
    }
    
    LaunchedEffect(currentMonth) {
        onMonthChange(currentMonth)
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Month Navigation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { 
                    currentMonth = currentMonth.minusMonths(1)
                }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month")
                }
                
                Text(
                    text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    style = MaterialTheme.typography.titleLarge
                )
                
                IconButton(onClick = { 
                    currentMonth = currentMonth.plusMonths(1)
                }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Calendar Grid
            CalendarGrid(
                currentMonth = currentMonth,
                salahCountMap = salahCountMap,
                onDateClick = onDateClick
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Legend
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Legend",
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text("🟢 Green: 5 prayers", style = MaterialTheme.typography.bodySmall)
                    Text("🟡 Yellow: 3-4 prayers", style = MaterialTheme.typography.bodySmall)
                    Text("🔴 Red: 1-2 prayers", style = MaterialTheme.typography.bodySmall)
                    Text("⚪ Gray: 0 prayers", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
