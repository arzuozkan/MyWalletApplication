package com.arzuozkan.mywalletapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.*
import com.arzuozkan.mywalletapplication.data.database.BankCard
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository
import kotlinx.coroutines.launch

class SettingsFragmentViewModel(
    private val repository: BankCardRepository,
    application: Application):AndroidViewModel(application) {

    val allCard:LiveData<List<BankCard>> =repository.allBankCard.asLiveData()

    fun deleteAllCards(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}