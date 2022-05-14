package com.arzuozkan.mywalletapplication.data.repository

import androidx.annotation.WorkerThread
import com.arzuozkan.mywalletapplication.data.database.BankCard
import com.arzuozkan.mywalletapplication.data.database.BankCardDAO
import kotlinx.coroutines.flow.Flow


class BankCardRepository(private val cardDao: BankCardDAO){
    val allBankCard: Flow<List<BankCard>> = cardDao.allCards()

    @WorkerThread
    suspend fun insertBankCard(card:BankCard){
        cardDao.addCard(card)
    }
}
