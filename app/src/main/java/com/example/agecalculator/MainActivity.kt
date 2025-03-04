package com.example.agecalculator

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)

        btnDatePicker.setOnClickListener{view ->
            clickDatePicker(view)
        }
    }

    fun clickDatePicker(view: View) {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val tvSelectedDate: TextView = findViewById(R.id.tvSelectedDate)
        val tvSelectedDateInMinute: TextView = findViewById(R.id.tvSelectedDateInMinute)
        val tvSelectedMonth:TextView = findViewById(R.id.tvSelectedMonth)
        val tvSelectedYear:TextView = findViewById(R.id.tvSelectedYear)

        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener {
                                               view, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"
                tvSelectedDate.text = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val birthDate = sdf.parse(selectedDate)

                birthDate?.let{
                    val birthDateInMillis = it.time
                    val currentTimeInMillis = System.currentTimeMillis()
                    val ageInHours = (currentTimeInMillis - birthDateInMillis) / (1000 * 60 * 60)

                    tvSelectedDateInMinute.text = ageInHours.toString()

                    var ageInMonths = (year - selectedYear) * 12 + (month - selectedMonth)

                    if (day < selectedDayOfMonth){
                        ageInMonths -= 1
                    }
                    tvSelectedMonth.text = ageInMonths.toString()

                    var ageInYears = year - selectedYear

                    if(month < selectedMonth || (month == selectedMonth && day < selectedDayOfMonth)) {
                        ageInYears -= 1
                    }
                    tvSelectedYear.text = ageInYears.toString()
                }

            },
            year,
            month,
            day).show()

    }
}