package com.vixiloc.vcashiermobile.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vixiloc.vcashiermobile.presentation.screens.login.LoginFormScreen
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VcashierMobileTheme {
                LoginFormScreen()
            }
        }
    }
}