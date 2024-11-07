package com.example.vpn.presentation.screens

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.VpnService
import android.os.Build
import android.os.IBinder
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuOpen
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import com.example.vpn.R
import com.example.vpn.domain.model.UnitedState
import com.example.vpn.domain.usecase.ResultState
import com.example.vpn.presentation.viewmodel.MainViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun UnitedVpn() {
    val viewModel: MainViewModel = koinInject()
    val state by viewModel.allVpn.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    var unitedData by remember { mutableStateOf<UnitedState?>(null) }
    var isConnected by remember { mutableStateOf(false) }
    var bottomNavigation by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("United State") }
    var selectedCountryFlag by remember { mutableStateOf(R.drawable.unitedflag) }
    val scope = rememberCoroutineScope()
    val scroll = rememberScrollState()
    val context = LocalContext.current
    var rewardedAd: RewardedAd? = null

    when (state) {
        is ResultState.Error -> {
            val error = (state as ResultState.Error).error
            Text(text = error.toString())
            isLoading = false
        }

        ResultState.Loading -> {
            isLoading = true
        }

        is ResultState.Success -> {
            val success = (state as ResultState.Success).success
            unitedData = success
            isLoading = false
        }
    }

    RewardedAd.load(
        context,
        "ca-app-pub-3940256099942544/2247696110",
        AdRequest.Builder().build(),
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                rewardedAd = null
            }

            override fun onAdLoaded(p0: RewardedAd) {
                super.onAdLoaded(p0)
                rewardedAd = p0
            }
        }
    )

    rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            super.onAdClicked()
        }

        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
        }

        override fun onAdImpression() {
            super.onAdImpression()
        }

        override fun onAdShowedFullScreenContent() {
            super.onAdShowedFullScreenContent()
        }
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "Vpn",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.MenuOpen,
                    contentDescription = "",
                    tint = Color.White
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0XFF0b98fa))
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scroll)
                .background(Color.White)
                .padding(top = it.calculateTopPadding(), bottom = 2.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 200.dp, bottomEnd = 200.dp
                        )
                    )
                    .fillMaxWidth()
                    .background(Color(0XFF0b98fa))
                    .height(290.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {

                }
            }

        }
    }
}

fun startVpnService(context: Context) {
    val intent = Intent(context, VpnService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else {
        context.startService(intent)
    }
}

fun stopVpnService(context: Context) {
    val intent = Intent(context, VpnService::class.java)
    context.stopService(intent)
}

open class VpnService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat
            .Builder(this, "vpn_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("VPN Connected")
            .setContentText("VPN is running")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        startForeground(1,notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }

    private fun createNotificationChannel() {
        TODO("Not yet implemented")
    }

}


