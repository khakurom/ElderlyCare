package com.project.elderlyhealthcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemAvgHeartRateBinding
import com.project.elderlyhealthcare.domain.models.AvgHeartRateModel

class AvgHeartRateAdapter :
    BaseAdapterDiffUtil<AvgHeartRateModel, ItemAvgHeartRateBinding, AvgHeartRateAdapter.AvgHeartRateHolder>(ItemAvgHeartRateDiffCallback()) {

    inner class AvgHeartRateHolder(binding: ItemAvgHeartRateBinding) :
        BaseViewHolder<AvgHeartRateModel, ItemAvgHeartRateBinding>(binding) {
        override fun bind(item: AvgHeartRateModel) {
            binding.avgHeartRate = item
        }

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemAvgHeartRateBinding {
        return ItemAvgHeartRateBinding.inflate(inflater, parent, false)
    }


    override fun createViewHolder(binding: ItemAvgHeartRateBinding): AvgHeartRateHolder {
        return AvgHeartRateHolder(binding)
    }

    override fun onBindViewHolder(holder: AvgHeartRateHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }
}


class ItemAvgHeartRateDiffCallback : DiffUtil.ItemCallback<AvgHeartRateModel>() {
    override fun areItemsTheSame(oldItem: AvgHeartRateModel, newItem: AvgHeartRateModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AvgHeartRateModel, newItem: AvgHeartRateModel): Boolean {
        return oldItem == newItem
    }
}