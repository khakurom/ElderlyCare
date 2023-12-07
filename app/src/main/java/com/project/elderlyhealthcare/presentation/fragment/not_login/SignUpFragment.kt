package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentSignUpBinding
import com.project.elderlyhealthcare.domain.models.CustomerInfoModel
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import com.project.elderlyhealthcare.utils.Utils.textIsNull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment :
    BaseFragment<NotLoginViewModel, FragmentSignUpBinding>(R.layout.fragment_sign_up) {
    override fun variableId(): Int = BR.signUpViewModel

    override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentSignUpBinding {
        return FragmentSignUpBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            signUpFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            layoutRegister.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })

            registerBtContinue.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    checkAccountInfo()
                }
            })
        }
    }

    private fun checkAccountInfo() {
        binding.apply {
            if (textIsNull(registerEdCustomerName) ||
                textIsNull(registerEdPhoneNumber) ||
                textIsNull(registerEdAccount) ||
                textIsNull(registerEdPassword) ||
                textIsNull(registerEdConfirmPassword)
            ) {
                setBackgroundLayoutEditText()
            } else {
                setBackgroundLayoutEditText()
                if (phoneNumberIsValid(registerEdPhoneNumber.text!!.trim().toString())) {
                    if (accountNameIsValid(registerEdAccount.text!!.trim().toString())) {

                    } else {
                        showDialog(requireContext(),"Vui lòng đặt ")
                    }
                } else {
                    showDialog(requireContext(), "Vui lòng nhập số điện thoại hợp lệ")
                }
                if (registerEdPassword.text.toString()
                        .trim() == registerEdConfirmPassword.text.toString().trim()
                ) {
                    val customerInfoModel = CustomerInfoModel (
                        customerName = registerEdCustomerName.text.toString().trim(),
                        phoneNumber = registerEdPhoneNumber.text.toString().trim(),
                        accountName = registerEdAccount.text.toString().trim(),
                        password = registerEdPassword.text.toString().trim()
                    )
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToProvideInformationFragment(customerInfoModel))
                } else {
					showDialog(requireContext(), "Mật khẩu xác nhận không chính xác")
                }
            }
        }
    }


    private fun phoneNumberIsValid (phoneNumber : String) : Boolean {
        return phoneNumber.length > 6
    }

    private fun accountNameIsValid (accountName : String) : Boolean {
        return accountName.length > 5
    }

    // when input text field is empty
    private fun setBackgroundLayoutEditText() {
        val errorMsg = getString(R.string.login_error_msg_edittext_empty)
        binding.apply {
            registerLayoutEdCustomerName.error =
                if (textIsNull(registerEdCustomerName)) errorMsg else null
            registerLayoutEdPhoneNumber.error =
                if (textIsNull(registerEdPhoneNumber)) errorMsg else null
            registerLayoutEdAccount.error = if (textIsNull(registerEdAccount)) errorMsg else null
            registerLayoutEdPassword.error = if (textIsNull(registerEdPassword)) errorMsg else null
            registerLayoutEdConfirmPassword.error =
                if (textIsNull(registerEdConfirmPassword)) errorMsg else null
        }
    }




}