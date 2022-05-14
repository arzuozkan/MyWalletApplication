package com.arzuozkan.mywalletapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arzuozkan.mywalletapplication.data.database.BankCard
import com.arzuozkan.mywalletapplication.databinding.BankCardItemBinding

class BankCardRWAdapter:ListAdapter<BankCard,BankCardRWAdapter.BankCardItem>(DiffUtilCallback()) {

    class BankCardItem (val bankCardItemBinding: BankCardItemBinding)
        :RecyclerView.ViewHolder(bankCardItemBinding.root)

    //referenced link:https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding#4
    class DiffUtilCallback:DiffUtil.ItemCallback<BankCard>() {
        override fun areItemsTheSame(oldItem: BankCard, newItem: BankCard): Boolean {
            return oldItem.cardNumber == newItem.cardNumber
        }

        override fun areContentsTheSame(oldItem: BankCard, newItem: BankCard): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankCardItem {
        val layoutInflater=LayoutInflater.from(parent.context)
        val bankCardItemBinding=BankCardItemBinding.inflate(layoutInflater,parent,false)
        return BankCardItem(bankCardItemBinding)
    }

    override fun onBindViewHolder(holder: BankCardItem, position: Int) {

        val bankCard=getItem(position)
        holder.bankCardItemBinding.apply {
            cardNumberText.text=bankCard.cardNumber
            expireText.text=bankCard.expireDate
        }
    }


}