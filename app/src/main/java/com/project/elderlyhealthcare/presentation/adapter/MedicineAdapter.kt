package com.project.elderlyhealthcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemMedicineEventBinding
import com.project.elderlyhealthcare.domain.models.MedicineEventModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class MedicineAdapter :
    BaseAdapterDiffUtil<MedicineEventModel, ItemMedicineEventBinding, MedicineAdapter.MedicineHolder>(ItemMedicineDiffCallback()) {

    inner class MedicineHolder(binding: ItemMedicineEventBinding) :
        BaseViewHolder<MedicineEventModel, ItemMedicineEventBinding>(binding) {
        override fun bind(item: MedicineEventModel) {
            binding.medicineModel = item
            binding.durationMedicine = calculateDays(item.dayBegin,item.dayEnd)
        }

        private fun calculateDays (dayBegin : String, dayEnd : String) : Long {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date1: Date = dateFormat.parse(dayBegin) as Date
            val date2: Date = dateFormat.parse(dayEnd) as Date

            val diffInMillis: Long = date2.time - date1.time
            return TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS)
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
        holder.binding.itemMedicineSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                holder.binding.itemMedicineSwitch.isChecked =
                    onItemTurnOnListener?.onItemTurnOn(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition) == true
            } else {
                onItemTurnOnListener?.onItemTurnOff(getItem(holder.absoluteAdapterPosition), holder.absoluteAdapterPosition)
            }
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