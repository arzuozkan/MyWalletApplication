package com.arzuozkan.mywalletapplication.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arzuozkan.mywalletapplication.data.database.BankCard
import com.arzuozkan.mywalletapplication.data.database.BankCardDB
import com.arzuozkan.mywalletapplication.data.repository.BankCardRepository
import com.arzuozkan.mywalletapplication.databinding.FragmentAddCardBinding
import com.arzuozkan.mywalletapplication.ui.viewModel.AddCardViewModel
import com.arzuozkan.mywalletapplication.ui.viewModel.AddCardViewModelFactory
import com.huawei.hms.mlplugin.card.bcr.*


class AddCardFragment : Fragment() {
    private val LOGTAG ="AddCardFragment"
    private lateinit var binding: FragmentAddCardBinding
    private lateinit var viewModel: AddCardViewModel
    private lateinit var db:BankCardDB
    private lateinit var newBankCard:BankCard

    //referenced code related to request permissions: https://github.com/blackzshaik/ActivityResultAPISample/blob/request-permission/app/src/main/java/com/madtutorial/activityresult/HomeFragment.kt
    private val permissionArray = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val requestPermissions=
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissionGranted ->
            if(permissionGranted.isNotEmpty()){
                if (permissionGranted[permissionArray[0]]!! && permissionGranted[permissionArray[1]]!!){
                    Log.e(LOGTAG,"permissions granted")

                }else{
                    Log.e(LOGTAG,"denied permissions")
                }

            }

        }
    //Referenced link address:https://forums.developer.huawei.com/forumPortal/en/topicview?tid=0201261566832050274&fid=0101187876626530001
    private val callbackObj:MLBcrCapture.Callback=object :MLBcrCapture.Callback{
        //if it scans successfully, onSuccess method runs
        override fun onSuccess(p0: MLBcrCaptureResult) {
            newBankCard=BankCard(
                cardNumber = setCardFormat(p0.number),
                expireDate = p0.expire
            )
            binding.cardItem.apply {
                bankCardItem.visibility=View.VISIBLE
                cardNumberText.text=newBankCard.cardNumber
                expireText.text=newBankCard.expireDate
            }
            binding.addCardButton.visibility=View.VISIBLE
        }

        override fun onCanceled() {
            TODO("Not yet implemented")
        }

        override fun onFailure(p0: Int, p1: Bitmap?) {
            TODO("Not yet implemented")
        }

        override fun onDenied() {
            TODO("Not yet implemented")
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db=BankCardDB.getCardsDB(requireContext())!!
        val application = requireNotNull(this.activity).application
        val repository=BankCardRepository(db.bankCardDao)
        //viewModel declaration
        val viewModelFactory=AddCardViewModelFactory(repository,application)
        viewModel=viewModelFactory.let {
            ViewModelProvider(this,it)[AddCardViewModel::class.java]
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentAddCardBinding.inflate(inflater,container,false)
        //application declaration
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //scanning the credit card
        binding.apply {
            scanCardButton.setOnClickListener {
                Toast.makeText(requireContext(), "Scanning starting", Toast.LENGTH_SHORT).show()
                checkPermissions()
            }
            addCardButton.setOnClickListener {
                viewModel.addCard(newBankCard)
                //findNavController().navigate(AddCardFragmentDirections.actionAddCardFragmentToWalletFragment())
                Toast.makeText(
                    requireContext(),
                    "The bank card is added to your wallet successfully",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
    }
    //Referenced link address:https://forums.developer.huawei.com/forumPortal/en/topicview?tid=0201261566832050274&fid=0101187876626530001
    private fun startCaptureActivity(callback: MLBcrCapture.Callback) {
        val config =
            MLBcrCaptureConfig.Factory()
                .setResultType(MLBcrCaptureConfig.RESULT_SIMPLE)
                .setOrientation(MLBcrCaptureConfig.ORIENTATION_AUTO)
                .create()
        val bankCapture = MLBcrCaptureFactory.getInstance().getBcrCapture(config)
        bankCapture.captureFrame(requireContext(),callback)

    }
    //referenced codes related to request permission: https://github.com/blackzshaik/ActivityResultAPISample/blob/request-permission/app/src/main/java/com/madtutorial/activityresult/HomeFragment.kt
   private fun checkPermissions() {
        if (isAllPermissionGranted()) {
            Log.e(LOGTAG,"Permission Already Granted")
            try {
                startCaptureActivity(callbackObj)
            } catch (e: Exception) {
                Log.e(LOGTAG, "MLBcrCapture captureFrame Exception : " + e.message, e)
                Toast.makeText(
                    requireContext(),
                    "MLBcrCapture captureFrame Exception :" + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            if (requireActivity().shouldShowRequestPermissionRationale(permissionArray[0])
                || requireActivity().shouldShowRequestPermissionRationale(permissionArray[1])
                /*|| requireActivity().shouldShowRequestPermissionRationale(permissionArray[2])*/
            ) {
                Toast.makeText(
                    requireContext(),
                    "You need to granted required permissions!",
                    Toast.LENGTH_LONG
                ).show()

                requestPermissions.launch(permissionArray)
            } else {
                requestPermissions.launch(permissionArray)
            }
        }
    }

    private fun isAllPermissionGranted(): Boolean {
        permissionArray.forEach {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    it
                ) == PackageManager.PERMISSION_DENIED
            ) {
                return false
            }
        }
        return true
    }
    private fun setCardFormat(number: String): String {
        var formatted=""
        var index=0
        while (index<number.length){
            formatted+= number.substring(index,index+4)+" "
            index+=4
        }
        return formatted
    }
}