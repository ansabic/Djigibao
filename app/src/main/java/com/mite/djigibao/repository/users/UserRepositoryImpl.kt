package com.mite.djigibao.repository.users

import com.mite.djigibao.database.core.SharedPreferenceService
import com.mite.djigibao.database.daos.UserDao
import com.mite.djigibao.database.entities.User
import com.mite.djigibao.model.Account
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val sharedPreferenceService: SharedPreferenceService

): IUserRepository {
    override suspend fun checkIfLoggedIn(): Boolean {
        return sharedPreferenceService.isLoggedIn()
    }

    override suspend fun getUserByName(name: String): User? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<User>? {
        return userDao.getAllUsers()
    }

    override suspend fun insertUser(account: Account, user: User) {
        sharedPreferenceService.saveAccount(account)
        userDao.insertUser(user)
    }

    override suspend fun insertUsers(users: List<User>) {
        userDao.insertUsers(users)
    }

    override suspend fun removeUser(name: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveMyAccountData(account: Account) {
        sharedPreferenceService.setIsLoggedIn(true)
        sharedPreferenceService.saveAccount(account)
    }
}