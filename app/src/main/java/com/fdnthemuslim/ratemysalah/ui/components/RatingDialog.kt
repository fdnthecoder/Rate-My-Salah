package com.fdnthemuslim.ratemysalah.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun RatingDialog(
    salahName: String,
    currentRating: Int?,
    currentNotes: String?,
    onDismiss: () -> Unit,
    onSave: (rating: Int, notes: String?) -> Unit
) {
    var rating by remember { mutableStateOf(currentRating?.toString() ?: "") }
    var notes by remember { mutableStateOf(currentNotes ?: "") }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Rate $salahName",
                    style = MaterialTheme.typography.headlineSmall
                )
                
                OutlinedTextField(
                    value = rating,
                    onValueChange = { 
                        if (it.isEmpty() || (it.toIntOrNull() != null && it.toInt() in 1..10)) {
                            rating = it
                        }
                    },
                    label = { Text("Rating (1-10)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val ratingInt = rating.toIntOrNull()
                            if (ratingInt != null && ratingInt in 1..10) {
                                onSave(ratingInt, notes.ifEmpty { null })
                            }
                        },
                        enabled = rating.toIntOrNull()?.let { it in 1..10 } == true
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
