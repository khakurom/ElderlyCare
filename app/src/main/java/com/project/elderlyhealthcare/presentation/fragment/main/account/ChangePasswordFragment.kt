package com.project.elderlyhealthcare.presentation.fragment.main.account

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentChangePasswordBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.AccountViewModel
import com.project.elderlyhealthcare.utils.Constant.PHONE_NUMBER
import com.project.elderlyhealthcare.utils.DelegatedPreferences
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils
import com.project.elderlyhealthcare.utils.Utils.getTextFromEdittext
import com.project.elderlyhealthcare.utils.Utils.hideKeyboard
import com.project.elderlyhealthcare.utils.Utils.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<AccountViewModel, FragmentChangePasswordBinding>(R.layout.fragment_change_password) {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    private var listener: OnFragmentInteractionListener? = null
    override fun variableId(): Int = BR.changePwViewModel

    override fun createViewModel(): Lazy<AccountViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentChangePasswordBinding {
        return FragmentChangePasswordBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun init() {
        super.init()
        binding.apply {
            changePwFrCsBar.customAppBarIvBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            changePwBtChangePw.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    checkNetworkIsAvailable ()
                }
            })

            layoutChangePw.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    v.hideKeyboard()
                }
            })

        }
        listener?.updateBottomNavVisible(true)
    }

    private fun checkNetworkIsAvailable () {
        if (Utils.isNetworkAvailable(requireContext())) {
            validateChangePassword()
        } else {
            showDialog(requireContext(), "Vui lòng kết nối internet")
        }
    }

    private fun validateChangePassword() {
        binding.apply {
            if (getTextFromEdittext(changePwEdCurrentPassword).isEmpty() || getTextFromEdittext(changePwEdNewPassword).isEmpty() || getTextFromEdittext(changePwEdConfirmPassword).isEmpty()) {
                setBackgroundLayoutEditText()
            } else {
                setBackgroundLayoutEditText()
                if (passwordIsValid(getTextFromEdittext(changePwEdNewPassword))) {
                    if (getTextFromEdittext(changePwEdNewPassword) == getTextFromEdittext(changePwEdConfirmPassword)) {
                        getCurrentPassword {
                            if (it == getTextFromEdittext(changePwEdCurrentPassword)) {
                                updateNewPassword()
                            } else {
                                showDialog(requireContext(), "Mật khẩu hiện tại không chính xác")
                            }
                        }
                    } else {
                        showDialog(requireContext(), "Mật khẩu xác nhận không chính xác")
                    }
                } else {
                    showDialog(requireContext(), "Vui lòng đặt mật khẩu dài hơn 5 kí tự")
                }
            }
        }
    }

    private fun getCurrentPassword(callback: (String?) -> Unit) {
        val phoneNumber = DelegatedPreferences(requireContext(), PHONE_NUMBER, "").getValue()
        binding.progressBar.visibility = View.VISIBLE
        val dataNodeReference = databaseReference.child("data").child(phoneNumber).child(getString(R.string.key_customer_info))
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

    private fun updateNewPassword() {
        val phoneNumber = DelegatedPreferences(requireContext(), PHONE_NUMBER, "").getValue()
        binding.progressBar.visibility = View.VISIBLE
        val dataNodeReference = databaseReference.child("data").child(phoneNumber).child(getString(R.string.key_customer_info)).child("password")
        dataNodeReference.setValue(getTextFromEdittext(binding.changePwEdNewPassword))
            .addOnSuccessListener {
                binding.progressBar.visibility = View.GONE
                showNotificationDialog()
            }
            .addOnFailureListener {
                binding.progressBar.visibility = View.GONE
                showDialog(requireContext(), "Lỗi hệ thống. Vui lòng thử lại sau!")
            }
    }

    private fun showNotificationDialog () {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        with(alertDialogBuilder) {
            setMessage("Thay đổi mật khẩu thành công")
            setCancelable(false)
            setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                backToPreScreen()
                dialog.dismiss()
            }
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun passwordIsValid(password: String): Boolean {
        return password.length >= 6
    }

    private fun setBackgroundLayoutEditText() {
        val errorMsg = getString(R.string.login_error_msg_edittext_empty)
        binding.apply {
            changePwLayoutEdCurrentPassword.error = if (Utils.textIsNull(changePwEdCurrentPassword)) errorMsg else null
            changePwLayoutEdNewPassword.error = if (Utils.textIsNull(changePwEdNewPassword)) errorMsg else null
            changePwLayoutEdConfirmPassword.error = if (Utils.textIsNull(changePwEdConfirmPassword)) errorMsg else null

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.updateBottomNavVisible(false)
    }

}