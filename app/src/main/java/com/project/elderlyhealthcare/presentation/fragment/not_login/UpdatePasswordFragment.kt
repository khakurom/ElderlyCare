package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentUpdatePasswordBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.getTextFromEdittext
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog


class UpdatePasswordFragment : BaseFragment<NotLoginViewModel, FragmentUpdatePasswordBinding>(R.layout.fragment_update_password) {

    private val navArg: UpdatePasswordFragmentArgs by navArgs()

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun variableId(): Int = BR.notLoginViewModel

    override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentUpdatePasswordBinding {
        return FragmentUpdatePasswordBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            updateBtUpdate.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    validateUpdatePassword()
                }
            })
            layoutUpdatePw.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })
        }
    }

    private fun validateUpdatePassword() {
        binding.apply {
            if (getTextFromEdittext(updateEdNewPw).isEmpty() || getTextFromEdittext(updateEdConfirmPw).isEmpty()) {
                setBackgroundLayoutEditText()
            } else {
                setBackgroundLayoutEditText()
                if (passwordIsValid(getTextFromEdittext(updateEdNewPw))) {
                    if (getTextFromEdittext(updateEdNewPw) == getTextFromEdittext(updateEdConfirmPw)) {
                        updatePassword ()
                    } else {
                        showDialog(requireContext(), "Mật khẩu xác nhận không chính xác")
                    }
                } else {
                    showDialog(requireContext(), "Vui lòng đặt mật khẩu nhiều hơn 5 kí tự")
                }
            }
        }

    }

    private fun passwordIsValid(password: String): Boolean {
        return password.length >= 6
    }

    private fun updatePassword() {
        binding.progressBar.visibility = View.VISIBLE
        val dataNodeReference = databaseReference.child("data").child(navArg.PhoneNumber).child(getString(R.string.key_customer_info)).child("password")
        dataNodeReference.setValue(getTextFromEdittext(binding.updateEdNewPw))
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                findNavController().navigate(UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToCompleteFragment())
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                showDialog(requireContext(), "Lỗi hệ thống. Vui lòng thử lại sau!")
            }
    }

    private fun setBackgroundLayoutEditText() {
        val errorMsg = getString(R.string.login_error_msg_edittext_empty)
        binding.apply {
            updateLayoutEdNewPw.error = if (Utils.textIsNull(updateEdNewPw)) errorMsg else null
            updateLayoutEdConfirmPw.error = if (Utils.textIsNull(updateEdConfirmPw)) errorMsg else null

        }
    }

}