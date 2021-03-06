package com.jiyoon.fistclickerapp

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionManager
import android.view.View
import com.bumptech.glide.Glide
import com.jiyoon.fistclickerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var ostPlayer: MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 기능 - 배경 음악 재생
        ostPlayer = MediaPlayer.create(this, R.raw.misty_valley_ost)
        ostPlayer.isLooping = true

        // 레아아웃 - 메인 gif 이미지 로드
        Glide.with(this).load(R.drawable.potion_store).into(binding.ivMainGif)

        Thread() {
            ostPlayer.start()
        }.start()

        // 기능 - (자원: 돈) 나타내기
        var sumMoney = 1
        var currentMoney = 1

        // 핸들러
        val handler = Handler(Looper.getMainLooper())


        // 기능 - 클릭 시 (자원: 돈) 증가
        binding.btnClick.setOnClickListener {
            Thread() {
                currentMoney++
                sumMoney++

                // 일정 자원 수집 후 버튼 노출
                if (currentMoney >= 11) {
                    handler.post {
                        binding.clSource1.visibility = View.VISIBLE
                    }
                }
                if (currentMoney >= 71) {
                    handler.post {
                        binding.clSource2.visibility = View.VISIBLE
                    }
                }
                if (currentMoney >= 251) {
                    handler.post {
                        binding.clSource3.visibility = View.VISIBLE
                    }
                }
            }.start()

            binding.tvStartSource1.text = currentMoney.toString()
        }


        // 기능 - shop1 클릭하면 자원 수집 시작
        var source1IsClicked = false
        var source2IsClicked = false

        var sumSource1 = 0
        var currentSource1 = 0

        binding.btnSource1.setOnClickListener {
            source1IsClicked = true

            // 기능 - source1 클릭 시 buying cost 빠짐
            Thread() {
                val source1BuyingCost = 11
                currentMoney -= source1BuyingCost

                handler.post {
                    binding.tvStartSource1.text = currentMoney.toString()
                }

                // 기능 - 3초 후 포션 생성 반복
                var second = 0

                kotlin.concurrent.timer(period = 6000, initialDelay = 1000) {
                    if (source1IsClicked && second == 0) {
                        currentSource1++
                        sumSource1++
                        second++

                        // TODO: progress bar 천천히 제대로 보이게
                        handler.post {
                            binding.tvSource1.text = currentSource1.toString()
                            binding.prbSource1.setProgress(6000, true)
                        }
                    }
                    if (second == 1) {
                        cancel()
                        second = 0

                        // TODO: 프로그레스바 3초 후 초기화 후 다시 재생 이것도 변수로 해보기
//                        handler.post {
//                            binding.prbSource1.
//                        }
                        // TODO: 프로그레스 바 되는 중에는 버튼 비활성화
                    }
                }
            }.start()
        }

        // 기능 - 판매시 돈 자원 증가
        binding.btnSell1.setOnClickListener {
            Thread() {
                if (currentSource1 > 0) {
                    currentMoney += currentSource1 * 20
                    currentSource1 = 0

                    handler.post {
                        binding.tvStartSource1.text = currentMoney.toString()
                        binding.tvSource1.text = currentSource1.toString()
                    }

                }
            }.start()
        }

        // TODO: 돈 음수되지 않게 제한
//        if (currentMoney < 0) {
//            Toast.makeText(this, R.string.money_not_enough, Toast.LENGTH_LONG).show()
//        }

        // TODO: 레이아웃 - Bottom Navigation 구현

        // TODO: 레이아웃 - Recycler View 구현

        // 기능 - shop2 클릭하면 자원 수집 시작
        var sumSource2 = 0
        var currentSource2 = 0

        binding.btnSource2.setOnClickListener {
            source2IsClicked = true

            // 기능 - source2 클릭 시 buying cost 빠짐
            Thread() {
                val source2BuyingCost = 71
                currentMoney -= source2BuyingCost

                handler.post {
                    binding.tvStartSource1.text = currentMoney.toString()
                }

                // 기능 - 3초 후 포션 생성 반복
                var second = 0

                kotlin.concurrent.timer(period = 6000, initialDelay = 1000) {
                    if (source2IsClicked && second == 0) {
                        currentSource2++
                        sumSource2++
                        second++

                        // TODO: progress bar 천천히 제대로 보이게
                        handler.post {
                            binding.tvSource2.text = currentSource2.toString()
                            binding.prbSource2.setProgress(6000, true)
                        }
                    }
                    if (second == 1) {
                        cancel()
                        second = 0

                        // TODO: 프로그레스바 3초 후 초기화 후 다시 재생 이것도 변수로 해보기
//                        handler.post {
//                            binding.prbSource1.
//                        }
                        // TODO: 프로그레스 바 되는 중에는 버튼 비활성화
                    }
                }
            }.start()
        }

        // 기능 - 판매시 돈 자원 증가
        binding.btnSell2.setOnClickListener {
            Thread() {
                if (currentSource2 > 0) {
                    currentMoney += currentSource2 * 140
                    currentSource2 = 0

                    handler.post {
                        binding.tvStartSource1.text = currentMoney.toString()
                        binding.tvSource2.text = currentSource2.toString()
                    }

                }
            }.start()
        }

        // 기능 - 엔딩
        binding.btnSource3.setOnClickListener {
            val intent = Intent(this, EndingActivity::class.java)
            if (currentMoney >= 250) {
                startActivity(intent)
            }
        }

    }

    override fun onStop() {
        super.onStop()
        ostPlayer.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        ostPlayer.release()
    }
}