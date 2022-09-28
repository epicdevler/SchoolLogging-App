package com.cedarsstudio.internal.schoollogging.presentations.onBoarding.utils.model

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingItem(
    @DrawableRes val img: Int,
    @StringRes val info: Int
)
