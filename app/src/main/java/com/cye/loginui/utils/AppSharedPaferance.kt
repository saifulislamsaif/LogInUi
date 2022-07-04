package com.cye.loginui.utils

import android.content.Context
import android.provider.SyncStateContract
import com.cye.loginui.model.UserModel
import com.cye.loginui.utils.Constant.FLASH_ACTION_ON_OFF
import com.google.gson.Gson

private const val TEMP_SHARE_PREF = "TEMP_SHARE_PREF"
private const val APP_SHARE_PREF = "APP_SHARE_PREF"
private const val LOGIN_SHARE_PREF = "LOGIN_SHARE_PREF"

private const val IS_USER_LOGIN = "IS_USER_LOGIN"
private const val NOTIFICATION_COUNT = "NOTIFICATION_COUNT"

private const val APP_LANGUAGE = "APP_LANGUAGE"

private const val APP_TOKEN = "APP_TOKEN"
private const val USER_MODEL = "USER_MODEL"
private const val FLASH_ACTION = "FLASH_ACTION"

fun Context.clearTempPreferences() {
    getSharedPreferences(TEMP_SHARE_PREF, Context.MODE_PRIVATE).edit().clear().apply()
}


fun Context.clearAppPreferences() {
    getSharedPreferences(APP_SHARE_PREF, Context.MODE_PRIVATE).edit().clear().apply()
}

fun Context.clearLoginPreferences() {
    getSharedPreferences(LOGIN_SHARE_PREF, Context.MODE_PRIVATE).edit().clear().apply()
}


fun Context.setLanguage(languageCode: String) {
    getSharedPreferences(APP_SHARE_PREF, Context.MODE_PRIVATE).edit().putString(APP_LANGUAGE, languageCode)
        .apply()
}

fun Context.getLanguage(): String {
    return getSharedPreferences(APP_SHARE_PREF, Context.MODE_PRIVATE).getString(APP_LANGUAGE, "bn") ?: "bn"
}

fun Context.setToken(token: String) {
    getSharedPreferences(APP_SHARE_PREF, Context.MODE_PRIVATE).edit().putString(APP_TOKEN, token)
        .apply()
}

fun Context.getToken(): String {
    return getSharedPreferences(APP_SHARE_PREF, Context.MODE_PRIVATE).getString(APP_TOKEN, "") ?: ""
}


fun Context.setUserModel(userModel: UserModel?) {
    if (userModel != null)
        getSharedPreferences(LOGIN_SHARE_PREF, Context.MODE_PRIVATE).edit()
            .putString(USER_MODEL, Gson().toJson(userModel))
            .apply()
}

fun Context.getUserModel(): UserModel? {
    return Gson().fromJson(
        getSharedPreferences(
            LOGIN_SHARE_PREF,
            Context.MODE_PRIVATE
        ).getString(USER_MODEL, "") ?: "", UserModel::class.java
    )
}

fun Context.setIsUserLogin(status: Boolean) {
    getSharedPreferences(LOGIN_SHARE_PREF, Context.MODE_PRIVATE).edit().putBoolean(IS_USER_LOGIN, status)
        .apply()
}

fun Context.getIsUserLogin(): Boolean {
    return getSharedPreferences(LOGIN_SHARE_PREF, Context.MODE_PRIVATE).getBoolean(IS_USER_LOGIN, false)
}


fun Context.setNotificationCount(number: Int) {
    getSharedPreferences(TEMP_SHARE_PREF, Context.MODE_PRIVATE).edit().putInt(IS_USER_LOGIN, number)
        .apply()
}

fun Context.getNotificationCount(): Int {
    return getSharedPreferences(TEMP_SHARE_PREF, Context.MODE_PRIVATE).getInt(IS_USER_LOGIN, 0)
}
