package com.example.vpn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vpn.di.appModule
import com.example.vpn.presentation.screens.UnitedVpn
import com.example.vpn.ui.theme.VPNTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin {
            androidContext(this@MainActivity)
            androidLogger()
            modules(appModule)
        }
        setContent {
            VPNTheme {
                // A surface container using the 'background' color from the theme
                UnitedVpn()
            }
        }
    }
}

