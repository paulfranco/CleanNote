package co.paulfran.cleannote.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.viewmodel.NoteViewModel
import co.paulfran.cleannote.databinding.FragmentListBinding
import co.paulfran.cleannote.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data binding
        _binding = FragmentListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.sharedViewModel = sharedViewModel

        // setup recycler view
        setUpRecyclerView()

        // observe live data
        noteViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            sharedViewModel.checkIfDatabaseIsEmpty(data)
            adapter.setData(data)
        })

        // set menu
        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_all) {
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    // show alert dialog message of all items
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            noteViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Removed All Items", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete All Items?")
        builder.setMessage("Are you sure you want to delete All Items?")
        builder.create().show()
    }

    // important to avoid memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}