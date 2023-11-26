package com.project.elderlyhealthcare.presentation.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.elderlyhealthcare.domain.models.ExerciseEventModel


@BindingAdapter("submitListExerciseEvent")
fun RecyclerView.submitListCourse(listExerciseEvent: List<ExerciseEventModel>?) {
    listExerciseEvent?.let {
        (adapter as ExerciseAdapter).submitList(it)
    }
}