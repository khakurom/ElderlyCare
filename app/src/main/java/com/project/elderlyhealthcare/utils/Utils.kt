package com.project.elderlyhealthcare.utils

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
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

    fun sortDayList (list : List <String?>) : List<String?> {
        val sortedList = list.sortedWith(compareBy<String?> {
                if (it?.startsWith("T") == true) 0 else 1
            }.thenBy {
                it
            })
        return sortedList.distinct()
    }

    fun showDialog(context: Context , message: String) {
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
    fun formatTime (editText: NumberPicker): String {
        val time  = editText.value.toString()
        return if (time.length == 1) "0$time" else time
    }


    // return true if time set is not pass time compare to current time
    fun compareToCurrentTime (date : String, hour : String, minutes : String) : Boolean {
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


}