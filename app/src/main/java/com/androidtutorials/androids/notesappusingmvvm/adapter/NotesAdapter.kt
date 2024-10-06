package com.androidtutorials.androids.notesappusingmvvm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.androidtutorials.androids.notesappusingmvvm.R
import com.androidtutorials.androids.notesappusingmvvm.databinding.ItemNotesBinding
import com.androidtutorials.androids.notesappusingmvvm.fragments.HomeFragmentDirections
import com.androidtutorials.androids.notesappusingmvvm.room.entity.Notes

class NotesAdapter(private var context: Context, private var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.mViewholder>() {
    private var listener: OnClickListener? = null

    lateinit var mBinder: ItemNotesBinding

    @SuppressLint("NotifyDataSetChanged")
    fun filter(newfilteredlist:ArrayList<Notes>){
        notesList = newfilteredlist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewholder {
        mBinder = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_notes,
            parent,
            false
        )
        return mViewholder(mBinder)
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: mViewholder, position: Int) {
        val data = notesList[position]
        holder.title.text = data.title
        holder.subtitle.text = data.subTitle
        holder.date.text = data.date

        when (data.priority) {
            "1" -> {
                holder.priority.setBackgroundResource(R.drawable.green_dot)
            }

            "2" -> {
                holder.priority.setBackgroundResource(R.drawable.yellow_dot)
            }

            "3" -> {
                holder.priority.setBackgroundResource(R.drawable.red_dot)
            }
        }
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
    }

    class mViewholder(mBinder: ItemNotesBinding) : RecyclerView.ViewHolder(mBinder.root) {
        var title = mBinder.tvTitle
        var subtitle = mBinder.tvSubTitle
        var date = mBinder.tvDate
        var priority = mBinder.viewPriority
    }

    interface OnClickListener {
        fun onItemClick(item: Notes)
    }
}