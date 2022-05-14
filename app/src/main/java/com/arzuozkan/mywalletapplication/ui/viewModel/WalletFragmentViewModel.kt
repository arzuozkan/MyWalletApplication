package com.arzuozkan.mywalletapplication.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.arzuozkan.mywalletapplication.data.database.BankCard
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository
import kotlinx.coroutines.flow.Flow

class WalletFragmentViewModel(
    repository: BankCardRepository,
    application: Application
):AndroidViewModel(application) {

   val cardList: LiveData<List<BankCard>> = repository.allBankCard.asLiveData()


}