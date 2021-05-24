package com.example.fastwayofcrossroad

import android.app.SharedElementCallback
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fastwayofcrossroad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val fs = FileSystem("/CrossRoadData.txt")
    var cr : CrossRoad? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //cr = fs.readFile()

        binding.settingImageButton.setOnClickListener {
            val intent = Intent(this, SettingCrossRoad::class.java)
            startActivity(intent)
        }
    }


}