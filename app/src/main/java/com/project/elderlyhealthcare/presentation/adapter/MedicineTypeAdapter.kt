package com.project.elderlyhealthcare.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.project.elderlyhealthcare.databinding.ItemMedicineTypeBinding
import com.project.elderlyhealthcare.domain.models.MedicineTypeModel

class MedicineTypeAdapter :
    BaseAdapterDiffUtil<MedicineTypeModel, ItemMedicineTypeBinding, MedicineTypeAdapter.MedicineTypeHolder>(
        ItemMedicineTypeDiffCallback()
    ) {
    inner class MedicineTypeHolder(binding: ItemMedicineTypeBinding) :
        BaseViewHolder<MedicineTypeModel, ItemMedicineTypeBinding>(binding) {

        override fun bind(item: MedicineTypeModel) {
            binding.medicineType = item
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemMedicineTypeBinding {
        return ItemMedicineTypeBinding.inflate(inflater, parent, false)
    }


    override fun createViewHolder(binding: ItemMedicineTypeBinding): MedicineTypeHolder {
        return MedicineTypeHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineTypeHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener {
            onItemSelectListener?.onItemSelected(
                getItem(holder.absoluteAdapterPosition),
                holder.absoluteAdapterPosition
            )
        }
        holder.binding.itemMedicineImgRemove.setOnClickListener {
            onItemRemoveListener?.onItemRemove(
                getItem(holder.absoluteAdapterPosition),
                holder.absoluteAdapterPosition
            )
        }
    }
}


class ItemMedicineTypeDiffCallback : DiffUtil.ItemCallback<MedicineTypeModel>() {
    override fun areItemsTheSame(oldItem: MedicineTypeModel, newItem: MedicineTypeModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MedicineTypeModel, newItem: MedicineTypeModel): Boolean {
        return oldItem == newItem
    }
}