package co.paulfran.cleannote.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.models.Importance
import co.paulfran.cleannote.data.models.NoteData
import co.paulfran.cleannote.data.viewmodel.NoteViewModel
import co.paulfran.cleannote.databinding.FragmentUpdateBinding
import co.paulfran.cleannote.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val sharedViewModel: SharedViewModel by viewModels()
    private val noteViewModel: NoteViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data Binding
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args
        // set menu
        setHasOptionsMenu(true)


        // spinner Item Selected Listener
        binding.currentImportanceSpinner.current_importanceSpinner.onItemSelectedListener = sharedViewModel.listerner

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = current_titleET.text.toString()
        val description = current_descriptionET.text.toString()
        val getImportance = current_importanceSpinner.selectedItem.toString()

        val validation = sharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            // update current item
            val updatedItem = NoteData(
                args.currentItem.id,
                title,
                sharedViewModel.parseImportance(getImportance),
                description
            )
            noteViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
            // navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    // show alert dialog to confirm removal
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            noteViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(), "Successfully Removed: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to delete '${args.currentItem.title}'?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}