package com.example.githubuseruiux.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuseruiux.R
import com.example.githubuseruiux.databinding.ActivitySettingsBinding
import com.example.githubuseruiux.service.ReminderReceiver

class SettingsActivity : AppCompatActivity() {

    companion object {
        private const val REMINDER = "reminder"
    }

    private lateinit var preference: SharedPreferences
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var reminderReceiver: ReminderReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        reminderReceiver = ReminderReceiver()
        preference = getSharedPreferences("setting", MODE_PRIVATE)

        binding.switchReminder.isChecked = getReminder()
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                setReminder(isChecked)
                reminderReceiver.setRepeatingAlarm(this,"09:00", "Comeback to Github App")
            }else{
                setReminder(isChecked)
                reminderReceiver.cancelAlarm(this)
            }
        }
    }

    private fun setReminder(value: Boolean) {
        val editor = preference.edit()
        editor.putBoolean(REMINDER, value)
        editor.apply()
    }

    private fun getReminder(): Boolean {
        return preference.getBoolean(REMINDER, false)
    }

}