package com.arzuozkan.mywalletapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository
import com.arzuozkan.mywalletapplication.ui.fragments.WalletFragment

class WalletFragmentViewModelFactory(
    private val repository: BankCardRepository,
    private val application: Application
    ): ViewModelProvider.Factory{
        @Suppress("unchecked_cast")
        override fun <T: ViewModel> create(modelClass: Class<T>): T{
            if(modelClass.isAssignableFrom(WalletFragmentViewModel::class.java))
                return WalletFragmentViewModel(repository,application) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }