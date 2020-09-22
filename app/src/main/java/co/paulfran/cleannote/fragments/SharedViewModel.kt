package co.paulfran.cleannote.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.models.Importance

class SharedViewModel(application: Application): AndroidViewModel(application) {

    val listerner: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position) {
                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.red))}
                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.yellow))}
                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application, R.color.green))}
            }
        }

    }

    fun verifyDataFromUser(title: String, description: String): Boolean {
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    fun parseImportance(importance: String): Importance {
        return when(importance) {
            "High Importance" -> {
                Importance.HIGH}
            "Medium Importance" -> {
                Importance.MEDIUM}
            "Low Importance" -> {
                Importance.LOW}
            else -> Importance.LOW
        }
    }

    fun parseImportanceToInt(importance: Importance): Int {
        return when(importance) {
            Importance.HIGH -> 0
            Importance.MEDIUM -> 1
            Importance.LOW -> 2
        }
    }

}