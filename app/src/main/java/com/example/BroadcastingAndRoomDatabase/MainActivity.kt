package com.example.BroadcastingAndRoomDatabase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.BroadcastingAndRoomDatabase.databinding.ActivityMainBinding
import com.example.BroadcastingAndRoomDatabase.servise.ForegroundService
import android.content.IntentFilter as IntentFilter


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
            startService(Intent(this, ForegroundService::class.java))


        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(broadcastReceiver, intentFilter)

    }

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_BATTERY_CHANGED) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                binding.tv.post {
                    binding.tv.text = level.toString().plus("").plus("%")
                }

            }

        }

    }

    override fun onResume() {
        /*   val  receiver = BroadCast()

           val intentFilter = IntentFilter()
           intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
           intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
           registerReceiver(receiver, intentFilter)*/
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)

    }

}
