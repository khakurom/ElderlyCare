package com.project.elderlyhealthcare.utils

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {
    fun isNetworkAvailable(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun sortDayList(list: List<String?>): List<String?> {
        val sortedList = list.sortedWith(compareBy<String?> {
            if (it?.startsWith("T") == true) 0 else 1
        }.thenBy {
            it
        })
        return sortedList.distinct()
    }

    fun showDialog(context: Context, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        with(alertDialogBuilder) {
            setMessage(message)
            setCancelable(true)
            setPositiveButton("OK") { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    // format time text
    fun formatTime(editText: NumberPicker): String {
        val time = editText.value.toString()
        return if (time.length == 1) "0$time" else time
    }


    // return true if time set is not pass time compare to current time
    fun compareToCurrentTime(date: String, hour: String, minutes: String): Boolean {
        val dateTime = "$date $hour:$minutes"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val targetDateTime = Calendar.getInstance()

        targetDateTime.time = dateFormat.parse(dateTime) ?: Date()
        val currentDateTime = Calendar.getInstance()
        return currentDateTime.time.after(targetDateTime.time)
    }


    fun getDayMonthYearFromCurrentDate(dateString: String): Triple<String, String, String>? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        try {
            val date = dateFormat.parse(dateString)
            val calendar = Calendar.getInstance()
            calendar.time = date ?: return null

            val day = calendar.get(Calendar.DAY_OF_MONTH).toString()
            val month =
                (calendar.get(Calendar.MONTH) + 1).toString() // Months are zero-based, so add 1
            val year = calendar.get(Calendar.YEAR).toString()

            return Triple(day, month, year)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    // set background for toggle button
    fun settingDayPicker(
        context: Context,
        toggleBtMon: ToggleButton,
        toggleBtTu: ToggleButton,
        toggleBtWe: ToggleButton,
        toggleBtTh: ToggleButton,
        toggleBtFr: ToggleButton,
        toggleBtSa: ToggleButton,
        toggleBtSun: ToggleButton,

        ) {
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(R.attr.state_checked), // Checked state
                intArrayOf(-R.attr.state_checked)  // Unchecked state
            ),
            intArrayOf(
                ContextCompat.getColor(
                    context,
                    com.project.elderlyhealthcare.R.color.login_blue
                ),   // Color when checked
                ContextCompat.getColor(
                    context,
                    com.project.elderlyhealthcare.R.color.blue
                ),   // Color when uncheck
            )
        )

        toggleBtMon.backgroundTintList = colorStateList
        toggleBtTu.backgroundTintList = colorStateList
        toggleBtWe.backgroundTintList = colorStateList
        toggleBtTh.backgroundTintList = colorStateList
        toggleBtFr.backgroundTintList = colorStateList
        toggleBtSa.backgroundTintList = colorStateList
        toggleBtSun.backgroundTintList = colorStateList

    }


    // get current date
    fun getCurrentTime(textview: TextView, context: Context) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DATE)
        textview.text = context.getString(
            com.project.elderlyhealthcare.R.string.day_month_year,
            day.toString(),
            (month + 1).toString(),
            year.toString()
        )
    }

    // uncheck toggle button
    fun uncheckedRepeatDay(
        toggleBtMon: ToggleButton,
        toggleBtTu: ToggleButton,
        toggleBtWe: ToggleButton,
        toggleBtTh: ToggleButton,
        toggleBtFr: ToggleButton,
        toggleBtSa: ToggleButton,
        toggleBtSun: ToggleButton,
    ) {

        toggleBtMon.isChecked = false
        toggleBtTu.isChecked = false
        toggleBtWe.isChecked = false
        toggleBtTh.isChecked = false
        toggleBtFr.isChecked = false
        toggleBtSa.isChecked = false
        toggleBtSun.isChecked = false

    }

    // compare day before or after current day (return true if day is before current day)
    @SuppressLint("SimpleDateFormat")
    fun compareToCurrentDay (day : String) : Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateToCompare: Date = dateFormat.parse(day)
        val currentDate: Calendar = Calendar.getInstance()
        val currentDateAsDate: Date = currentDate.time
        return dateToCompare.before(currentDateAsDate)
    }

    fun settingDayRepeat(
        dayRepeatList: List<String?>, toggleBtMon: ToggleButton,
        toggleBtTu: ToggleButton,
        toggleBtWe: ToggleButton,
        toggleBtTh: ToggleButton,
        toggleBtFr: ToggleButton,
        toggleBtSa: ToggleButton,
        toggleBtSun: ToggleButton,
    ) {

        if (dayRepeatList.contains("T2")) {
            toggleBtMon.isChecked = true
        }
        if (dayRepeatList.contains("T3")) {
            toggleBtTu.isChecked = true
        }
        if (dayRepeatList.contains("T4")) {
            toggleBtWe.isChecked = true
        }
        if (dayRepeatList.contains("T5")) {
            toggleBtTh.isChecked = true
        }
        if (dayRepeatList.contains("T6")) {
            toggleBtFr.isChecked = true
        }
        if (dayRepeatList.contains("T7")) {
            toggleBtSa.isChecked = true
        }
        if (dayRepeatList.contains("CN")) {
            toggleBtSun.isChecked = true
        }

    }

    // check edittext is null or not
    fun textIsNull(etId: TextInputEditText): Boolean {
        return etId.text.toString().trim().isEmpty()
    }

    // set layout edittext with border is red color
    fun textFieldIsEmpty(layoutEtId: TextInputLayout) {
        layoutEtId.error = "Bạn cần nhập thông tin đầy đủ"
    }

    fun textFieldIsNotEmpty( layoutEtId: TextInputLayout) {
        layoutEtId.error = null
    }




}