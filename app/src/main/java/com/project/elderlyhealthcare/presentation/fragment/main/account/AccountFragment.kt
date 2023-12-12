package com.project.elderlyhealthcare.presentation.fragment.main.account

import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentAccountBinding
import com.project.elderlyhealthcare.presentation.activity.NotLoginActivity
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.AccountViewModel
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.authenticate.UserManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<AccountViewModel, FragmentAccountBinding>(R.layout.fragment_account) {
    override fun variableId(): Int = BR.accountViewModel

    override fun createViewModel(): Lazy<AccountViewModel> = viewModels()

    override fun bindView(view: View): FragmentAccountBinding {
        return FragmentAccountBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.apply {
            accountLayoutLogout.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    showConfirmDialog()
                }
            })
            accountLayoutInfo.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToProfileFragment())
                }
            })

            accountLayoutChangePw.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToChangePasswordFragment())
                }
            })
        }
    }

    private fun showConfirmDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        with(alertDialogBuilder) {
            setMessage("Bạn muốn đăng xuất tài khoản")
            setCancelable(true)
            setNegativeButton("Trở về") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                UserManager.getInstance(requireContext()).removeAccount()
                startActivity(Intent(requireContext(), NotLoginActivity::class.java))
                activity?.finish()
                dialog.dismiss()
            }
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}