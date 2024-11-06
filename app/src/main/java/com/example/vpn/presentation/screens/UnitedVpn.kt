package com.example.vpn.presentation.screens

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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

    rewardedAd?.fullScreenContentCallback=object :FullScreenContentCallback(){
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
}
