package com.omongole.fred.yomovieapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.omongole.fred.yomovieapp.util.Constants.BASE_IMAGE_URL
import com.omongole.fred.yomovieapp.util.Constants.BASE_IMAGE_URL_W500
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun displayOriginalImage(imagePath: String?) : String {
    return "${BASE_IMAGE_URL}$imagePath"
}

fun displayPosterImage( imagePath: String? ) : String {
    return "${BASE_IMAGE_URL_W500}$imagePath"
}

fun convertMinutesToHoursAndMinutes(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return "$hours hr $remainingMinutes mins"
}

fun separateWithCommas(number: Long): String {
    val formatter: NumberFormat = DecimalFormat("#,###")
    return formatter.format(number)
}

fun convertToPercentage(figure: Double): String {
    val percentage = (figure * 10).toInt()
    return percentage.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(releaseDate: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH)
    val date = LocalDate.parse(releaseDate, inputFormatter)
    return outputFormatter.format(date)
}
