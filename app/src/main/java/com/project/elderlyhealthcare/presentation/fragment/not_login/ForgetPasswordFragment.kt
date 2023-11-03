package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentForgetPasswordBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<NotLoginViewModel, FragmentForgetPasswordBinding>(R.layout.fragment_forget_password) {
	override fun variableId(): Int = BR.forgetPasswordViewModel

	override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

	override fun bindView(view: View): FragmentForgetPasswordBinding {
		return FragmentForgetPasswordBinding.bind(view)
	}

	override fun init() {
		super.init()
		binding.apply {
			forgetPwFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})
			forgetBtContinue.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToVerifyPhoneNumberFragment())
				}
			})
		}
	}

}