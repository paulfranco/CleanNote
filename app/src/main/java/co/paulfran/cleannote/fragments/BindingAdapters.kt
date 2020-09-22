package co.paulfran.cleannote.fragments

import android.view.View
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.models.Importance
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object {
        @BindingAdapter("android.navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if(navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            when(emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:parseImportanceToInt")
        @JvmStatic
        fun parseImportanceToInt(view: Spinner, importance: Importance) {
            when(importance) {
                Importance.HIGH -> { view.setSelection(0) }
                Importance.MEDIUM -> { view.setSelection(1) }
                Importance.LOW -> { view.setSelection(2) }
            }
        }

    }
}