package com.cedarsstudio.internal.schoollogging.onBoarding.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.cedarsstudio.internal.schoollogging.databinding.OnboardingActivityBinding
import com.cedarsstudio.internal.schoollogging.onBoarding.vms.OnBoardingVM
import com.cedarsstudio.internal.schoollogging.onBoarding.utils.dataSource.OnBoardingData
import com.cedarsstudio.internal.schoollogging.utils.toAuthScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class OnBoarding : AppCompatActivity() {

    private lateinit var binding: OnboardingActivityBinding
    private lateinit var viewPager: ViewPager2
    private val vm: OnBoardingVM by viewModels()


    companion object {
        private const val TAG = "ON_BOARDING"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = OnboardingActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initPager()

        initObservers()

        initButtonClicks()


    }

    private fun initObservers() {
        observeViewPagerChange()

        binding.apply {
            lifecycleScope.launchWhenStarted {
            }
        }

    }

    private fun observeViewPagerChange() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.apply {
                    onBoardingNext.visibility = if (position == 2) View.INVISIBLE else View.VISIBLE
                    onBoardingSkip.visibility = if (position == 2) View.INVISIBLE else View.VISIBLE
                    onBoardingGetStarted.visibility =
                        if (position != 2) View.INVISIBLE else
                            View.VISIBLE

                }
            }
        })
    }

    private fun initButtonClicks() {
        binding.apply {
            onBoardingSkip.setOnClickListener {
                toAuthScreen()
            }
            onBoardingGetStarted.setOnClickListener {
                toAuthScreen()
            }
            onBoardingNext.setOnClickListener {
                moveViewPagerItem()
            }
        }
    }

    private fun initPager() {
        viewPager = binding.onBoardingPager
        viewPager.adapter = OnBoardingPager(OnBoardingData())
        binding.onBoardingIndicators.attachTo(viewPager)

    }

    private fun moveViewPagerItem() {
        viewPager.apply {
            setCurrentItem(if (currentItem == 2) 0 else ++currentItem, true)
        }
    }
}