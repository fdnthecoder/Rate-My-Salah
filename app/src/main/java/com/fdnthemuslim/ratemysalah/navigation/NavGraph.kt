package com.fdnthemuslim.ratemysalah.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fdnthemuslim.ratemysalah.data.entity.SalahLog
import com.fdnthemuslim.ratemysalah.ui.components.BottomNavBar
import com.fdnthemuslim.ratemysalah.ui.screens.*
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun NavGraph(
    todaySalahs: List<SalahLog>,
    salahsForSelectedDate: List<SalahLog>,
    salahsForMonth: List<SalahLog>,
    averageRatingBySalah: Map<String, Double>,
    overallAverage: Double,
    isDarkMode: Boolean,
    onSaveSalah: (LocalDate, String, Int, String?) -> Unit,
    onLoadSalahsForDate: (LocalDate) -> Unit,
    onLoadSalahsForMonth: (YearMonth) -> Unit,
    onToggleDarkMode: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"
    
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    
    Scaffold(
        bottomBar = {
            // Show bottom nav only on main screens, not on DayDetailScreen
            if (!currentRoute.startsWith("dayDetail")) {
                BottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(
                    todaySalahs = todaySalahs,
                    onSaveSalah = onSaveSalah
                )
            }
            
            composable("calendar") {
                CalendarScreen(
                    salahsForMonth = salahsForMonth,
                    onDateClick = { date ->
                        selectedDate = date
                        onLoadSalahsForDate(date)
                        navController.navigate("dayDetail/${date}")
                    },
                    onMonthChange = { yearMonth ->
                        onLoadSalahsForMonth(yearMonth)
                    }
                )
            }
            
            composable(
                route = "dayDetail/{date}",
                arguments = listOf(navArgument("date") { type = NavType.StringType })
            ) { backStackEntry ->
                val dateString = backStackEntry.arguments?.getString("date")
                val date = dateString?.let { LocalDate.parse(it) } ?: LocalDate.now()
                
                DayDetailScreen(
                    selectedDate = date,
                    salahsForDate = salahsForSelectedDate,
                    onSaveSalah = onSaveSalah,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            
            composable("stats") {
                StatsScreen(
                    averageRatingBySalah = averageRatingBySalah,
                    overallAverage = overallAverage
                )
            }
            
            composable("settings") {
                SettingsScreen(
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = onToggleDarkMode
                )
            }
        }
    }
}
