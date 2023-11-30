package com.project.elderlyhealthcare.presentation.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel
import com.project.elderlyhealthcare.domain.models.MedicineEventModel


@BindingAdapter("submitListExerciseEvent")
fun RecyclerView.submitListExerciseEvent(listExerciseEvent: List<ExerciseEventModel>?) {
    listExerciseEvent?.let {
        (adapter as ExerciseAdapter).submitList(it)
    }
}

@BindingAdapter("submitListMedicineEvent")
fun RecyclerView.submitListMedicineEvent(listMedicineEvent: List<MedicineEventModel>?) {
    listMedicineEvent?.let {
        (adapter as MedicineAdapter).submitList(it)
    }
}