package com.fdnthemuslim.ratemysalah.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fdnthemuslim.ratemysalah.utils.Constants
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarGrid(
    currentMonth: YearMonth,
    salahCountMap: Map<LocalDate, Int>,
    onDateClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Day of week headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Calendar grid
        val firstDayOfMonth = currentMonth.atDay(1)
        val lastDayOfMonth = currentMonth.atEndOfMonth()
        val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
        val daysInMonth = currentMonth.lengthOfMonth()
        
        // Create list of all cells (including empty ones for alignment)
        val calendarDays = mutableListOf<LocalDate?>()
        repeat(firstDayOfWeek) { calendarDays.add(null) }
        repeat(daysInMonth) { calendarDays.add(firstDayOfMonth.plusDays(it.toLong())) }
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp)
        ) {
            items(calendarDays) { date ->
                if (date != null) {
                    val isFuture = date.isAfter(LocalDate.now())
                    CalendarDay(
                        date = date,
                        salahCount = salahCountMap[date] ?: 0,
                        isToday = date == LocalDate.now(),
                        isFuture = isFuture,
                        onClick = { if (!isFuture) onDateClick(date) }
                    )
                } else {
                    Box(modifier = Modifier.aspectRatio(1f))
                }
            }
        }
    }
}

@Composable
private fun CalendarDay(
    date: LocalDate,
    salahCount: Int,
    isToday: Boolean,
    isFuture: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when (salahCount) {
        5 -> Color(Constants.COLOR_GREEN)
        in 3..4 -> Color(Constants.COLOR_YELLOW)
        in 1..2 -> Color(Constants.COLOR_RED)
        else -> Color(Constants.COLOR_GRAY)
    }
    
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .alpha(if (isFuture) 0.5f else 1f)
            .background(
                color = backgroundColor.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.small
            )
            .clickable(enabled = !isFuture, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
            )
            if (salahCount > 0) {
                Text(
                    text = "$salahCount",
                    style = MaterialTheme.typography.labelSmall,
                    color = backgroundColor
                )
            }
        }
    }
}
