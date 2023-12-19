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
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
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
    fun formatTimeNumberPicker(value: NumberPicker): String {
        val time = value.value.toString()
        return if (time.length == 1) "0$time" else time
    }

    fun formatTimeString (value: String): String {
        return if (value.length == 1) "0$value" else value
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

    fun getTextFromEdittext (ed : EditText) : String {
        return ed.text.toString().trim()
    }

    // calculate age from birthday
    fun calculateAge (birthdate: String): Int {
        // Define the date format
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        // Parse the birthdate string to a Date object
        val birthDate: Date = dateFormat.parse(birthdate) ?: return -1

        // Get the current date
        val currentDate: Date = Calendar.getInstance().time

        // Calculate the age
        val ageInMillis: Long = currentDate.time - birthDate.time
        val ageCalendar = Calendar.getInstance().apply { timeInMillis = ageInMillis }

        return ageCalendar.get(Calendar.YEAR) - 1970
    }

    fun extractMonthAndYear(inputString: String): Pair<String, String> {
        // Define the regex pattern to match numbers
        val regex = Regex("\\b(\\d+)\\b")

        // Find all matches in the input string
        val matches = regex.findAll(inputString)

        // Initialize variables to hold the extracted values
        var month = ""
        var year = ""

        // Extract numbers and assign them to the appropriate variable
        matches.toList().take(2).forEachIndexed { index, match ->
            val value = match.groupValues[1]
            when (index) {
                0 -> month = value
                1 -> year = value
            }
        }

        return Pair(month, year)
    }




}