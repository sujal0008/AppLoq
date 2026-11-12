package com.example.apploq

import android.content.Context

object ProtectedAppsStorage {

    private const val PREFS_NAME = "apploq_protected_apps"
    private const val PROTECTED_APPS_KEY = "protected_apps"

    fun saveProtectedApps(
        context: Context,
        apps: Set<String>
    ) {
        context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .edit()
            .putStringSet(
                PROTECTED_APPS_KEY,
                apps
            )
            .apply()
    }

    fun getProtectedApps(
        context: Context
    ): Set<String> {

        return context
            .getSharedPreferences(
                PREFS_NAME,
                Context.MODE_PRIVATE
            )
            .getStringSet(
                PROTECTED_APPS_KEY,
                emptySet()
            )
            ?.toSet()
            ?: emptySet()
    }

    fun isProtected(
        context: Context,
        packageName: String
    ): Boolean {

        return getProtectedApps(context)
            .contains(packageName)
    }
}