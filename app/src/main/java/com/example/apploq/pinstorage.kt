package com.example.apploq

import android.content.Context
import java.security.MessageDigest

object PinStorage {

    private const val PREFS_NAME = "apploq_security"
    private const val PIN_HASH_KEY = "pin_hash"

    private fun hashPin(pin: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(pin.toByteArray())

        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }

    fun savePin(context: Context, pin: String) {
        val hashedPin = hashPin(pin)

        context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(PIN_HASH_KEY, hashedPin)
            .apply()
    }

    fun hasPin(context: Context): Boolean {
        return context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .contains(PIN_HASH_KEY)
    }

    fun verifyPin(context: Context, pin: String): Boolean {
        val savedHash = context
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(PIN_HASH_KEY, null)

        return savedHash != null && savedHash == hashPin(pin)
    }
}