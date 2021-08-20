package com.mite.djigibao.database.core

import android.content.SharedPreferences
import com.mite.djigibao.model.Account
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceService @Inject constructor(
    private val serviceRead: SharedPreferences,
    private val serviceWrite: SharedPreferences.Editor
) {

    fun isLoggedIn() = serviceRead.getBoolean("logged_in",false)
    fun setIsLoggedIn(value:Boolean) = serviceWrite.putBoolean("logged_in",value).commit()
    fun saveAccount(account: Account) {
        serviceWrite.putString("account_username",account.username).commit()
        serviceWrite.putString("account_password",account.password).commit()
        serviceWrite.putString("account_role",account.role.name).commit()
    }
    fun getAccountUsername() = serviceRead.getString("account_username",null)
    fun getAccountPassword() = serviceRead.getString("account_password",null)
    fun getAccountRole() = serviceRead.getString("account_role",null)
}