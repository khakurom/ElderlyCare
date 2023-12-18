package com.project.elderlyhealthcare.presentation.fragment.main.event.notification_event

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentDisplayReExaminationNotificationBinding
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SingleClickListener
import com.project.elderlyhealthcare.utils.Utils.showDialog
import java.net.URLEncoder


class DisplayReExaminationNotificationFragment : BaseFragment<EventViewModel, FragmentDisplayReExaminationNotificationBinding>(R.layout.fragment_display_re_examination_notification) {
    private val navArgs : DisplayReExaminationNotificationFragmentArgs by navArgs()
    private var listener: OnFragmentInteractionListener? = null

    override fun variableId(): Int = BR.reExaminationViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentDisplayReExaminationNotificationBinding {
        return FragmentDisplayReExaminationNotificationBinding.bind(view)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun init() {
        super.init()
        viewModel?.updateReExEventOnOff(navArgs.reExaminationModel!!.id, false)
        binding.apply {
            displayReExBtBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })

            displayReExBtSearch.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    openBrowser()
                }
            })

            reExaminationVModel = navArgs.reExaminationModel
        }
        listener?.updateBottomNavVisible(true)
    }

    private fun openBrowser () {
        if (binding.displayReExTvAddress.text.isNotEmpty()) {
            val searchTerm = "Bệnh viện ${binding.displayReExTvAddress.text}"
            val encodedSearchTerm = URLEncoder.encode(searchTerm, "UTF-8")
            val uriString = "https://www.google.com/search?q=$encodedSearchTerm"

            val uri = Uri.parse(uriString)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } else {
            showDialog (requireContext(), "Không có địa chỉ cụ thể để tìm kiếm")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.updateBottomNavVisible(false)
    }

}