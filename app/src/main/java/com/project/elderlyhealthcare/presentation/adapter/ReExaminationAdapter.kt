package com.project.elderlyhealthcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemExerciseEventBinding
import com.project.elderlyhealthcare.databinding.ItemReExaminationEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.ReExaminationEventModel

class ReExaminationAdapter :
    BaseAdapterDiffUtil<ReExaminationEventModel, ItemReExaminationEventBinding, ReExaminationAdapter.ReExaminationHolder>(
        ItemReExaminationDiffCallback()
    ) {

    inner class ReExaminationHolder(binding: ItemReExaminationEventBinding) :
        BaseViewHolder<ReExaminationEventModel, ItemReExaminationEventBinding>(binding) {
        override fun bind(item: ReExaminationEventModel) {
            binding.reExModel = item
        }
    }


    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemReExaminationEventBinding {
        return ItemReExaminationEventBinding.inflate(inflater, parent, false)
    }


    override fun createViewHolder(binding: ItemReExaminationEventBinding): ReExaminationHolder {
        return ReExaminationHolder(binding)
    }

    override fun onBindViewHolder(holder: ReExaminationAdapter.ReExaminationHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            onItemSelectListener?.onItemSelected(
                getItem(holder.absoluteAdapterPosition),
                holder.absoluteAdapterPosition
            )
        }
        holder.binding.itemReExIvClear.setOnClickListener {
            onItemRemoveListener?.onItemRemove(
                getItem(holder.absoluteAdapterPosition),
                holder.absoluteAdapterPosition
            )
        }
        holder.binding.itemReExSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.binding.itemReExSwitch.isChecked =
                    onItemTurnOnListener?.onItemTurnOn(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition) == true
            } else {
                onItemTurnOnListener?.onItemTurnOff(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition)
            }
        }
    }
}


class ItemReExaminationDiffCallback : DiffUtil.ItemCallback<ReExaminationEventModel>() {
    override fun areItemsTheSame(
        oldItem: ReExaminationEventModel,
        newItem: ReExaminationEventModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ReExaminationEventModel,
        newItem: ReExaminationEventModel
    ): Boolean {
        return oldItem == newItem
    }
}