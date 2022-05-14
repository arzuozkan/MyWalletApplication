package com.arzuozkan.mywalletapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arzuozkan.mywalletapplication.data.database.BankCard
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository
import kotlinx.coroutines.launch

class AddCardViewModel(
    private val repository: BankCardRepository,
    application: Application)
    :AndroidViewModel(application) {

    //val allCard:LiveData<List<BankCard>> = repository.allBankCard.asLiveData()

     fun addCard(card:BankCard){
        viewModelScope.launch {
            repository.insertBankCard(card)
        }
    }


}