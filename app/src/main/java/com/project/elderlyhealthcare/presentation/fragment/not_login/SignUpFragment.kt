package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentSignUpBinding
import com.project.elderlyhealthcare.domain.models.CustomerInfoModel
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.getTextFromEdittext
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import com.project.elderlyhealthcare.utils.Utils.textIsNull
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : BaseFragment<NotLoginViewModel, FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
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
            if (textIsNull(registerEdCustomerName) || textIsNull(registerEdPhoneNumber) || textIsNull(registerEdPassword) || textIsNull(registerEdConfirmPassword)) {
                setBackgroundLayoutEditText()
            } else {
                setBackgroundLayoutEditText()
                if (phoneNumberIsValid(registerEdPhoneNumber.text!!.trim().toString())) {
                    if (passwordIsValid(registerEdPassword.text!!.trim().toString())) {
                        if (registerEdPassword.text.toString().trim() == registerEdConfirmPassword.text.toString().trim()) {
                            checkNetworkIsAvailable ()
                        } else {
                            showDialog(requireContext(), "Mật khẩu xác nhận không chính xác")
                        }
                    } else {
                        showDialog(requireContext(), "Vui lòng đặt mật khẩu dài hơn 5 kí tự ")
                    }
                } else {
                    showDialog(requireContext(), "Vui lòng đặt tên tài khoản dài hơn 4 kí tự ")
                }

            }
        }
    }

    private fun checkNetworkIsAvailable () {
        if (Utils.isNetworkAvailable(requireContext())) {
            checkPhoneNumberIsRegistered()
        } else {
            showDialog(requireContext(),"Vui lòng kết nối internet")
        }
    }

    private fun checkPhoneNumberIsRegistered() {
        binding.apply {
            getPhoneNumberRegistered {
                if (it.contains(getTextFromEdittext(registerEdPhoneNumber))) {
                    showDialog(requireContext(), "Số điện thoại này đã đăng kí. Vui lòng nhập số khác!")
                } else {
                    val customerInfoModel = CustomerInfoModel(
                        customerName = getTextFromEdittext(registerEdCustomerName),
                        phoneNumber = getTextFromEdittext(registerEdPhoneNumber),
                        password = getTextFromEdittext(registerEdPassword)
                    )
                    findNavController().navigate(
                        SignUpFragmentDirections.actionSignUpFragmentToProvideInformationFragment(customerInfoModel)
                    )
                }
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


    private fun phoneNumberIsValid(phoneNumber: String): Boolean {
        return phoneNumber.length > 6
    }

    private fun passwordIsValid(password: String): Boolean {
        return password.length >= 6
    }

    // when input text field is empty
    private fun setBackgroundLayoutEditText() {
        val errorMsg = getString(R.string.login_error_msg_edittext_empty)
        binding.apply {
            registerLayoutEdCustomerName.error = if (textIsNull(registerEdCustomerName)) errorMsg else null
            registerLayoutEdPhoneNumber.error = if (textIsNull(registerEdPhoneNumber)) errorMsg else null
            registerLayoutEdPassword.error = if (textIsNull(registerEdPassword)) errorMsg else null
            registerLayoutEdConfirmPassword.error = if (textIsNull(registerEdConfirmPassword)) errorMsg else null
        }
    }


}