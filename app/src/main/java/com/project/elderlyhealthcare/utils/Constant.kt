package com.project.elderlyhealthcare.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Constant {


    const val TABLE_EXERCISE_EVENT = "ExerciseEvent"
    const val TABLE_MEDICINE_EVENT = "MedicineEvent"
    const val TABLE_RE_EXAM_EVENT = "ReExamEvent"

	// key preference
	const val PHONE_NUMBER = "phone number"

	// permission
	@RequiresApi(Build.VERSION_CODES.TIRAMISU)
	const val NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS


	// key intent
	const val KEY_EXERCISE_EVENT = "item exercise event"
	const val KEY_NOTIFICATION = "from notification"


    val listPrefecture = listOf(
        "Hồ Chí Minh",
        "Bà Rịa - Vũng Tàu",
        "Đà Nẵng",
        "Lâm Đồng",
        "Đồng Nai"
    )

    val listHour = arrayOf(
        "00",
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23"
    )

	val listMinutes = arrayOf(
		"00",
		"01",
		"02",
		"03",
		"04",
		"05",
		"06",
		"07",
		"08",
		"09",
		"10",
		"11",
		"12",
		"13",
		"14",
		"15",
		"16",
		"17",
		"18",
		"19",
		"20",
		"21",
		"22",
		"23",
		"24",
		"25",
		"26",
		"27",
		"28",
		"29",
		"30",
		"31",
		"32",
		"33",
		"34",
		"35",
		"36",
		"37",
		"38",
		"39",
		"40",
		"41",
		"42",
		"43",
		"44",
		"45",
		"46",
		"47",
		"48",
		"49",
		"50",
		"51",
		"52",
		"53",
		"54",
		"55",
		"56",
		"57",
		"58",
		"59",
	)
}