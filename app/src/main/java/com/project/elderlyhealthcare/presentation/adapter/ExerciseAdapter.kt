package com.project.elderlyhealthcare.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemExerciseEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.presentation.fragment.not_login.LoginFragmentDirections
import com.project.elderlyhealthcare.utils.Utils.sortDayList

class ExerciseAdapter :
    BaseAdapterDiffUtil<ExerciseEventModel, ItemExerciseEventBinding, ExerciseAdapter.ExerciseHolder>(ItemExerciseDiffCallback()) {
    inner class ExerciseHolder(binding: ItemExerciseEventBinding) :
        BaseViewHolder<ExerciseEventModel, ItemExerciseEventBinding>(binding) {
        override fun bind(item: ExerciseEventModel) {
            binding.exerciseModel = item
            var dayRepeat = ""
            if (item.dayRepeat.isNotEmpty()) {
                val eventList = sortDayList (item.dayRepeat)
                for ((index, i) in eventList.withIndex()) {
                    dayRepeat += i
                    if (index < eventList.size - 1) {
                        dayRepeat += ","
                    }
                }
            }
            binding.dayRepeat = dayRepeat
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemExerciseEventBinding {
        return ItemExerciseEventBinding.inflate(inflater,parent,false)
    }


    override fun createViewHolder(binding: ItemExerciseEventBinding): ExerciseHolder {
        return ExerciseHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            onItemSelectListener?.onItemSelected(getItem(position), position)
        }
        holder.binding.itemExerciseIvClear.setOnClickListener {
            onIvClearListener?.onItemClear(getItem(position), position)
            notifyItemChanged(position)
        }
    }
}


class ItemExerciseDiffCallback : DiffUtil.ItemCallback<ExerciseEventModel>() {
    override fun areItemsTheSame(oldItem: ExerciseEventModel, newItem: ExerciseEventModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ExerciseEventModel, newItem: ExerciseEventModel): Boolean {
        return oldItem.id == newItem.id
    }
}