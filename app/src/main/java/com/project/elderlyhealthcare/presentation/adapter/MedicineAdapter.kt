package com.project.elderlyhealthcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemExerciseEventBinding
import com.project.elderlyhealthcare.databinding.ItemMedicineEventBinding
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import com.project.elderlyhealthcare.utils.Utils.sortDayList

class MedicineAdapter :
    BaseAdapterDiffUtil<MedicineEventModel, ItemMedicineEventBinding, MedicineAdapter.MedicineHolder>(ItemMedicineDiffCallback()) {

    inner class MedicineHolder(binding: ItemMedicineEventBinding) :
        BaseViewHolder<MedicineEventModel, ItemMedicineEventBinding>(binding) {
        override fun bind(item: MedicineEventModel) {
            binding.medicineModel = item
            binding.dayRepeat = formatDayRepeatList (item)
        }

        private fun formatDayRepeatList (item : MedicineEventModel) : String {
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
            return dayRepeat
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemMedicineEventBinding {
        return ItemMedicineEventBinding.inflate(inflater,parent,false)
    }


    override fun createViewHolder(binding: ItemMedicineEventBinding): MedicineHolder {
        return MedicineHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            onItemSelectListener?.onItemSelected(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition)
        }
        holder.binding.itemMedicineIvClear.setOnClickListener {
            onItemRemoveListener?.onItemRemove(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition)
        }
    }
}


class ItemMedicineDiffCallback : DiffUtil.ItemCallback<MedicineEventModel>() {
    override fun areItemsTheSame(oldItem: MedicineEventModel, newItem: MedicineEventModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MedicineEventModel, newItem: MedicineEventModel): Boolean {
        return oldItem == newItem
    }
}