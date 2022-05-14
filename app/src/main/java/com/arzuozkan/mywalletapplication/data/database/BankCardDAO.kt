package com.arzuozkan.mywalletapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BankCardDAO{

    @Insert
    suspend fun addCard(card:BankCard)

    @Query("SELECT * FROM bank_cards_table")
    fun allCards(): Flow<List<BankCard>>

}