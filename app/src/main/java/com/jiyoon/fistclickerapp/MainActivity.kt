package com.jiyoon.fistclickerapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
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

        // 레아아웃 - 메인 gif 이미지 로드
        Glide.with(this).load(R.drawable.potion_store).into(binding.ivMainGif)

        // 기능 - 배경 음악 재생
        var ostPlayer: MediaPlayer = MediaPlayer.create(this, R.raw.misty_valley_ost)
        ostPlayer?.isLooping = true

        Thread() {
            ostPlayer?.start()
        }.start()

        // 기능 - (자원: 돈) 나타내기
        var sumMoney = 1
        var currentMoney = 1

        // 핸들러
        var handler = Handler(Looper.getMainLooper())


        // 기능 - 클릭 시 (자원: 돈) 증가
        binding.btnClick.setOnClickListener {
            Thread() {
                currentMoney++
                sumMoney++

                // 일정 자원 수집 후 버튼 노출
                if (sumMoney == 11) {
                    handler.post { binding.btnSource1.visibility = View.VISIBLE }
                    handler.post { binding.tvCost1.visibility = View.VISIBLE }
                }
            }.start()

            binding.tvStartSource1.text = currentMoney.toString()
        }

        // 기능 - shop1 클릭하면 자원 수집 시작
        var source1IsClicked: Boolean = false

        binding.btnSource1.setOnClickListener {
            source1IsClicked = true

            // 기능 - source1 클릭 시 buying cost 빠짐
            Thread() {
                var source1BuyingCost = 10
                currentMoney -= source1BuyingCost
            }.start()

            var currentMoneyString = currentMoney.toString()
            binding.tvStartSource1.text = currentMoney.toString()
        }

        // 기능 - 클릭 시 (자원: 돈) 증가
        Thread() {
            var sumSource1 = 0
            var currentSource1 = 0

            kotlin.concurrent.timer(period = 1000, initialDelay = 1000) {
                if (source1IsClicked) {
                    currentSource1++
                    sumSource1++

                    handler.post {
                        binding.tvSource1.text = currentSource1.toString()
                    }
                }
            }
        }.start()
    }
}