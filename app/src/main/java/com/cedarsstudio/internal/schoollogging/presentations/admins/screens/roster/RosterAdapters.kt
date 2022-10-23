package com.cedarsstudio.internal.schoollogging.presentations.admins.screens.roster

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cedarsstudio.internal.schoollogging.R
import com.cedarsstudio.internal.schoollogging.databinding.RosterMonthsRecyclerItemBinding

object RosterAdapters {

    class MonthCategoryAdapter :
        RecyclerView.Adapter<MonthCategoryAdapter.MonthCategoriesViewHolder>() {
        private val months: MutableList<String> = mutableListOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sept",
            "Oct",
            "Nov",
            "Dec",
        )

        @SuppressLint("InflateParams")
        override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int
        ): MonthCategoriesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.roster_months_recycler_item, parent, false)
            return MonthCategoriesViewHolder(view)
        }

        override fun onBindViewHolder(holder: MonthCategoriesViewHolder, position: Int) {
            val month = months[position]
            holder.bind(month) {
                Log.e("TAG", "onBindViewHolder: $it")
            }
        }

        override fun getItemCount(): Int = months.size

        inner class MonthCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val binding = RosterMonthsRecyclerItemBinding.bind(view)
            fun bind(month: String, itemClick: (String) -> Unit) {
                binding.rosterMonth.apply {
                    text = month
                    isChecked = true
                    setOnClickListener {
                        itemClick(month)
                    }
                }
            }

        }

    }

}

