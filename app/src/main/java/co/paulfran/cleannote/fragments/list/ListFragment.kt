package co.paulfran.cleannote.fragments.list

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import co.paulfran.cleannote.R
import co.paulfran.cleannote.data.models.NoteData
import co.paulfran.cleannote.data.viewmodel.NoteViewModel
import co.paulfran.cleannote.databinding.FragmentListBinding
import co.paulfran.cleannote.fragments.SharedViewModel
import co.paulfran.cleannote.fragments.list.adapter.ListAdapter
import co.paulfran.cleannote.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

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

        // hide soft keyboard
        hideKeyboard(requireActivity())

        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        // swipe to delete
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.dataList[viewHolder.adapterPosition]
                // delete item
                noteViewModel.deleteItem(deletedItem)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                // restore deleted data
                restoreDeletedData(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deletedItem: NoteData) {
        val snackBar = Snackbar.make(
            view, "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        ).setActionTextColor(Color.RED)
        snackBar.setAction("Undo") {
            noteViewModel.insertData(deletedItem)

        }
        snackBar.show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
            R.id.menu_importance_high -> noteViewModel.sortByHighImportance.observe(this, Observer { adapter.setData(it)})
            R.id.menu_importance_low -> noteViewModel.sortByLowImportance.observe(this, Observer { adapter.setData(it) })
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        noteViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })
    }

}