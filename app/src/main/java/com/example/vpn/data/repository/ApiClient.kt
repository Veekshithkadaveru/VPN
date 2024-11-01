package com.example.vpn.data.repository

import com.example.vpn.domain.model.UnitedState

interface ApiClient {
    suspend fun getUnitedVpn():UnitedState

    suspend fun disconnectVpn():String
}