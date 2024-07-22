package com.vallem.marvelhq.shared.data.util

import com.vallem.marvelhq.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest

data class MarvelApiAuthData(val timestamp: String, val apiKey: String, val hash: String) {
    companion object {
        fun generate() = run {
            val ts = System.currentTimeMillis().toString()
            val message = ts + BuildConfig.MARVEL_API_PRIVATE_KEY + BuildConfig.MARVEL_API_PUBLIC_KEY
            val messageDigest = MessageDigest.getInstance("MD5").digest(message.encodeToByteArray())
            val hash = BigInteger(1, messageDigest).toString(16).padStart(32, '0')

            MarvelApiAuthData(ts, BuildConfig.MARVEL_API_PUBLIC_KEY, hash)
        }
    }
}
