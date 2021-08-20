package com.mite.djigibao.repository.users

import com.mite.djigibao.database.entities.User
import com.mite.djigibao.model.Account
import dagger.hilt.android.scopes.ViewModelScoped

@ViewModelScoped
interface IUserRepository {
    suspend fun checkIfLoggedIn(): Boolean
    suspend fun getUserByName(name: String): User?
    suspend fun getAllUsers(): List<User>?
    suspend fun insertUser(account: Account, user: User)
    suspend fun insertUsers(users: List<User>)
    suspend fun removeUser(name: String)
    suspend fun saveMyAccountData(account: Account)
}