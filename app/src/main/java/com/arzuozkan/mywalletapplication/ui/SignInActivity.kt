package com.arzuozkan.mywalletapplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arzuozkan.mywalletapplication.R
import com.arzuozkan.mywalletapplication.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent= Intent(this,MainActivity::class.java)

        binding.huaweiIdAuthButton.setOnClickListener{
            //authentication process
            startActivity(intent)
        }


    }
}