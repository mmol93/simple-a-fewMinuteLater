package com.example.simeplafewminutelater

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
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
            // 6. 해당 펜딩인텐트에 있는 알람을 해제(삭제, 취소)한다
            alarmManager.cancel(pendingIntent)
        }

        // 7. 정확한 알람이 울리도록 설정해본다
        btn_exactTime.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                // 정확한 시간 설정
                set(Calendar.HOUR_OF_DAY, 14)
                set(Calendar.MINUTE, 43)
                set(Calendar.SECOND, 0)
                set(Calendar.DAY_OF_WEEK, 1)
            }
            val intent = Intent(context, Receiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            Log.d("test1", "Set exact Time: "+ Date().toString())
            // 해당 시간에 Receive()로 신호 보내주기
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }else{
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
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