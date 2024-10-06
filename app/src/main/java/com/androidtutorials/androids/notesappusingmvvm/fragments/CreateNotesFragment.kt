package com.androidtutorials.androids.notesappusingmvvm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.androidtutorials.androids.notesappusingmvvm.R
import com.androidtutorials.androids.notesappusingmvvm.constant.Constants.toast
import com.androidtutorials.androids.notesappusingmvvm.databinding.FragmentCreateNotesBinding
import com.androidtutorials.androids.notesappusingmvvm.room.entity.Notes
import com.androidtutorials.androids.notesappusingmvvm.viewModels.NotesViewModel
import java.util.Date

class CreateNotesFragment : Fragment(), View.OnClickListener {

    private lateinit var mBinder: FragmentCreateNotesBinding
    private var priority: String = "1"

    private val viewmodel: NotesViewModel by viewModels()

    //    private lateinit var viewmodel: NotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinder =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_notes, container, false)
        initUI()
        // Inflate the layout for this fragment
        return mBinder.root
    }

    private fun initUI() {
        mBinder.mToolBar.setOnClickListener{
            findNavController().popBackStack()
        }
        mBinder.greenDot.setImageResource(R.drawable.tick_svg)
        mBinder.createNotes.setOnClickListener(this@CreateNotesFragment)
        mBinder.greenDot.setOnClickListener(this@CreateNotesFragment)
        mBinder.yellowDot.setOnClickListener(this@CreateNotesFragment)
        mBinder.redDot.setOnClickListener(this@CreateNotesFragment)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.createNotes -> {
                createNotes(v)
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

    private fun createNotes(view: View) {
        val title = mBinder.edtTitle.text.toString()
        val subtitle = mBinder.edtsubTitle.text.toString()
        val notes = mBinder.edtNotes.text.toString()

        val d = Date()
        val date = android.text.format.DateFormat.format("MMMM d, yyyy", d.time).toString()
        val data = Notes(
            null,
            title = title,
            subTitle = subtitle,
            notes = notes,
            date = date,
            priority = priority
        )
        viewmodel.addNotes(data)
        toast(requireContext(), "Notes created Successfully.")
        findNavController().navigate(R.id.action_createNotesFragment_to_homeFragment)
    }
}