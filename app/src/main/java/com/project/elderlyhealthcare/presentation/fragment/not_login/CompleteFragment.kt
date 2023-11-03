package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentCompleteBinding
import com.project.elderlyhealthcare.databinding.FragmentForgetPasswordBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteFragment : BaseFragment<NotLoginViewModel, FragmentCompleteBinding>(R.layout.fragment_complete) {
	override fun variableId(): Int = BR.completeViewModel

	override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentCompleteBinding {
		return FragmentCompleteBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			completeBtComplete.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(CompleteFragmentDirections.actionCompleteFragmentToLoginFragment())
				}
			})
		}
	}

}