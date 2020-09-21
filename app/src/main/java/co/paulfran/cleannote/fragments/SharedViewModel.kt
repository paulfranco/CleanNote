package co.paulfran.cleannote.fragments

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import co.paulfran.cleannote.data.models.Importance

class SharedViewModel(application: Application): AndroidViewModel(application) {

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

}