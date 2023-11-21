package com.project.elderlyhealthcare.presentation.fragment.main.event

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentMedicineEventBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener


class MedicineEventFragment :  BaseFragment<EventViewModel, FragmentMedicineEventBinding>(R.layout.fragment_medicine_event){
	override fun variableId(): Int = BR.medicineViewModel

	override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentMedicineEventBinding {
		return FragmentMedicineEventBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			medicineFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
			medicineFabAdd.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					findNavController().navigate(MedicineEventFragmentDirections.actionMedicineEventFragmentToAddMedicineFragment())
				}
			})


		}
	}


}