package com.example.fastwayofcrossroad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastwayofcrossroad.databinding.ActivitySettingCrossRoadBinding

class SettingCrossRoad : AppCompatActivity() {
    val binding by lazy {ActivitySettingCrossRoadBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.completeButton.setOnClickListener {
            finish()
        }
    }
}