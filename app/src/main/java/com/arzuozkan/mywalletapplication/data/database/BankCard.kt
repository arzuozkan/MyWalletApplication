package com.arzuozkan.mywalletapplication.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "bank_cards_table")
data class BankCard(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id:Int=0,

    @ColumnInfo(name = "card_number")
    var cardNumber:String,

    @ColumnInfo(name = "expire_date")
    var expireDate:String

):Serializable
