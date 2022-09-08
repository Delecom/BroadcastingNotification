package com.example.BroadcastingAndRoomDatabase.servise


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.BroadcastingAndRoomDatabase.BroadCast
import com.example.BroadcastingAndRoomDatabase.MainActivity
import com.example.BroadcastingAndRoomDatabase.R

class ForegroundService : Service() {
    private val CHANNEL_ID = "ForegroundService Kotlin"
    val receiver = BroadCast()
    var input: String? = null
    var pendingIntent: PendingIntent? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //do heavy work on a background thread
        input = intent?.getStringExtra("inputExtra") ?: "N/A"
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
         pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0
        )

        setNotification(input ?: "M/A", pendingIntent!! , "Changing Service Notification")

        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(broadcastReceiver, intentFilter)
        return START_NOT_STICKY
    }


    private fun setNotification(input: String, pendingIntent: PendingIntent, newMessage: String) {
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(newMessage)
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
    }

    private var broadcastReceiver: BroadcastReceiver = object : BroadCast() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_POWER_CONNECTED -> {
                    val mPlayer: MediaPlayer = MediaPlayer.create(context, R.raw._33)
                    mPlayer.start()
                    setNotification(input ?: "M/A",
                        pendingIntent ?: return,
                        "Changing Connected")
                    Toast.makeText(context, "Changing Connected", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                    val mPlayer: MediaPlayer = MediaPlayer.create(context, R.raw._1)
                    mPlayer.start()
                    setNotification(input ?: "M/A",
                        pendingIntent ?: return,
                        "Changing  Disconnected")

                    Toast.makeText(context, "Changing  Disconnected", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}