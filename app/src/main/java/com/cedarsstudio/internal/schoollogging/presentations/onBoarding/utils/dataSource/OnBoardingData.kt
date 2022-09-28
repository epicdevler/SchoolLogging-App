package com.cedarsstudio.internal.schoollogging.presentations.onBoarding.utils.dataSource

import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.presentations.onBoarding.utils.model.OnBoardingItem

object OnBoardingData {

    operator fun invoke() = arrayListOf(
        OnBoardingItem(img = R.drawable.onboarding_img_one, info = R.string.onBoardingInfoPlaceHolder),
        OnBoardingItem(img = R.drawable.onboarding_img_two, info = R.string.onBoardingInfoPlaceHolder),
        OnBoardingItem(img = R.drawable.onboarding_img_three, info = R.string.onBoardingInfoPlaceHolder),
    )

}