package com.example.BroadcastingAndRoomDatabase

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {

    suspend fun createUserRecords(user: User) : Long {
        return userDao.insertToRoomDatabase(user)
    }

    val getUserDetails: Flow<List<User>> get() =  userDao.getUserDetails()

    suspend fun deleteSingleUserRecord() {
        userDao.deleteSingleUserDetails(1)
    }

}