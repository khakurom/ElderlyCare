package com.project.elderlyhealthcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemAvgOxygenBinding
import com.project.elderlyhealthcare.domain.models.AvgOxygenModel

class AvgOxygenAdapter :
    BaseAdapterDiffUtil<AvgOxygenModel, ItemAvgOxygenBinding, AvgOxygenAdapter.AvgOxygenHolder>(ItemOxygenRateDiffCallback()) {

    inner class AvgOxygenHolder(binding: ItemAvgOxygenBinding) :
        BaseViewHolder<AvgOxygenModel, ItemAvgOxygenBinding>(binding) {
        override fun bind(item: AvgOxygenModel) {
            binding.avgOxygen = item
        }

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemAvgOxygenBinding {
        return ItemAvgOxygenBinding.inflate(inflater, parent, false)
    }


    override fun createViewHolder(binding: ItemAvgOxygenBinding): AvgOxygenHolder {
        return AvgOxygenHolder(binding)
    }

    override fun onBindViewHolder(holder: AvgOxygenHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }
}


class ItemOxygenRateDiffCallback : DiffUtil.ItemCallback<AvgOxygenModel>() {
    override fun areItemsTheSame(oldItem: AvgOxygenModel, newItem: AvgOxygenModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AvgOxygenModel, newItem: AvgOxygenModel): Boolean {
        return oldItem == newItem
    }
}