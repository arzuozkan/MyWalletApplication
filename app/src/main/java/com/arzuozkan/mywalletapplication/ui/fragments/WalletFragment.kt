package com.arzuozkan.mywalletapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arzuozkan.mywalletapplication.R
import com.arzuozkan.mywalletapplication.data.database.BankCard
import com.arzuozkan.mywalletapplication.data.database.BankCardDB
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository
import com.arzuozkan.mywalletapplication.databinding.FragmentWalletBinding
import com.arzuozkan.mywalletapplication.ui.adapter.BankCardRWAdapter
import com.arzuozkan.mywalletapplication.ui.viewModel.WalletFragmentViewModel
import com.arzuozkan.mywalletapplication.ui.viewModel.WalletFragmentViewModelFactory
import kotlinx.coroutines.flow.Flow

class WalletFragment : Fragment() {
    private lateinit var binding: FragmentWalletBinding
    private lateinit var cardDB: BankCardDB
    private lateinit var repository: BankCardRepository
    private lateinit var viewModel:WalletFragmentViewModel
    private lateinit var cardList: List<BankCard>
    private lateinit var adapter:BankCardRWAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardDB=BankCardDB.getCardsDB(requireContext())!!
        repository=BankCardRepository(cardDB.bankCardDao)
        val application = requireNotNull(this.activity).application
        val viewModelFactory=WalletFragmentViewModelFactory(repository,application)
        viewModel=viewModelFactory.let {
            ViewModelProvider(this,it)[WalletFragmentViewModel::class.java]
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentWalletBinding.inflate(inflater,container,false)

        viewModel.cardList.observe(viewLifecycleOwner){ bankCardList ->
            adapter= BankCardRWAdapter()
            binding.recyclerView.adapter=adapter
            bankCardList.let{
                adapter.submitList(it)
            }

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.cardList.observe(viewLifecycleOwner){
            cardList=it
            if(cardList.isEmpty()){
                binding.status.visibility=View.VISIBLE
                binding.recyclerView.visibility=View.GONE
            }
            else{
                binding.apply {
                    status.visibility=View.GONE
                    recyclerView.visibility=View.VISIBLE
                    recyclerView.layoutManager =
                        LinearLayoutManager(context,LinearLayoutManager.VERTICAL,true)
                    recyclerView.setHasFixedSize(true)
                }

            }
        }

    }

}