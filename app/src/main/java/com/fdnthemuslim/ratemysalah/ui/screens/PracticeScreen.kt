package com.fdnthemuslim.ratemysalah.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fdnthemuslim.ratemysalah.data.entity.PracticeLog
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(
    practiceLogs: List<PracticeLog>,
    onSavePractice: (Int, Int, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Practice Salah") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Practice")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Khushu Tips Card
            item {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Tips for Khushu (Concentration)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        val tips = listOf(
                            "• Understand what you are reciting.",
                            "• Look at the place of prostration.",
                            "• Pray as if it is your last prayer.",
                            "• Avoid distractions before starting.",
                            "• Move slowly and calmly between positions."
                        )
                        tips.forEach { tip ->
                            Text(
                                text = tip,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }
                    }
                }
            }
            
            item {
                Text(
                    text = "Your Recent Practice",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            if (practiceLogs.isEmpty()) {
                item {
                    Text(
                        text = "No practice sessions logged yet. Tap + to start!",
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            items(practiceLogs) { log ->
                PracticeCard(log)
            }
        }
        
        if (showDialog) {
            PracticeDialog(
                onDismiss = { showDialog = false },
                onSave = { rakat, rating, notes ->
                    onSavePractice(rakat, rating, notes)
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun PracticeCard(log: PracticeLog) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${log.rakat} Rakat Practice",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = log.date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Rating: ${log.rating}/10",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            if (!log.notes.isNullOrEmpty()) {
                Text(
                    text = log.notes,
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun PracticeDialog(
    onDismiss: () -> Unit,
    onSave: (rakat: Int, rating: Int, notes: String?) -> Unit
) {
    var rakat by remember { mutableStateOf(2) }
    var rating by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Log Practice Salah") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text("How many Rakat?")
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    FilterChip(
                        selected = rakat == 2,
                        onClick = { rakat = 2 },
                        label = { Text("2 Rakat") }
                    )
                    FilterChip(
                        selected = rakat == 4,
                        onClick = { rakat = 4 },
                        label = { Text("4 Rakat") }
                    )
                }
                
                OutlinedTextField(
                    value = rating,
                    onValueChange = { 
                        if (it.isEmpty() || (it.toIntOrNull() != null && it.toInt() in 1..10)) {
                            rating = it
                        }
                    },
                    label = { Text("Rating (1-10)") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    rating.toIntOrNull()?.let { onSave(rakat, it, notes.ifEmpty { null }) }
                },
                enabled = rating.isNotEmpty()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
