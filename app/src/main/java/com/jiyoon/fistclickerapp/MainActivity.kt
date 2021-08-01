package com.jiyoon.fistclickerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jiyoon.fistclickerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 기능 - (자원: 돈) 나타내기
        var currentMoney = 0

        // 기능 - 클릭 시 (자원: 돈) 증가
        binding.btnClick.setOnClickListener {
            Thread() {
                currentMoney++
            }.start()
            binding.tvMoney.text = "Money: $currentMoney"
        }
    }
}