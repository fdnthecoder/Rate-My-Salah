package com.fdnthemuslim.ratemysalah.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.ui.theme.RateMyPrayerTheme
import java.time.LocalDate
import java.time.LocalDateTime

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val mockSalahs = listOf(
        SalahLog(1, LocalDate.now(), "Fajr", 8, "Good focus", LocalDateTime.now()),
        SalahLog(2, LocalDate.now(), "Dhuhr", 7, null, LocalDateTime.now())
    )
    RateMyPrayerTheme {
        HomeScreen(
            todaySalahs = mockSalahs,
            onSaveSalah = { _, _, _, _ -> },
            dayStartTime = 20
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatsScreenPreview() {
    val mockAverages = mapOf(
        "Fajr" to 8.5,
        "Dhuhr" to 7.2,
        "Asr" to 8.0,
        "Maghrib" to 9.1,
        "Isha" to 7.8
    )
    RateMyPrayerTheme {
        StatsScreen(
            averageRatingBySalah = mockAverages,
            overallAverage = 8.1
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    RateMyPrayerTheme {
        SettingsScreen(
            isDarkMode = false,
            onToggleDarkMode = {},
            dayStartTime = 20,
            onUpdateDayStartTime = {}
        )
    }
}
