package com.cedarsstudio.internal.schoollogging.onBoarding.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.viewpager2.widget.ViewPager2
import com.cedarsstudio.internal.schoollogging.databinding.OnboardingActivityBinding
import com.cedarsstudio.internal.schoollogging.onBoarding.OnBoardingVM
import com.cedarsstudio.internal.schoollogging.onBoarding.utils.dataSource.OnBoardingData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class OnBoarding : AppCompatActivity() {

    private lateinit var binding: OnboardingActivityBinding
    private lateinit var viewPager: ViewPager2
    private val vm: OnBoardingVM by viewModels()

    companion object{
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
            onBoardingNext.visibility = if(vm.shouldGetStarted.value) View.INVISIBLE else View
                .VISIBLE
            onBoardingGetStarted.visibility = if(!vm.shouldGetStarted.value) View.INVISIBLE else
                View.VISIBLE
        }

    }

    private fun observeViewPagerChange() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                vm.setShouldGetStarted(position == 2)
            }
        })
    }

    private fun initButtonClicks() {
        binding.onBoardingSkip.setOnClickListener {

        }

        binding.onBoardingNext.setOnClickListener {
            moveViewPagerItem()
        }
    }

    private fun initPager() {
        viewPager = binding.onBoardingPager
        viewPager.adapter = OnBoardingPager(OnBoardingData())
        binding.onBoardingIndicators.attachTo(viewPager)
        CoroutineScope(Dispatchers.Default).launch {
            while (isActive){
                delay(2000)
                moveViewPagerItem()
            }
            awaitCancellation()
        }
    }

    private fun moveViewPagerItem() {
        viewPager.apply {
            setCurrentItem(if (currentItem == 2) 0 else ++currentItem, true)

        }
    }
}