package com.project.elderlyhealthcare.presentation.fragment.main.overall

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentOverallBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.OverallViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OverallFragment : BaseFragment<OverallViewModel, FragmentOverallBinding>(R.layout.fragment_overall) {
	override fun variableId(): Int = BR.overallViewModel

	override fun createViewModel(): Lazy<OverallViewModel> = viewModels ()

	override fun bindView(view: View): FragmentOverallBinding {
		return FragmentOverallBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			layoutAvgHeartRate.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					findNavController().navigate(OverallFragmentDirections.actionOverallFragmentToAvgHeartRateFragment())
				}
			})
			layoutAvgOxygen.setOnClickListener(object : SingleClickListener() {
				override fun onSingleClick(v: View) {
					findNavController().navigate(OverallFragmentDirections.actionOverallFragmentToAvgOxygenFragment())
				}
			})
		}
	}



}