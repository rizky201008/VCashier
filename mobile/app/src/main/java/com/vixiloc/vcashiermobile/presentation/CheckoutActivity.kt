package com.vixiloc.vcashiermobile.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vixiloc.vcashiermobile.presentation.navigations.CheckoutNavHost
import com.vixiloc.vcashiermobile.presentation.ui.theme.VcashierMobileTheme

class CheckoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VcashierMobileTheme {
                Surface {
                    val navHostController = rememberNavController()
                    CheckoutNavHost(
                        navController = navHostController,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}