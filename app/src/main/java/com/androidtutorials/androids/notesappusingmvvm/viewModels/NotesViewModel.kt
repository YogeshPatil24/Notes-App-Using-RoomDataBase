package com.androidtutorials.androids.notesappusingmvvm.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.androidtutorials.androids.notesappusingmvvm.repository.NotesRepository
import com.androidtutorials.androids.notesappusingmvvm.room.database.NotesDatabase
import com.androidtutorials.androids.notesappusingmvvm.room.entity.Notes

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    var repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).mynotesDAo()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes: Notes) = repository.insertNotes(notes)

    fun getAllNotes(): LiveData<List<Notes>> = repository.getAllNotes()
    fun getHighNotes(): LiveData<List<Notes>> = repository.getHighNotes()
    fun getMediumNotes(): LiveData<List<Notes>> = repository.getMediumNotes()
    fun getLowNotes(): LiveData<List<Notes>> = repository.getLowNotes()

    fun deleteNotes(id: Int) = repository.deleteNotes(id)

    fun updateNotes(notes: Notes) = repository.updateNotes(notes)

}