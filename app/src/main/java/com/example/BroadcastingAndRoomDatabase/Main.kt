package com.example.BroadcastingAndRoomDatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.BroadcastingAndRoomDatabase.databinding.ActivityMain2Binding
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class Main : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       checkIfUserHasSavedDetails()
        makeButtonNotClickableAtFirst()

    }

    private fun checkIfUserHasSavedDetails(){
        dataStoreViewModel.savedKey.observe(this){
            if (it == true){
                //saved go to the next activity
                val intent = Intent(this, UserDetail::class.java)
                startActivity(intent)
            }
            else{
                initViews()
            }
        }
    }

    private fun initViews(){
        handleClick()
    }


    private fun makeButtonNotClickableAtFirst(){
        binding.btnSave.isEnabled = false
        binding.btnSave.background = ContextCompat.getDrawable(
            this,
            R.drawable.btn_opaque
        )

        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                val nameEt = binding.etName.text.toString()
                val ageEt = binding.etAge.text.toString()
                val numberEt = binding.etNumber.text.toString()

                if (nameEt.isEmpty() || ageEt.isEmpty() || numberEt.isEmpty()) {
                    binding.btnSave.isEnabled = false
                    binding.btnSave.background = ContextCompat.getDrawable(
                        this@Main,
                        R.drawable.btn_opaque
                    )
                } else {
                    binding.btnSave.isEnabled = true
                    binding.btnSave.background = ContextCompat.getDrawable(
                        this@Main,
                        R.drawable.btn_round
                    )
                }
            }

            override fun afterTextChanged(s: Editable) {

            }

        }

        binding.etName.addTextChangedListener(watcher)
        binding.etAge.addTextChangedListener(watcher)
        binding.etNumber.addTextChangedListener(watcher)
        handleClick()
    }


    private fun handleClick(){

        binding.btnSave.setOnClickListener {

            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString()
            val number = binding.etNumber.text.toString()


            val user = User(id = 1, name = name, age = age, number = number)

            userViewModel.insertUserDetails(user)

            userViewModel.response.observe(this){

                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()

                dataStoreViewModel.setSavedKey(true)

                Toast.makeText(this, applicationContext.getString(R.string.record_saved), Toast.LENGTH_LONG).show()

                startActivity(Intent(this, UserDetail::class.java))

            }

        }

    }
}