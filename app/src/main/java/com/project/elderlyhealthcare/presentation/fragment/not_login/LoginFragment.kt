package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.content.Intent
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentLoginBinding
import com.project.elderlyhealthcare.presentation.activity.MainActivity
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<NotLoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {
	override fun variableId(): Int = BR.loginViewModel

	override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentLoginBinding {
		return FragmentLoginBinding.bind(view)
	}


	override fun init() {
		super.init()
		binding.apply {
			layoutLogin.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					v.hideKeyboard()
				}
			})
			loginTvRegister.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
				}
			})

			loginTvClickHere.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
				}
			})

			loginBtLogin.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					startActivity(Intent(requireActivity(), MainActivity::class.java))
					activity?.finish()
				}
			})
		}
	}





}