package com.androidtutorials.androids.notesappusingmvvm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidtutorials.androids.notesappusingmvvm.R
import com.androidtutorials.androids.notesappusingmvvm.constant.Constants.toast
import com.androidtutorials.androids.notesappusingmvvm.databinding.DeleteDialogBinding
import com.androidtutorials.androids.notesappusingmvvm.databinding.FragmentEditNotesBinding
import com.androidtutorials.androids.notesappusingmvvm.room.entity.Notes
import com.androidtutorials.androids.notesappusingmvvm.viewModels.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date

class EditNotesFragment : Fragment(), View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private lateinit var mBinder: FragmentEditNotesBinding
    private val viewmodel: NotesViewModel by viewModels()
    private val notes by navArgs<EditNotesFragmentArgs>()
    private var priority: String = "1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_notes, container, false)
        mBinder.mToolBar.inflateMenu(R.menu.menu_items)
//        mBinder.mToolBar.setNavigationIcon(R.drawable.tick_svg)
        setHasOptionsMenu(true)
        initUI()
        return mBinder.root
    }

    private fun initUI() {
        mBinder.backArrow.setOnClickListener {
            findNavController().popBackStack()
        }
        mBinder.edtTitle.setText(notes.data.title)
        mBinder.edtsubTitle.setText(notes.data.subTitle)
        mBinder.edtNotes.setText(notes.data.notes)
        mBinder.mToolBar.setOnMenuItemClickListener(this@EditNotesFragment)
        when (notes.data.priority) {
            "1" -> {
                priority = "1"
                mBinder.greenDot.setImageResource(R.drawable.tick_svg)
                mBinder.redDot.setImageResource(0)
                mBinder.yellowDot.setImageResource(0)
            }

            "2" -> {
                priority = "2"
                mBinder.yellowDot.setImageResource(R.drawable.tick_svg)
                mBinder.greenDot.setImageResource(0)
                mBinder.redDot.setImageResource(0)
            }

            "3" -> {
                priority = "3"
                mBinder.redDot.setImageResource(R.drawable.tick_svg)
                mBinder.greenDot.setImageResource(0)
                mBinder.yellowDot.setImageResource(0)
            }
        }
        mBinder.editNotes.setOnClickListener(this@EditNotesFragment)
        mBinder.greenDot.setOnClickListener(this@EditNotesFragment)
        mBinder.yellowDot.setOnClickListener(this@EditNotesFragment)
        mBinder.redDot.setOnClickListener(this@EditNotesFragment)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.editNotes -> {
                editNotes(v)
            }

            R.id.green_Dot -> {
                priority = "1"
                mBinder.greenDot.setImageResource(R.drawable.tick_svg)
                mBinder.redDot.setImageResource(0)
                mBinder.yellowDot.setImageResource(0)
            }

            R.id.yellow_Dot -> {
                priority = "2"
                mBinder.yellowDot.setImageResource(R.drawable.tick_svg)
                mBinder.greenDot.setImageResource(0)
                mBinder.redDot.setImageResource(0)
            }

            R.id.red_Dot -> {
                priority = "3"
                mBinder.redDot.setImageResource(R.drawable.tick_svg)
                mBinder.greenDot.setImageResource(0)
                mBinder.yellowDot.setImageResource(0)
            }
        }
    }

    private fun editNotes(view: View) {
        val title = mBinder.edtTitle.text.toString()
        val subtitle = mBinder.edtsubTitle.text.toString()
        val edtnotes = mBinder.edtNotes.text.toString()
        val d = Date()
        val date = android.text.format.DateFormat.format("MMMM d, yyyy", d.time).toString()
        val data = Notes(
            notes.data.id, title = title, subTitle = subtitle, notes = edtnotes, date, priority
        )
        viewmodel.updateNotes(data)
        toast(requireContext(), "Notes Updated Successfully.")
        findNavController().navigate(R.id.action_editNotesFragment_to_homeFragment)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.delete -> {
                openDialog()
                true
            }

            else -> {
                false
            }
        }
    }

    private fun openDialog() {
        val bottomSheetBehaviour = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
        val binding = DataBindingUtil.inflate<DeleteDialogBinding>(
            layoutInflater, R.layout.delete_dialog, null, false
        )
        binding.txtNo.setOnClickListener {
            bottomSheetBehaviour.dismiss()
        }
        binding.txtYes.setOnClickListener {
            viewmodel.deleteNotes(notes.data.id!!)
            bottomSheetBehaviour.dismiss()
            Navigation.findNavController(requireView())
                .navigate(R.id.action_editNotesFragment_to_homeFragment)
        }
        bottomSheetBehaviour.setContentView(binding.root)
        bottomSheetBehaviour.show()
    }
}