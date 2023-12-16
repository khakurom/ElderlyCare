package com.project.elderlyhealthcare.presentation.fragment.main.event.notification_event

import android.content.Context
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.project.elderlyhealthcare.R
import com.project.elderlyhealthcare.BR
import com.project.elderlyhealthcare.databinding.FragmentDisplayMedicineNotificationBinding
import com.project.elderlyhealthcare.domain.models.MedicineTypeModel
import com.project.elderlyhealthcare.presentation.adapter.MedicineTypeAdapter
import com.project.elderlyhealthcare.presentation.adapter.OnItemRemoveListener
import com.project.elderlyhealthcare.presentation.adapter.OnItemSelectListener
import com.project.elderlyhealthcare.presentation.fragment.base.BaseFragment
import com.project.elderlyhealthcare.presentation.viewmodels.main.EventViewModel
import com.project.elderlyhealthcare.utils.OnFragmentInteractionListener
import com.project.elderlyhealthcare.utils.SimpleDividerItemDecoration
import com.project.elderlyhealthcare.utils.SingleClickListener


class DisplayMedicineNotificationFragment :
    BaseFragment<EventViewModel, FragmentDisplayMedicineNotificationBinding>(R.layout.fragment_display_medicine_notification) {
    private val navArgs : DisplayMedicineNotificationFragmentArgs by navArgs()
    private var listener: OnFragmentInteractionListener? = null


    override fun variableId(): Int = BR.displayMedicineViewModel

    override fun createViewModel(): Lazy<EventViewModel> = activityViewModels()

    override fun bindView(view: View): FragmentDisplayMedicineNotificationBinding {
        return FragmentDisplayMedicineNotificationBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun init() {
        super.init()
        val adapter : MedicineTypeAdapter by lazy {
           MedicineTypeAdapter(true)
        }
        binding.apply {
            displayMedicineBtBack.setOnClickListener(object : SingleClickListener() {
                override fun onSingleClick(v: View) {
                    backToPreScreen()
                }
            })
            medicineEventModel = navArgs.medicineModel
            displayMedicineRcvMedicineType.adapter = adapter
            displayMedicineRcvMedicineType.addItemDecoration(SimpleDividerItemDecoration(requireContext(),R.drawable.line_divider))

            adapter.submitList(createMedicineTypeList())
        }
        listener?.updateBottomNavVisible(true)
    }

    private fun createMedicineTypeList () : List <MedicineTypeModel> {
        val medicineNameList = navArgs.medicineModel?.medicineName!!
        val medicineDoseList = navArgs.medicineModel?.medicineDose!!
        return medicineNameList.zip(medicineDoseList) { string, int ->
            MedicineTypeModel(string, int)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener?.updateBottomNavVisible(false)
    }

}