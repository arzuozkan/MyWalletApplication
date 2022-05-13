package com.arzuozkan.mywalletapplication.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arzuozkan.mywalletapplication.callback.MLBcrCallback
import com.arzuozkan.mywalletapplication.databinding.FragmentAddCardBinding
import com.huawei.hms.mlplugin.card.bcr.CustomView
import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureConfig
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureFactory

class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding

    //referenced code related to request permissions: https://github.com/blackzshaik/ActivityResultAPISample/blob/request-permission/app/src/main/java/com/madtutorial/activityresult/HomeFragment.kt
    private val permissionArray = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private val requestPermissions=
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissionGranted ->
            /*permissionGranted.entries.forEach {
                Log.e("ADDCARDFRS", "${it.key} = ${it.value}")
            }*/
            if(permissionGranted.isNotEmpty()){
                if (permissionGranted[permissionArray[0]]!! && permissionGranted[permissionArray[1]]!! /*&& permissionGranted[permissionArray[2]]!!*/){
                    Log.e("ADDCARDFRS","permissions granted")

                }else{
                    Log.e("ADDCARDFRS","denied permissions")
                }

            }

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAddCardBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //scanning the credit card
        binding.scanCardButton.setOnClickListener {
            Toast.makeText(requireContext(), "Scanning starting", Toast.LENGTH_SHORT).show()
            checkPermissions()

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
    //referenced code related to request permissions: https://github.com/blackzshaik/ActivityResultAPISample/blob/request-permission/app/src/main/java/com/madtutorial/activityresult/HomeFragment.kt
   private fun checkPermissions() {
        if (isAllPermissionGranted()) {
            Log.e("ADDCARDFRS","Permission Already Granted")
            try {
                Log.e("ADDCARDFRS","try-catch içi")
                startCaptureActivity(MLBcrCallback.Callback)
                /*val resultCallback=CustomView.OnBcrResultCallback{card->
                    if(card.errorCode==0){
                        binding.cardNumber
                    }else{

                    }
                }*/
            } catch (e: Exception) {
                Log.e("ADDCARDFRS", "MLBcrCapture captureFrame Exception : " + e.message, e)
            }
        } else {
            if (requireActivity().shouldShowRequestPermissionRationale(permissionArray[0])
                || requireActivity().shouldShowRequestPermissionRationale(permissionArray[1])
                /*|| requireActivity().shouldShowRequestPermissionRationale(permissionArray[2])*/
            ) {
                Toast.makeText(
                    requireContext(),
                    "In order to demonstrate the success scenario we need you to allow access to the permission",
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
}