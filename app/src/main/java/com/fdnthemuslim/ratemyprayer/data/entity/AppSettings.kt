package com.fdnthemuslim.ratemyprayer.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_settings")
data class AppSettings(
    @PrimaryKey 
    val id: Int = 1,
    val darkMode: Boolean = false
)
