package com.cedarsstudio.internal.schoollogging.onBoarding.screens

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.databinding.OnboardingItemLayoutBinding
import com.cedarsstudio.internal.schoollogging.onBoarding.utils.model.OnBoardingItem
import com.google.android.material.textview.MaterialTextView

class OnBoardingPager(private val contentList: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingPager.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: OnBoardingItem) {
            itemView.findViewById<ImageView>(R.id.onBoarding_item_img).setImageResource(item.img)
            itemView.findViewById<MaterialTextView>(R.id.onBoarding_item_text).text = itemView
                .context.getString(item.info)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        OnboardingItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(contentList[position])

    override fun getItemCount(): Int = contentList.size

}