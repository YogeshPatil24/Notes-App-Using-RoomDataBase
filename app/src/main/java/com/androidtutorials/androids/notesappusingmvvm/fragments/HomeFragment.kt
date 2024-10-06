package com.androidtutorials.androids.notesappusingmvvm.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.androidtutorials.androids.notesappusingmvvm.R
import com.androidtutorials.androids.notesappusingmvvm.adapter.NotesAdapter
import com.androidtutorials.androids.notesappusingmvvm.databinding.FragmentHomeBinding
import com.androidtutorials.androids.notesappusingmvvm.room.entity.Notes
import com.androidtutorials.androids.notesappusingmvvm.viewModels.NotesViewModel


class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinder: FragmentHomeBinding
    private val viewmodel: NotesViewModel by viewModels()
    private lateinit var notesAdapter: NotesAdapter
    var menuItem: MenuItem? = null
    var oldMotes = arrayListOf<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initUI()
        return mBinder.root
    }

    private fun initUI() {

        mBinder.notesRv.layoutManager = GridLayoutManager(requireContext(), 2)
        mBinder.addNotes.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNotesFragment)
        }
        viewmodel.getAllNotes().observe(viewLifecycleOwner) { notesList ->
            oldMotes = notesList as ArrayList<Notes>
            notesAdapter = NotesAdapter(requireContext(), notesList = notesList)
            mBinder.notesRv.adapter = notesAdapter
        }

        mBinder.filterHigh.setOnClickListener(this@HomeFragment)
        mBinder.filterMedium.setOnClickListener(this@HomeFragment)
        mBinder.filterLow.setOnClickListener(this@HomeFragment)
        mBinder.filterAll.setOnClickListener(this@HomeFragment)
        mBinder.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("SEARCHHH", "submit$query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("SEARCHHH", "$newText")
                NotesSearch(newText)
                return false
            }

        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.filterHigh -> {
                viewmodel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                    oldMotes = notesList as ArrayList<Notes>
                    notesAdapter = NotesAdapter(requireContext(), notesList = notesList)
                    mBinder.notesRv.adapter = notesAdapter
                }
            }

            R.id.filterMedium -> {
                viewmodel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                    oldMotes = notesList as ArrayList<Notes>
                    notesAdapter = NotesAdapter(requireContext(), notesList = notesList)
                    mBinder.notesRv.adapter = notesAdapter
                }
            }

            R.id.filterLow -> {
                viewmodel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                    oldMotes = notesList as ArrayList<Notes>
                    notesAdapter = NotesAdapter(requireContext(), notesList = notesList)
                    mBinder.notesRv.adapter = notesAdapter
                }
            }

            R.id.filterAll -> {
                viewmodel.getAllNotes().observe(viewLifecycleOwner) { notesList ->
                    oldMotes = notesList as ArrayList<Notes>
                    notesAdapter = NotesAdapter(requireContext(), notesList = notesList)
                    mBinder.notesRv.adapter = notesAdapter
                }
            }
        }
    }

    /*@Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_items, menu)
        searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView!!.isIconified = true
        var seatchmanager: SearchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView!!.setSearchableInfo(seatchmanager.getSearchableInfo(requireActivity().componentName))

        super.onCreateOptionsMenu(menu, inflater)
    }*/

    private fun NotesSearch(text: String?) {
        val newfilteredlist = ArrayList<Notes>()
        for (i in oldMotes) {
            if (i.title.contains(text!!) || i.subTitle.contains(text)) {
                newfilteredlist.add(i)
            }
        }
        notesAdapter.filter(newfilteredlist)
    }

}