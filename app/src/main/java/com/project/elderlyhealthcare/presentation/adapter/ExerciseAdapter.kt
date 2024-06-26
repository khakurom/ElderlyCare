package com.project.elderlyhealthcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemExerciseEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.utils.Utils.sortDayList

class ExerciseAdapter :
    BaseAdapterDiffUtil<ExerciseEventModel, ItemExerciseEventBinding, ExerciseAdapter.ExerciseHolder>(ItemExerciseDiffCallback()) {
    inner class ExerciseHolder(binding: ItemExerciseEventBinding) :
        BaseViewHolder<ExerciseEventModel, ItemExerciseEventBinding>(binding) {
        override fun bind(item: ExerciseEventModel) {
            binding.exerciseModel = item
        }

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemExerciseEventBinding {
        return ItemExerciseEventBinding.inflate(inflater, parent, false)
    }


    override fun createViewHolder(binding: ItemExerciseEventBinding): ExerciseHolder {
        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            onItemSelectListener?.onItemSelected(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition)
        }
        holder.binding.itemExerciseIvClear.setOnClickListener {
            onItemRemoveListener?.onItemRemove(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition)
        }
        holder.binding.itemExerciseSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.binding.itemExerciseSwitch.isChecked =
                    onItemTurnOnListener?.onItemTurnOn(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition) == true
            } else {
                onItemTurnOnListener?.onItemTurnOff(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition)
            }
        }
    }
}


class ItemExerciseDiffCallback : DiffUtil.ItemCallback<ExerciseEventModel>() {
    override fun areItemsTheSame(oldItem: ExerciseEventModel, newItem: ExerciseEventModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExerciseEventModel, newItem: ExerciseEventModel): Boolean {
        return oldItem == newItem
    }
}