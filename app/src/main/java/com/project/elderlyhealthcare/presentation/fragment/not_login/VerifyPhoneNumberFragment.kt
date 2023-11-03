package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentVerifyPhoneNumberBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VerifyPhoneNumberFragment : BaseFragment<NotLoginViewModel, FragmentVerifyPhoneNumberBinding>(R.layout.fragment_verify_phone_number) {
	override fun variableId(): Int = BR.verifyPhoneViewModel

	override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels ()

	override fun bindView(view: View): FragmentVerifyPhoneNumberBinding {
		return FragmentVerifyPhoneNumberBinding.bind(view)
	}

	override fun init() {
		super.init()
		setupOTPInputs ()
		binding.apply {
			verifyFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					backToPreScreen()
				}
			})

			verifyBtVerifyOtp.setOnClickListener(object : SingleClickListener(){
				override fun onSingleClick(v: View) {
					findNavController().navigate(VerifyPhoneNumberFragmentDirections.actionVerifyPhoneNumberFragmentToCompleteFragment())
				}
			})
		}

	}

	private fun setupOTPInputs () {
		binding.apply {
			verifyInputCode1.addTextChangedListener (object : TextWatcher{
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
					if (s.toString().trim().isNotEmpty()) {
						verifyInputCode2.requestFocus()
					}
				}

				override fun afterTextChanged(s: Editable?) {

				}

			})
			verifyInputCode2.addTextChangedListener (object : TextWatcher{
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
					if (s.toString().trim().isNotEmpty()) {
						verifyInputCode3.requestFocus()
					}
				}

				override fun afterTextChanged(s: Editable?) {

				}

			})
			verifyInputCode3.addTextChangedListener (object : TextWatcher{
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
					if (s.toString().trim().isNotEmpty()) {
						verifyInputCode4.requestFocus()
					}
				}

				override fun afterTextChanged(s: Editable?) {

				}

			})
			verifyInputCode4.addTextChangedListener (object : TextWatcher{
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
					if (s.toString().trim().isNotEmpty()) {
						verifyInputCode5.requestFocus()
					}
				}

				override fun afterTextChanged(s: Editable?) {

				}

			})
			verifyInputCode5.addTextChangedListener (object : TextWatcher{
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
					if (s.toString().trim().isNotEmpty()) {
						verifyInputCode6.requestFocus()
					}
				}

				override fun afterTextChanged(s: Editable?) {

				}

			})
			verifyInputCode6.addTextChangedListener (object : TextWatcher{
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
					if (s.toString().trim().isNotEmpty()) {
						view?.hideKeyboard()
					}
				}

				override fun afterTextChanged(s: Editable?) {

				}

			})

		}
	}


}