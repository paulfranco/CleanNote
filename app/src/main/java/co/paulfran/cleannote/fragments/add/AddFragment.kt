package co.paulfran.cleannote.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.models.Importance
import co.paulfran.cleannote.data.models.NoteData
import co.paulfran.cleannote.data.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add.*


class AddFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        // set menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title = titleET.text.toString()
        val importance = importanceSpinner.selectedItem.toString()
        val description = descriptionET.text.toString()

        val validation = verifyDataFromUser(title, description)

        if(validation) {
            // insert data to database
            val newData = NoteData(
                0,
                title,
                parseImportance(importance),
                description
            )
            noteViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }

    }

    private fun verifyDataFromUser(title: String, description: String): Boolean {
        return if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    private fun parseImportance(importance: String): Importance {
        return when(importance) {
            "High Importance" -> {Importance.HIGH}
            "Medium Importance" -> {Importance.MEDIUM}
            "Low Importance" -> {Importance.LOW}
            else -> Importance.LOW
        }
    }

}