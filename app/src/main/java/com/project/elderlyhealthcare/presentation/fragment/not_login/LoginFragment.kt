package com.project.elderlyhealthcare.presentation.fragment.not_login

import android.content.Intent
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
import com.project.elderlyhealthcare.databinding.FragmentLoginBinding
import com.project.elderlyhealthcare.presentation.activity.MainActivity
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.not_login.NotLoginViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.getTextFromEdittext
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.isNetworkAvailable
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<NotLoginViewModel, FragmentLoginBinding>(R.layout.fragment_login) {


    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    override fun variableId(): Int = BR.loginViewModel

    override fun createViewModel(): Lazy<NotLoginViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentLoginBinding {
        return FragmentLoginBinding.bind(view)
    }


    override fun init() {
        super.init()
        binding.apply {
            layoutLogin.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })
            loginTvRegister.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
                }
            })

            loginTvClickHere.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
                }
            })

            loginBtLogin.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    checkNetworkIsAvailable ()
                }
            })
        }
    }

    private fun checkNetworkIsAvailable () {
        if (isNetworkAvailable(requireContext())) {
            validateAccount()
        } else {
            showDialog(requireContext(),"Vui lòng kết nối internet")
        }
    }


    private fun validateAccount() {
        binding.apply {
            if (getTextFromEdittext(loginEdPhoneNumber).isEmpty() || getTextFromEdittext(loginEdPassword).isEmpty()) {
                showDialog(requireContext(), "Vui lòng nhập đầy đủ số điện thoại và mật khẩu")
            } else {
                getPassword {
                    if (loginEdPassword.text?.trim().toString() == it) {
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        activity?.finish()
                    } else {
                        showDialog(requireContext(), "Sai thông tin tài khoản")
                    }
                }
            }
        }
    }


    private fun getPassword(callback: (String?) -> Unit) {
       binding.progressBar.visibility = View.VISIBLE
        val dataNodeReference = databaseReference.child("data").child(getTextFromEdittext(binding.loginEdPhoneNumber))
        dataNodeReference.orderByKey().equalTo("password")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.hasChild("password")) {
                        val passwordValue = dataSnapshot.child("password").value
                        callback(passwordValue as? String)
                    } else {
                        callback(null)
                    }
                    binding.progressBar.visibility = View.GONE
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showDialog(requireContext(), "Hệ thống lỗi, vui lòng thử lại")
                    callback(null)
                    binding.progressBar.visibility = View.GONE
                }
            })
    }



}