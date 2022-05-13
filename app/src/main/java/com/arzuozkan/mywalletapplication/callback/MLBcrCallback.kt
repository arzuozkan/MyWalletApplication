package com.arzuozkan.mywalletapplication.callback

import android.graphics.Bitmap
import android.util.Log
import com.huawei.hms.mlplugin.card.bcr.MLBcrCapture
import com.huawei.hms.mlplugin.card.bcr.MLBcrCaptureResult

class MLBcrCallback{
    companion object Callback:MLBcrCapture.Callback {
        override fun onSuccess(p0: MLBcrCaptureResult?) {
            var bankCardNumber: String = p0?.number ?: "yokyoy"
            //bankCardrecognitionViewModel.cardNumber.value=cardNumber
            Log.e("BANKCARDNUMARA",bankCardNumber)
        }

        override fun onCanceled() {

        }

        override fun onFailure(p0: Int, p1: Bitmap?) {

        }

        override fun onDenied() {

        }
    }

}