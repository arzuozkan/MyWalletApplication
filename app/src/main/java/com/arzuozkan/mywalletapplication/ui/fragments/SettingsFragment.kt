package com.arzuozkan.mywalletapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.arzuozkan.mywalletapplication.R
import com.arzuozkan.mywalletapplication.data.database.BankCardDB
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository
import com.arzuozkan.mywalletapplication.databinding.FragmentSettingsBinding
import com.arzuozkan.mywalletapplication.ui.viewModel.SettingsFragmentViewModel
import com.arzuozkan.mywalletapplication.ui.viewModel.SettingsFragmentViewModelFactory

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var db: BankCardDB
    private lateinit var viewModel: SettingsFragmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db=BankCardDB.getCardsDB(requireContext())!!
        val application = requireNotNull(this.activity).application
        val repository= BankCardRepository(db.bankCardDao)

        val viewModelFactory=SettingsFragmentViewModelFactory(repository,application)
        viewModel=viewModelFactory.let {
            ViewModelProvider(this,it)[SettingsFragmentViewModel::class.java]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deleteCards.setOnClickListener {
            viewModel.deleteAllCards()
            Toast.makeText(
                requireContext(),
                "Saved bank cards are deleted successfully",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}