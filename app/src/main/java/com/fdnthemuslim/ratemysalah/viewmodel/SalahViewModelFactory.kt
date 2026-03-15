package com.fdnthemuslim.ratemysalah.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fdnthemuslim.ratemysalah.data.repository.SalahRepository

class SalahViewModelFactory(
    private val repository: SalahRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalahViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SalahViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
