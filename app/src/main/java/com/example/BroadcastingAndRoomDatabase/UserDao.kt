package com.example.BroadcastingAndRoomDatabase

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToRoomDatabase(user: User) : Long
    @Transaction
    @Query("SELECT * FROM users_table ORDER BY id DESC")
    fun getUserDetails() : Flow<List<User>>
    @Transaction
    @Query("SELECT * FROM users_table WHERE id = :id ORDER BY id DESC")
    fun getSingleUserDetails(id: Long) : Flow<User>
    @Update
    suspend fun updateUserDetails(user: User)
    @Query("DELETE FROM users_table WHERE id = :id")
    suspend fun deleteSingleUserDetails(id: Int)

    @Delete
    suspend fun deleteAllUsersDetails(user: User)
}