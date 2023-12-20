package com.project.elderlyhealthcare.presentation.fragment.main.location

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.databinding.FragmentPermissionBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.LocationViewModel
import com.project.elderlyhealthcare.utils.Constant
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.showDialog


class PermissionFragment : BaseFragment<LocationViewModel, FragmentPermissionBinding>(R.layout.fragment_permission) {
    override fun variableId(): Int = BR.permissionViewModel

    override fun createViewModel(): Lazy<LocationViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentPermissionBinding {
        return FragmentPermissionBinding.bind(view)
    }

    override fun init() {
        super.init()
        binding.permissionBtContinue.setOnClickListener(object : SingleClickListener() {
            override fun onSingleClick(v: View) {
                accessNotificationPermission()
            }
        })
    }

    private fun accessNotificationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Constant.ACCESS_FINE_PERMISSION) == PackageManager.PERMISSION_DENIED) {
            requestAccessLocationPermissionLauncher.launch(Constant.ACCESS_FINE_PERMISSION)
        } else {
            findNavController().navigate(R.id.action_permissionFragment_to_locationFragment)
        }

    }


    @SuppressLint("MissingPermission")
    private val requestAccessLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            showDialog(requireContext(), "Vui lòng cho phép chúng tôi truy cập vị trí của bạn")
        } else {
            findNavController().navigate(R.id.action_permissionFragment_to_locationFragment)
        }
    }

}