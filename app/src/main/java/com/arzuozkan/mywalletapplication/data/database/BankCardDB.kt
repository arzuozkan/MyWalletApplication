package com.arzuozkan.mywalletapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BankCard::class], version = 1, exportSchema = false)

abstract class BankCardDB:RoomDatabase() {
    abstract val bankCardDao:BankCardDAO

    companion object{
        @Volatile
        private var INSTANCE:BankCardDB?=null

        fun getCardsDB(context: Context):BankCardDB?{
            synchronized(this){
                var instance= INSTANCE
                if(instance==null)
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        BankCardDB::class.java,
                        "bank_card_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                INSTANCE=instance
            }
            return INSTANCE
        }
    }
}