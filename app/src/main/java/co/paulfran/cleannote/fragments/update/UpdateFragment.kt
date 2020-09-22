package co.paulfran.cleannote.fragments.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.models.Importance
import co.paulfran.cleannote.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        // set menu
        setHasOptionsMenu(true)

        view.current_titleET.setText(args.currentItem.title)
        view.current_descriptionET.setText(args.currentItem.description)
        view.current_importanceSpinner.setSelection(parseImportance(args.currentItem.importance))

        view.current_importanceSpinner.onItemSelectedListener = sharedViewModel.listerner

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    private fun parseImportance(importance: Importance): Int {
        return when(importance) {
            Importance.HIGH -> 0
            Importance.MEDIUM -> 1
            Importance.LOW -> 2
        }
    }
}