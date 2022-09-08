package com.example.BroadcastingAndRoomDatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.BroadcastingAndRoomDatabase.databinding.ActivityUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class UserDetail : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    private val userViewModel: UserViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getUserDetails()

        handleClicks()

    }

    private fun handleClicks(){

        binding.btnClearRecord.setOnClickListener {

            //clear record from room database
            userViewModel.doDeleteSingleUserRecord()

            //remove the datastorage key
            dataStoreViewModel.setSavedKey(false)

            //go to main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }

    private fun getUserDetails(){

        this.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){

                userViewModel.doGetUserDetails()
                userViewModel.userDetails.collectLatest { users->

                    for (user in users){
                        binding.txtName.text = user.name
                        binding.txtAge.text = user.age
                        binding.txtNumber.text = user.number
                    }

                }
            }
        }

    }

}