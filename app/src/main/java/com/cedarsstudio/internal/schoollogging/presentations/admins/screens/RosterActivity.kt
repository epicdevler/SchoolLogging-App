package com.cedarsstudio.internal.schoollogging.presentations.admins.screens

import android.content.pm.ActivityInfo
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.cedarsstudio.internal.schoollogging.databinding.RosterActivityLayoutBinding
import com.cedarsstudio.internal.schoollogging.presentations.admins.screens.roster.RosterAdapters

class RosterActivity : AppCompatActivity() {
    private val binding: RosterActivityLayoutBinding by lazy {
        RosterActivityLayoutBinding.inflate(
            layoutInflater
        )
    }
    private val monthCategoryAdapter = RosterAdapters.MonthCategoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener { finish() }

        binding.monthCategories.apply {
            adapter = monthCategoryAdapter
            layoutManager = LinearLayoutManager(this@RosterActivity, LinearLayoutManager.HORIZONTAL, false)
            addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
                ) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.left = 10
                    outRect.right = 10
                    outRect.bottom = 10
                    outRect.top = 10
                }
            })
        }
    }
}