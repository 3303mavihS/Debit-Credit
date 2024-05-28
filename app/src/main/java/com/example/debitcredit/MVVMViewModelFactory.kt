package com.example.debitcredit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MVVMViewModelFactory(
    private val dataRepo: DataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BridgeViewModel(dataRepo) as T
    }
}