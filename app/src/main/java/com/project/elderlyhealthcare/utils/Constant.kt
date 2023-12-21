package com.project.elderlyhealthcare.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Constant {


    const val TABLE_EXERCISE_EVENT = "ExerciseEvent"
    const val TABLE_MEDICINE_EVENT = "MedicineEvent"
    const val TABLE_RE_EXAM_EVENT = "ReExamEvent"
    const val TABLE_HEART_RATE = "HeartRate"
    const val TABLE_OXYGEN = "Oxygen"

	// key preference
	const val PHONE_NUMBER = "phone number"

	// permission
	@RequiresApi(Build.VERSION_CODES.TIRAMISU)
	const val NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS
	const val ACCESS_FINE_PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION

	// key intent
	const val KEY_EVENT = "key event"
	const val KEY_EVENT_ITEM = "item exercise event"
	const val KEY_NOTIFICATION = "from notification"
	const val KEY_LOCATION = "key location"

	// mode event
	const val MODE_EXERCISE = "exercise mode"
	const val MODE_MEDICINE = "medicine mode"
	const val MODE_RE_EXAMINATION = "re-examination mode"

	val listPrefecture = listOf(
		"An Giang",
		"Bà Rịa - Vũng Tàu",
		"Bắc Giang",
		"Bắc Kạn",
		"Bạc Liêu",
		"Bắc Ninh",
		"Bến Tre",
		"Bình Định",
		"Bình Dương",
		"Bình Phước",
		"Bình Thuận",
		"Cà Mau",
		"Cần Thơ",
		"Cao Bằng",
		"Đà Nẵng",
		"Đắk Lắk",
		"Đắk Nông",
		"Điện Biên",
		"Đồng Nai",
		"Đồng Tháp",
		"Gia Lai",
		"Hà Giang",
		"Hà Nam",
		"Hà Nội",
		"Hà Tĩnh",
		"Hải Dương",
		"Hải Phòng",
		"Hậu Giang",
		"Hòa Bình",
		"Hồ Chí Minh",
		"Hưng Yên",
		"Khánh Hòa",
		"Kiên Giang",
		"Kon Tum",
		"Lai Châu",
		"Lâm Đồng",
		"Lạng Sơn",
		"Lào Cai",
		"Long An",
		"Nam Định",
		"Nghệ An",
		"Ninh Bình",
		"Ninh Thuận",
		"Phú Thọ",
		"Phú Yên",
		"Quảng Bình",
		"Quảng Nam",
		"Quảng Ngãi",
		"Quảng Ninh",
		"Quảng Trị",
		"Sóc Trăng",
		"Sơn La",
		"Tây Ninh",
		"Thái Bình",
		"Thái Nguyên",
		"Thanh Hóa",
		"Thừa Thiên Huế",
		"Tiền Giang",
		"Trà Vinh",
		"Tuyên Quang",
		"Vĩnh Long",
		"Vĩnh Phúc",
		"Yên Bái"
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