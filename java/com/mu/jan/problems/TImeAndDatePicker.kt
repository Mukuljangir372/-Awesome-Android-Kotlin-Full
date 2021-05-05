package com.mu.jan.problems

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import java.util.*

object MyTimePicker{
    fun show(mContext: Context,onTimePick:()->Unit){
        //Calender (java)
        val mCalender = Calendar.getInstance()
        val hourOfDay = mCalender.get(Calendar.HOUR_OF_DAY)
        val minute = mCalender.get(Calendar.MINUTE)

        var picker = TimePickerDialog(mContext,object: TimePickerDialog.OnTimeSetListener{
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                onTimePick()
            }
        },hourOfDay,minute,false)

        picker.apply {
            setTitle("Time picker")
            show()
        }

    }
}
object MyDatePicker{
    fun show(mContext: Context, onDatePick:()->Unit){
        val mCalender = Calendar.getInstance()

        var picker = DatePickerDialog(mContext,object: DatePickerDialog.OnDateSetListener{
           override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
               onDatePick()
           }
       },mCalender.get(Calendar.YEAR),mCalender.get(Calendar.MONTH),mCalender.get(Calendar.DAY_OF_MONTH))

       picker.apply {
           setTitle("")
           show()
       }
    }
}





















