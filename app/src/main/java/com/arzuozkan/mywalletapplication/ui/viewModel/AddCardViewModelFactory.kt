package com.arzuozkan.mywalletapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddCardViewModelFactory(
    private val application: Application
): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(AddCardViewModel::class.java))
            return AddCardViewModel(application) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}