package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentForgetPasswordBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.getTextFromEdittext
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment<NotLoginViewModel, FragmentForgetPasswordBinding>(R.layout.fragment_forget_password) {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun variableId(): Int = BR.forgetPasswordViewModel

    override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentForgetPasswordBinding {
        return FragmentForgetPasswordBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            forgetPwFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            layoutForgetPassword.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })
            forgetBtContinue.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    checkPhoneNumber()
                }
            })
        }
    }

    private fun checkPhoneNumber() {
        val errorMsg = getString(R.string.login_error_msg_edittext_empty)
        binding.apply {
            if (getTextFromEdittext(forgetEdPhoneNumber).isEmpty()) {
                forgetLayoutEdPhoneNumber.error = errorMsg
            } else {
                forgetLayoutEdPhoneNumber.error = null
                checkPhoneNumberIsRegistered()
            }
        }
    }

    private fun checkPhoneNumberIsRegistered() {
        getPhoneNumberRegistered {
            if (it.contains(getTextFromEdittext(binding.forgetEdPhoneNumber))) {
                findNavController().navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToVerifyPhoneNumberFragment(null, getTextFromEdittext(binding.forgetEdPhoneNumber)))
            } else {
                showDialog(requireContext(), "Số điện thoại không tồn tại")
            }
        }
    }


    private fun getPhoneNumberRegistered(callback: (List<String>) -> Unit) {
        binding.progressBar.visibility = View.VISIBLE
        val dataNodeReference = databaseReference.child("data")
        dataNodeReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val childNames = mutableListOf<String>()
                for (childSnapshot in dataSnapshot.children) {
                    // Retrieve the name of each child
                    val childName = childSnapshot.key
                    if (childName != null) {
                        childNames.add(childName)
                    }
                }
                callback(childNames)
                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(databaseError: DatabaseError) {
                showDialog(requireContext(), "Lỗi hệ thống. Vui lòng thử lại sau!")
                binding.progressBar.visibility = View.GONE
            }
        })
    }

}