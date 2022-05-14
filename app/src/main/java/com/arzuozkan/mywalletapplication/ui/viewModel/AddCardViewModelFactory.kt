package com.arzuozkan.mywalletapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository

class AddCardViewModelFactory(
    private val repository: BankCardRepository,
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(AddCardViewModel::class.java))
            return AddCardViewModel(repository,application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}