package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentSignUpBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<NotLoginViewModel, FragmentSignUpBinding>(R.layout.fragment_sign_up) {
	override fun variableId(): Int = BR.signUpViewModel

	override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentSignUpBinding {
		return FragmentSignUpBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			signUpFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})

			registerBtContinue.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToProvideInformationFragment())
				}
			})
		}
	}


}