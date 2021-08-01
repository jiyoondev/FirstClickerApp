package com.jiyoon.fistclickerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.jiyoon.fistclickerapp.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 기능 - (자원: 돈) 나타내기
        var sumMoney = 0
        var currentMoney = 1


        // TODO: 타이머

        // 핸들러
        var handler = Handler(Looper.getMainLooper())

        // 기능 - 클릭 시 (자원: 돈) 증가
        binding.btnClick.setOnClickListener {
            Thread() {
                currentMoney++
                sumMoney += currentMoney

                // 일정 자원 수집 후 버튼 노출
                if (currentMoney == 11) {
                    handler.post { binding.btnSource1.visibility = View.VISIBLE }
                }
            }.start()

            binding.tvStartSource1.text = "Money: $currentMoney"
        }


        // 기능 - shop1 함수
        var source1IsClicked: Boolean = false
        binding.btnSource1.setOnClickListener {
            source1IsClicked = true
        }


        // 기능 - 클릭 시 (자원: 돈) 증가
        Thread() {
            var sumSource1 = 0
            var currentSource1 = 0

            kotlin.concurrent.timer(period = 1000, initialDelay = 1000) {
                if (source1IsClicked) {
                    currentSource1++
                    sumSource1 += currentSource1

                    handler.post {
                        binding.tvSource1.text = "Bread: $currentSource1"
                    }
                }
            }
        }.start()
    }
}