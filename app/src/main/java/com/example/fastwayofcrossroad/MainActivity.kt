package com.example.fastwayofcrossroad

import android.app.SharedElementCallback
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.fastwayofcrossroad.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}
    val fs = FileSystem("/CrossRoadData.txt")
    var cr : CrossRoad? = null
    var openedRoad : Int? = null
    var startPoint : Int? = null
    var endPoint : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //cr = fs.readFile()

        if(intent.hasExtra("name")) {
            cr?.name = intent.getStringExtra("name").toString()
        }
        if(intent.hasExtra("time")) {
            cr?.time = intent.getIntExtra("time", 0).toInt()
        }
        if(intent.hasExtra("crossroad1") && intent.hasExtra("crossroad2")
            && intent.hasExtra("crossroad3") && intent.hasExtra("crossroad4")) {
            cr?.roadnums = arrayOf(intent.getIntExtra("crossroad1", 0).toInt(),
                intent.getIntExtra("crossroad2", 0).toInt(),
                intent.getIntExtra("crossroad3", 0).toInt(),
                intent.getIntExtra("crossroad4", 0).toInt())
        }
        if(intent.hasExtra("crosswalk1") && intent.hasExtra("crosswalk2")
            && intent.hasExtra("crosswalk3") && intent.hasExtra("crosswalk4")) {
            cr?.crosswalknums = arrayOf(intent.getIntExtra("crosswalk1", 0).toInt(),
                intent.getIntExtra("crosswalk2", 0).toInt(),
                intent.getIntExtra("crosswalk3", 0).toInt(),
                intent.getIntExtra("crosswalk4", 0).toInt())
        }

        binding.nameTextViewForVar.setText(cr?.name)

        binding.settingImageButton.setOnClickListener {
            val intent = Intent(this, SettingCrossRoad::class.java)
            startActivity(intent)
            finish()
        }
    }


}