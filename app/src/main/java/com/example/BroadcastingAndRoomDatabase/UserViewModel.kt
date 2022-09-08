package com.example.BroadcastingAndRoomDatabase

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(private val userRepo: UserRepo): ViewModel() {

    private val _response = MutableLiveData<Long>()
    val response: LiveData<Long> = _response

    fun insertUserDetails(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            _response.postValue(userRepo.createUserRecords(user))
        }
    }


    private val _userDetails = MutableStateFlow<List<User>>(emptyList())
    val userDetails : StateFlow<List<User>> =  _userDetails

    fun doGetUserDetails(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.getUserDetails
                .catch {
                }
                .collectLatest {
                    _userDetails.value = it
                }
        }
    }

    fun doDeleteSingleUserRecord(){
        viewModelScope.launch(Dispatchers.IO) {
            userRepo.deleteSingleUserRecord()
        }
    }

}