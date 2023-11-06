package com.project.elderlyhealthcare.presentation.fragment.main

import android.view.View
import androidx.fragment.app.viewModels
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentOverallBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.OverallViewModel

class OverallFragment : BaseFragment<OverallViewModel, FragmentOverallBinding>(R.layout.fragment_overall) {
	override fun variableId(): Int = BR.overallViewModel

	override fun createViewModel(): Lazy<OverallViewModel> = viewModels ()

	override fun bindView(view: View): FragmentOverallBinding {
		return FragmentOverallBinding.bind(view)
	}

}