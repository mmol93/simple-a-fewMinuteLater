package com.example.simeplafewminutelater

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    // 1. context와 AlarmManager 객체 생성
    lateinit var context: Context
    lateinit var alarmManager: AlarmManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        btn_create.setOnClickListener {
            val second = edit_timer.text.toString().toInt() * 1000
            // 3. 리시버 인텐트 생성
            val intent = Intent(context, Receiver::class.java)
            // 4. 펜딩 인텐트를 생성하고 브로드케스트를 얻게 한다
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.d("test1", "Create: "+ Date().toString())
            // 알람 매니저에 알람을 세팅한다
            // 5. setExact(): 정확한 시간의 알람 세팅
            // RTC_WAKEUP: 절전모드일 경우 절전모드를 해제하고 알람이 울리게한다
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + second, pendingIntent)
        }

        btn_update.setOnClickListener {
            val second = edit_timer.text.toString().toInt() * 1000
            val intent = Intent(context, Receiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.d("test1", "Update: "+ Date().toString())
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + second, pendingIntent)
        }

        btn_cancel.setOnClickListener {
            val intent = Intent(context, Receiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.d("test1", "Cancel: "+ Date().toString())
            // 6. 해당 펜딩인텐트에 있는 알람을 해제한다
            alarmManager.cancel(pendingIntent)
        }


    }
    // 2. 리시버 클래스 정의
    // 알람 울릴 시 할 행동을 여기에 정의한다
    class Receiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("test1", "Receiver: " + Date().toString())
        }
    }
}